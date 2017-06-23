package com.example.avinash.moviebaaz;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.avinash.moviebaaz.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by avinash on 23/4/16.
 */
public class FavouriteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> favouriteList = new ArrayList<>() ;
    Context context ;
    private static String BASE_URL = "http://image.tmdb.org/t/p/w500/" ;
    public int HEADER_TYPE = 0;
    public int ITEM_TYPE = 1;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvFavourites ;
        public ImageView iv_favourites ;
        public ViewHolder(View itemView) {
            super(itemView);
           // this.tvFavourites = (TextView)itemView.findViewById(R.id.tv_favourite_item) ;
            this.iv_favourites = (ImageView)itemView.findViewById(R.id.iv_favourites) ;

        }
    }

    public static class HeaderHolder extends RecyclerView.ViewHolder {

        public TextView tvHeader ;
        public HeaderHolder(View itemView) {
            super(itemView);
            this.tvHeader = (TextView)itemView.findViewById(R.id.favourites_header);
        }
    }

    public FavouriteAdapter(ArrayList<String> favouriteList , Context context) {
        this.favouriteList = favouriteList ;
        this.context = context ;
        Log.d("Hellyeah Adapter", "" + favouriteList.size());

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == HEADER_TYPE) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourites_header , parent , false);
            HeaderHolder ivh = new HeaderHolder(v);
            return ivh ;
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourites_item , parent , false);
            ViewHolder ivh = new ViewHolder(v);
            return ivh ;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if( holder instanceof HeaderHolder) {
            HeaderHolder newHolder = (HeaderHolder)holder;
            newHolder.tvHeader.setText("Your Favourites");
            newHolder.tvHeader.setVisibility(View.GONE);
        } else {
            ViewHolder newHolder = (ViewHolder)holder;
            String url = BASE_URL + favouriteList.get(position-1);
            //holder.tvFavourites.setText(favouriteList.get(position));
            Log.d("Hellyeah Adapter", url + " " + position + "\n");
            try {
                Picasso.with(context).load(url).into(newHolder.iv_favourites, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d("Hellyeah Adapter", "Picasso success" + "\n");

                    }

                    @Override
                    public void onError() {
                        Log.d("Hellyeah Adapter", "Picasso error" + "\n");
                    }
                });
            } catch (Exception e) {
                Log.d("Hellyeah picasso", "" + e);
            }
        }

    }

    @Override
    public int getItemCount() {
        Log.d("Hellyeah getItemCount" , favouriteList.size()+"\n");
        return favouriteList.size() + 1 ;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0) {
            return HEADER_TYPE;
        }
        return ITEM_TYPE;
    }
}
