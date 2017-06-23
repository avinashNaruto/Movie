package com.example.avinash.moviebaaz;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avinash.moviebaaz.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * Created by avinash on 3/5/16.
 */
public class GenreListFragment extends BaseFragment {

    private RecyclerView rvMoviesByGenre;
    private ArrayList<String> genreNamesList = new ArrayList<>();
    private ArrayList<String> genreIdList = new ArrayList<>();
    private GenreListAdapter genreListAdapter;
    private Map<String , String > genreNameId = new HashMap<>();
    private LinearLayoutManager rvlinearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentActivity=(MainActivity)getActivity();
        View v = inflater.inflate(R.layout.movies_by_genre_widget , container , false);
        rvMoviesByGenre = (RecyclerView)v.findViewById(R.id.rv_movies_by_genre);
        rvlinearLayoutManager = new LinearLayoutManager(parentActivity);
        rvlinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMoviesByGenre.setLayoutManager(rvlinearLayoutManager);
        genreListAdapter = new GenreListAdapter(parentActivity.getApplicationContext() , genreNamesList);
        rvMoviesByGenre.setAdapter(genreListAdapter);
        Bundle bundle = getArguments();
        String fragment = bundle.getString("fragment");
        if(fragment.equals("TVGenreListFragment")) {
            fetchTVByGenre();
        } else  if(fragment.equals("GenreListFragment")){
            fetchMoviesByGenre();
        }

        genreListAdapter.setOnGenreClick(new GenreListAdapter.OnGenreClick() {
            @Override
            public void onClick(int position) {
                parentActivity.openMoviesByGenreFragment(genreIdList.get(position));
            }
        });
        return v;
    }

    private void fetchMoviesByGenre() {

        AsyncHttpClient client = new AsyncHttpClient() ;
        client.get("http://api.themoviedb.org/3/genre/movie/list?api_key=98e6d988c1f0793f75610352a871a7e7", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("Hellyeah", "Reached fetchMoviesByGenre success");
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
                    JSONArray jsonArray = jsonData.getJSONArray("genres");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject ob = jsonArray.getJSONObject(i);
                        cnt++;
                        //    Log.i("Hellyeah", ob.getString("poster_path") + "\n") ;
                        genreNamesList.add(ob.getString("name"));
                        genreIdList.add(ob.getString("id"));
                        //genreNameId.put(ob.getString("name"), ob.getString("id"));
                    }

                } catch (Exception e) {
                    Log.d("Hellyeah", "Reached fetchMoviesByGenre success catch");
                    e.printStackTrace();
                }
                genreListAdapter.notifyDataSetChanged();
                Log.d("Hellyeahcnt", "Reached fetchMoviesByGenre success end " + cnt);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Hellyeah ", "fetchMoviesByGenre failed " + error);
            }
        });
    }

    private void fetchTVByGenre() {

        AsyncHttpClient client = new AsyncHttpClient() ;
        client.get("http://api.themoviedb.org/3/genre/tv/list?api_key=98e6d988c1f0793f75610352a871a7e7", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("Hellyeah", "Reached fetchMoviesByGenre success");
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
                    JSONArray jsonArray = jsonData.getJSONArray("genres");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject ob = jsonArray.getJSONObject(i);
                        cnt++;
                        //    Log.i("Hellyeah", ob.getString("poster_path") + "\n") ;
                        genreNamesList.add(ob.getString("name"));
                        genreIdList.add(ob.getString("id"));
                      //  genreNameId.put(ob.getString("name") , ob.getString("id"));
                    }

                } catch (Exception e) {
                    Log.d("Hellyeah", "Reached fetchMoviesByGenre success catch");
                    e.printStackTrace();
                }
                genreListAdapter.notifyDataSetChanged();
                Log.d("Hellyeahcnt", "Reached fetchMoviesByGenre success end " +cnt);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Hellyeah ", "fetchMoviesByGenre failed " + error);
            }
        });
    }
}
