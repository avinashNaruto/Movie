package com.example.avinash.moviebaaz;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avinash.moviebaaz.R;

import java.util.ArrayList;

/**
 * Created by avinash on 4/5/16.
 */
public class GenreListAdapter extends RecyclerView.Adapter<GenreListAdapter.ViewHolder> {

    public interface OnGenreClick{
        public void onClick(int position);
    }

    public OnGenreClick onGenreClick;

    public void setOnGenreClick(OnGenreClick onGenreClick) {
        this.onGenreClick = onGenreClick;
    }

    private Context context;
    private ArrayList<String> genreList = new ArrayList<>();

    public GenreListAdapter(Context context, ArrayList<String> genreList) {
        this.context = context;
        this.genreList = genreList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.genre_list_item_view , parent , false);
        ViewHolder ivh = new ViewHolder(v);
        return ivh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvGenreListItem.setText(genreList.get(position));
        holder.tvGenreListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onGenreClick != null) {
                    onGenreClick.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return genreList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvGenreListItem;
        public ViewHolder(View itemView) {
            super(itemView);
            this.tvGenreListItem = (TextView)itemView.findViewById(R.id.tv_movie_genre_list_item);
        }
    }
}
