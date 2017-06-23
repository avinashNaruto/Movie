package com.example.avinash.moviebaaz;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.avinash.moviebaaz.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by avinash on 1/5/16.
 */
public class TVAdapter extends RecyclerView.Adapter<TVAdapter.ViewHolder> {

    private ArrayList<String> mostPopularTVList = new ArrayList<>();
    private Context context;
    private static String BASE_URL = "http://image.tmdb.org/t/p/w500/" ;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivMostPopular ;
        public ViewHolder(View itemView) {
            super(itemView);
            ivMostPopular = (ImageView)itemView.findViewById(R.id.iv_most_popular_tv);
        }
    }

    public TVAdapter(ArrayList<String> mostPopularTVList , Context context) {
        this.mostPopularTVList =mostPopularTVList;
        this.context = context;
    }

    @Override
    public TVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.most_popular_tv_item , parent , false);
        ViewHolder ivh = new ViewHolder(v);
        return ivh;
    }

    @Override
    public void onBindViewHolder(TVAdapter.ViewHolder holder, int position) {

        String url = BASE_URL + mostPopularTVList.get(position) ;
        //holder.tvFavourites.setText(favouriteList.get(position));
        Log.d("Hellyeahtv", url + " " + position + "\n");
        try {
            Picasso.with(context).load(url).into(holder.ivMostPopular, new Callback() {
                @Override
                public void onSuccess() {
                    Log.d("Hellyeah Adapter" , "Picasso success" + "\n");

                }

                @Override
                public void onError() {
                    Log.d("Hellyeah Adapter" , "Picasso error" + "\n");
                }
            });
        } catch (Exception e) {
            Log.d("Hellyeah picasso" , ""+e) ;
        }
    }

    @Override
    public int getItemCount() {
        Log.d("Hellyeahtv", ""+mostPopularTVList.size());
        return mostPopularTVList.size();
    }
}