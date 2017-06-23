package com.example.avinash.moviebaaz;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avinash.moviebaaz.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by avinash on 30/4/16.
 */
public class MoviesFragment extends BaseFragment {

    private LinearLayout linearLayout;
    private ArrayList<String> mostPopularMoviesList = new ArrayList<>();
    private NestedScrollView nestedScrollView;
    private TextView tvTopRated ;
    private TextView tvLowestRated;
    private TextView tvUpcomingMovies;
    private TextView tvMoviesByGenre;
    private TextView tvSeeAll;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentActivity=(MainActivity)getActivity();
        View v = inflater.inflate(R.layout.movies_widget , container , false);
        rvMostPopularMovies = (RecyclerView)v.findViewById(R.id.rv_most_popular_movies);
        fetchMostPopularMovies();
        // scrollView = (ScrollView)v.findViewById(R.id.scroll_bar);
        tvLowestRated = (TextView)v.findViewById(R.id.tv_lowest_rated);
        tvMoviesByGenre = (TextView)v.findViewById(R.id.tv_popular_genre);
        tvTopRated = (TextView)v.findViewById(R.id.tv_top_rated);
        tvUpcomingMovies = (TextView)v.findViewById(R.id.tv_upcoming_movies);
        mostPopularMoviesAdapter = new MostPopularMoviesAdapter(mostPopularMoviesList , parentActivity.getApplicationContext());
        rvLinearLayoutManager = new LinearLayoutManager(parentActivity);
        rvLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvMostPopularMovies.setLayoutManager(rvLinearLayoutManager);
        rvMostPopularMovies.setAdapter(mostPopularMoviesAdapter);
        tvSeeAll = (TextView)v.findViewById(R.id.tv_see_all);

        tvSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.openCarouselFragment("Movies");
            }
        });

        tvMoviesByGenre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.openMoviesGenreListFragment();
            }
        });

        tvTopRated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.openTopRatedMoviesFragment();
            }
        });

        tvUpcomingMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.openUpcomingMoviesFragment();
            }
        });
        return v;
    }

//    private void addMoviesWidget() {
//        View v = initializeMoviesWidget(mostPopularMoviesList);
//        linearLayout.addView(v);
//
//    }

    private void fetchMostPopularMovies() {

        AsyncHttpClient client = new AsyncHttpClient() ;
        client.get("http://api.themoviedb.org/3/movie/popular?api_key=98e6d988c1f0793f75610352a871a7e7", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("Hellyeah", "Reached fetchDiscoverMovies success");
                String response = "";
                try {
                    response = new String(responseBody, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                int cnt = 0 ;
                try {
                    JSONObject jsonData = new JSONObject(response);
                    //    Log.d("Hellyeah" , jsonData.toString()) ;
                    JSONArray jsonArray = jsonData.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject ob = jsonArray.getJSONObject(i);
                        cnt++;
                        //    Log.i("Hellyeah", ob.getString("poster_path") + "\n") ;
                        mostPopularMoviesList.add(ob.getString("poster_path"));
                    }

                } catch (Exception e) {
                    Log.d("Hellyeah", "Reached fetchDiscoverMovies success catch");
                    e.printStackTrace();
                }
                mostPopularMoviesAdapter.notifyDataSetChanged();
                Log.d("Hellyeahcnt", "Reached fetchDiscoverMovies success end " +cnt);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Hellyeah ", "fetchDiscoverMovies failed " + error);
            }
        });
    }

}
