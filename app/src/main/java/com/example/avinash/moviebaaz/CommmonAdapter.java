package com.example.avinash.moviebaaz;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avinash.moviebaaz.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by avinash on 4/5/16.
 */
public class CommmonAdapter extends RecyclerView.Adapter<CommmonAdapter.ViewHolder> {

    private ArrayList<String> topRatedList = new ArrayList<>();
    private ArrayList<String> topRatedImagesURl = new ArrayList<>();
    private ArrayList<String> ratingList = new ArrayList<>();
    private ArrayList<String> idList = new ArrayList<>();
    private static String BASE_URL = "http://image.tmdb.org/t/p/w500/" ;
    Context context;

    public interface OnLayoutClick{
        public void onClick(String id);
    }

    public OnLayoutClick onLayoutClick;

    public void setOnLayoutClick(OnLayoutClick onLayoutClick) {
        this.onLayoutClick = onLayoutClick;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvMovieName;
        public ImageView ivImage;
        private TextView tvRating;
        public RelativeLayout rlCommon;

        public ViewHolder(View itemView) {
            super(itemView);
            rlCommon = (RelativeLayout)itemView.findViewById(R.id.rl);
            tvMovieName = (TextView)itemView.findViewById(R.id.tv_top_rated_text);
            ivImage = (ImageView)itemView.findViewById(R.id.iv_top_rated_image);
            tvRating = (TextView)itemView.findViewById(R.id.tv_rating);

        }
    }

    public CommmonAdapter(Context context, ArrayList<String> topRatedList, ArrayList<String> topRatedImagesURl , ArrayList<String> ratingList , ArrayList<String> idList) {
        this.context = context;
        this.topRatedList = topRatedList;
        this.topRatedImagesURl = topRatedImagesURl;
        this.ratingList = ratingList;
        this.idList = idList;
    }

    @Override
    public CommmonAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_rated_item_view , parent , false);
        ViewHolder ivh = new ViewHolder(v);
        return ivh;
    }

    @Override
    public void onBindViewHolder(CommmonAdapter.ViewHolder holder, final int position) {
        String url = BASE_URL + topRatedImagesURl.get(position);
        holder.tvMovieName.setText(topRatedList.get(position));
        try {
            holder.tvRating.setText(ratingList.get(position));
        } catch (Exception e) {
            Log.d("Extraniry" , position +"");
        }
        try {
            Picasso.with(context).load(url).into(holder.ivImage);
        } catch (Exception e) {
            Log.d("Hellyeah" , "CommmonAdapter " +e.toString());
        }

        holder.rlCommon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onLayoutClick != null) {
                    onLayoutClick.onClick(idList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return topRatedImagesURl.size();
    }
}
