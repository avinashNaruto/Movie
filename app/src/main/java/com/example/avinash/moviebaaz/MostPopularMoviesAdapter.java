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
 * Created by avinash on 30/4/16.
 */
public class MostPopularMoviesAdapter extends RecyclerView.Adapter<MostPopularMoviesAdapter.ViewHolder> {

    private ArrayList<String> mostPopularMoviesList = new ArrayList<>();
    private Context context;
    private static String BASE_URL = "http://image.tmdb.org/t/p/w500/" ;

    public MostPopularMoviesAdapter(ArrayList<String> mostPopularMoviesList , Context context) {

        this.mostPopularMoviesList = mostPopularMoviesList;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivMostPopular;
        public ViewHolder(View itemView) {
            super(itemView);
            ivMostPopular = (ImageView)itemView.findViewById(R.id.iv_most_popular_movie);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.most_popular_movies_item , parent , false);
        ViewHolder ivh = new ViewHolder(v);
        return ivh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String url = BASE_URL + mostPopularMoviesList.get(position) ;
        //holder.tvFavourites.setText(favouriteList.get(position));
        Log.d("Hellyeah Adapter", url + " " + position + "\n");
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
        return mostPopularMoviesList.size();
    }

}
