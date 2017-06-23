package com.example.avinash.moviebaaz;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class TVFragment extends BaseFragment {

    private RecyclerView rvMostPopularTVShows;
    private TVAdapter mostPopularTvAdapter;
    private LinearLayoutManager rvLinearLayoutManager;
    private ArrayList<String> mostPopularTVShowsList = new ArrayList<>();
    private TextView tvTopRated ;
    private TextView tvLowestRated;
    private TextView tvOnTheAir;
    private TextView tvTVByGenre;
    private TextView tvAiringToday;
    private TextView tvSeeAll;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        parentActivity = (MainActivity)getActivity();
        View v = inflater.inflate(R.layout.tv_widget , container ,  false);
        tvTopRated = (TextView)v.findViewById(R.id.tv_top_rated);
        tvOnTheAir = (TextView)v.findViewById(R.id.tv_on_the_air);
        tvAiringToday = (TextView)v.findViewById(R.id.tv_airing_today);
        tvTVByGenre = (TextView)v.findViewById(R.id.tv_popular_genre);
        rvMostPopularTVShows = (RecyclerView)v.findViewById(R.id.rv_most_popular_tv_shows);
        rvLinearLayoutManager = new LinearLayoutManager(parentActivity);
        rvLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvMostPopularTVShows.setLayoutManager(rvLinearLayoutManager);
        mostPopularTvAdapter = new TVAdapter(mostPopularTVShowsList , parentActivity.getApplicationContext());
        rvMostPopularTVShows.setAdapter(mostPopularTvAdapter);
        fetchMostPopularTV();

        tvSeeAll = (TextView)v.findViewById(R.id.tv_see_all);

        tvSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.openCarouselFragment("Tv");
            }
        });

        tvTopRated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.openTopRatedTVFragment();
            }
        });

        tvOnTheAir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.openOnTheAirTVFragment();
            }
        });

        tvAiringToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.openAiringTodayTVFragment();
            }
        });

        tvTVByGenre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.openTVGenreListFragment();
            }
        });
        return v ;
    }

    private void fetchMostPopularTV() {

        AsyncHttpClient client = new AsyncHttpClient() ;
        client.get("http://api.themoviedb.org/3/tv/popular?api_key=98e6d988c1f0793f75610352a871a7e7", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("Hellyeahtv", "Reached fetchPopularTV success");
                String response = "";
                try {
                    response = new String(responseBody, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                int cnt = 0;
                try {
                    JSONObject jsonData = new JSONObject(response);
                    //    Log.d("Hellyeah" , jsonData.toString()) ;
                    JSONArray jsonArray = jsonData.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject ob = jsonArray.getJSONObject(i);
                        cnt++;
                        //    Log.i("Hellyeah", ob.getString("poster_path") + "\n") ;
                        mostPopularTVShowsList.add(ob.getString("poster_path"));
                    }

                } catch (Exception e) {
                    Log.d("Hellyeahtv", "Reached fetchPopularTV success catch");
                    e.printStackTrace();
                }
                mostPopularTvAdapter.notifyDataSetChanged();
                Log.d("Hellyeahtv", "Reached fetchPopularTV success end " + cnt);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Hellyeahtv", "fetchPopularTV failed " + error);
            }
        });
    }
}
