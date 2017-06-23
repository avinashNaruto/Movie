package com.example.avinash.moviebaaz;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.avinash.moviebaaz.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by avinash on 27/4/16.
 */
public class HomePageFragment extends BaseFragment {

    private LinearLayout linearLayout ;
    private CarouselAdapter carouselAdapter;
    private ArrayList<String> imageURLlist = new ArrayList<>();
    private FavouriteAdapter favouriteAdapter;
    private LinearLayoutManager rvLayoutManager;
    private ArrayList<String> favouriteList = new ArrayList<>() ;
    private ArrayList<String> discoverMovieList = new ArrayList<>() ;
    private ArrayList<String> nowPlayingMovieIdList = new ArrayList<>();
    private ArrayList<String> nowPlayingMoviesTrailerList = new ArrayList<>();
    private CarouselTrailerAdapter carouselTrailerAdapter ;
    private NestedScrollView nestedScrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.common_container, container, false) ;
        linearLayout = (LinearLayout)v.findViewById(R.id.container);
        parentActivity = (MainActivity) getActivity();
        nestedScrollView = (NestedScrollView)v.findViewById(R.id.container_scroll_view);
        addCarouselWidget();
        addHorizantalMovieScroller();

        //scrollToTop();

        return v;
    }

    public void addCarouselWidget() {

        View v = initializeCarousel();
        linearLayout.addView(v);
//        imageURLlist.add("http://image.tmdb.org/t/p/w500/udKOK1kE3wJzpjrCfhmuMC6M7lC.jpg");
//        imageURLlist.add("http://image.tmdb.org/t/p/w500/aUV2ujhVT7cQHu4IlwnCBN9OQvN.jpg");
//        imageURLlist.add("http://image.tmdb.org/t/p/w500/6iQ4CMtYorKFfAmXEpAQZMnA0Qe.jpg");
//        imageURLlist.add("http://image.tmdb.org/t/p/w500/3zQvuSAUdC3mrx9vnSEpkFX0968.jpg");
//        imageURLlist.add("http://image.tmdb.org/t/p/w500/xjjO3JIdneMBTsS282JffiPqfHW.jpg");
        fetchDiscoverMovies();
        carouselAdapter = new CarouselAdapter(parentActivity.getApplicationContext() , imageURLlist);
        carouselAdapter.setTimer(vpCarousel, 3);
        vpCarousel.setAdapter(carouselAdapter);
        circlePageIndicator.setViewPager(vpCarousel);
        /*carouselAdapter.setRealCount(new CarouselAdapter.RealCount() {
            @Override
            public int getRealCount() {
                return 1000;
            }
        });*/
        DisplayMetrics displaymetrics = new DisplayMetrics();
        parentActivity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        carouselAdapter.notifyDataSetChanged();
//        fetchplayingNowMoviesId();
//        carouselTrailerAdapter = new CarouselTrailerAdapter( nowPlayingMoviesTrailerList , parentActivity.getApplicationContext());
//        carouselTrailerAdapter.setTimer(vpCarousel, 3);
//        vpCarousel.setAdapter(carouselTrailerAdapter);
    }

    public void addHorizantalMovieScroller() {

        View v = initializeHorizantalMovieScroller();
        rvLayoutManager = new LinearLayoutManager(parentActivity);
        rvLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvFavourite.setLayoutManager(rvLayoutManager);
        fetchFavouriteList();
        favouriteAdapter = new FavouriteAdapter(favouriteList , parentActivity);
        rvFavourite.setAdapter(favouriteAdapter);
        rvFavourite.setFocusable(false);
        linearLayout.addView(v);
    }

    public void scrollToTop() {
        nestedScrollView.smoothScrollTo(0, 0);
    }

    public void fetchFavouriteList() {

        String sessionId = "" ;
        sessionId = parentActivity.sessionID ;
        Log.d("Hellyeah", "Reached faav " + sessionId) ;
        AsyncHttpClient client = new AsyncHttpClient() ;
        client.get("http://api.themoviedb.org/3/account/id/favorite/movies?api_key=98e6d988c1f0793f75610352a871a7e7&session_id=" + sessionId, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("Hellyeah", "Reached faav success");
                String response = "";
                try {
                    response = new String(responseBody, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                try {
                    JSONObject jsonData = new JSONObject(response);
                    //    Log.d("Hellyeah" , jsonData.toString()) ;
                    JSONArray jsonArray = jsonData.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject ob = jsonArray.getJSONObject(i);
                        //    Log.i("Hellyeah", ob.getString("poster_path") + "\n") ;
                        favouriteList.add(ob.getString("poster_path"));
                    }

                } catch (Exception e) {
                    Log.d("Hellyeah", "Reached faav success catch");
                    e.printStackTrace();
                }
                favouriteAdapter.notifyDataSetChanged();
                Log.d("Hellyeah", "Reached faav success end ");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Hellyeah favoritesFrag", "failed " + error);
            }
        });
    }

    private void fetchDiscoverMovies() {

        AsyncHttpClient client = new AsyncHttpClient() ;
        client.get("http://api.themoviedb.org/3/discover/movie?api_key=98e6d988c1f0793f75610352a871a7e7&primary_release_year=2016", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("Hellyeah", "Reached fetchDiscoverMovies success");
                String response = "";
                try {
                    response = new String(responseBody, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                try {
                    JSONObject jsonData = new JSONObject(response);
                    //    Log.d("Hellyeah" , jsonData.toString()) ;
                    JSONArray jsonArray = jsonData.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject ob = jsonArray.getJSONObject(i);
                        //    Log.i("Hellyeah", ob.getString("poster_path") + "\n") ;
                        imageURLlist.add(ob.getString("backdrop_path"));
                    }

                } catch (Exception e) {
                    Log.d("Hellyeah", "Reached fetchDiscoverMovies success catch");
                    e.printStackTrace();
                }
                carouselAdapter.notifyDataSetChanged();
                Log.d("Hellyeah", "Reached fetchDiscoverMovies success end ");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Hellyeah ", "fetchDiscoverMovies failed " + error);
            }
        });
    }

    private void fetchplayingNowMoviesId() {

        AsyncHttpClient client = new AsyncHttpClient() ;
        client.get("http://api.themoviedb.org/3/movie/now_playing?api_key=98e6d988c1f0793f75610352a871a7e7", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("Hellyeah", "Reached fetchplayingNowMoviesId success");
                String response = "";
                try {
                    response = new String(responseBody, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                try {
                    JSONObject jsonData = new JSONObject(response);
                    //    Log.d("Hellyeah" , jsonData.toString()) ;
                    JSONArray jsonArray = jsonData.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject ob = jsonArray.getJSONObject(i);
                        //    Log.i("Hellyeah", ob.getString("poster_path") + "\n") ;
                        nowPlayingMovieIdList.add(ob.getString("id"));
                    }

                } catch (Exception e) {
                    Log.d("Hellyeah", "Reached fetchplayingNowMoviesId success catch");
                    e.printStackTrace();
                }
                Log.d("Hellyeah", "Reached fetchplayingNowMoviesId success end ");

                for( int i = 0 ; i < nowPlayingMovieIdList.size() ; i++) {
                    fetchVideosFromId(nowPlayingMovieIdList.get(i));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Hellyeah ", "fetchplayingNowMoviesId failed " + error);
            }
        });

    }

    private void fetchVideosFromId(String key) {

        AsyncHttpClient client = new AsyncHttpClient() ;
        client.get("http://api.themoviedb.org/3/movie/" +  key+ "/videos?api_key=98e6d988c1f0793f75610352a871a7e7", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("Hellyeah", "Reached fetchVideosFromId success");
                String response = "";
                try {
                    response = new String(responseBody, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                try {
                    JSONObject jsonData = new JSONObject(response);
                    //    Log.d("Hellyeah" , jsonData.toString()) ;
                    JSONArray jsonArray = jsonData.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject ob = jsonArray.getJSONObject(i);
                        //    Log.i("Hellyeah", ob.getString("poster_path") + "\n") ;
                        nowPlayingMoviesTrailerList.add(ob.getString("key"));
                        Log.d("HellyeahId", ob.getString("key"));
                    }

                } catch (Exception e) {
                    Log.d("Hellyeah", "Reached fetchVideosFromId success catch");
                    e.printStackTrace();
                }
                carouselTrailerAdapter.notifyDataSetChanged();
                Log.d("Hellyeah", "Reached fetchVideosFromId success end ");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Hellyeah ", "fetchVideosFromId failed " + error);
            }
        });
    }

}
