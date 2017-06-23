package com.example.avinash.moviebaaz;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.avinash.moviebaaz.R;
import com.example.avinash.moviebaaz.CirclePageIndicator;


/**
 * Created by avinash on 21/4/16.
 */
public class BaseFragment extends Fragment {
    private LayoutInflater inflater;
    protected MainActivity parentActivity ;
    protected ViewPager vpCarousel ;
    protected RecyclerView rvFavourite ;

    //Movies Fragment
    protected RecyclerView rvMostPopularMovies;
    protected MostPopularMoviesAdapter mostPopularMoviesAdapter;
    protected LinearLayoutManager rvLinearLayoutManager;
    protected ScrollView scrollView ;
    protected CirclePageIndicator circlePageIndicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected View initializeHorizantalMovieScroller() {

        inflater = (LayoutInflater)parentActivity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
        View v = inflater.inflate(R.layout.favourites ,null ) ;
        rvFavourite = (RecyclerView)v.findViewById(R.id.rv_favourite) ;
        return v ;
    }

    protected View initializeCarousel() {
        inflater = (LayoutInflater)parentActivity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.carousel_widget , null);
        vpCarousel = (ViewPager)v.findViewById(R.id.vp_carousel);
        circlePageIndicator = (CirclePageIndicator)v.findViewById(R.id.lcp_indicator);
        return v;
    }

    /*protected View initializeMoviesWidget(ArrayList<String> mostPopularMoviesList) {

        parentActivity=(MainActivity)getActivity();
        inflater = (LayoutInflater)parentActivity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
        View v = inflater.inflate(R.layout.movies_widget , null);
        rvMostPopularMovies = (RecyclerView)v.findViewById(R.id.rv_most_popular_movies);
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
        return v;
    }*/

}
