package com.example.avinash.moviebaaz;

import android.content.SharedPreferences;
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

import cz.msebera.android.httpclient.Header;

/**
 * Created by avinash on 23/4/16.
 */
public class FavouritesFragment extends BaseFragment {

    private RecyclerView rvFavourites ;
    private ArrayList<String> favouriteList = new ArrayList<>() ;
    private MainActivity parentActivity ;
    private FavouriteAdapter favouriteAdapter;
    private LinearLayoutManager rvLayoutManager ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.favourites , container , false) ;
        parentActivity = (MainActivity)getActivity();
        rvFavourites = (RecyclerView)v.findViewById(R.id.rv_favourite);
        rvLayoutManager = new LinearLayoutManager(parentActivity) ;
        rvLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvFavourites.setLayoutManager(rvLayoutManager);
        fetchFavouriteList();
        Log.d("Hellyeah favoncreate", "" + favouriteList.size());
//        favouriteList.add("http://image.tmdb.org/t/p/w500/udKOK1kE3wJzpjrCfhmuMC6M7lC.jpg");
//        favouriteList.add("http://image.tmdb.org/t/p/w500/aUV2ujhVT7cQHu4IlwnCBN9OQvN.jpg");
        favouriteAdapter = new FavouriteAdapter(favouriteList , parentActivity);
        rvFavourites.setAdapter(favouriteAdapter);
        for(int i = 0 ; i < favouriteList.size() ; i++ ) {
            Log.d("Hellyeah", ""+ favouriteList.get(i) +" ") ;
        }
        return v ;
    }

    public void fetchFavouriteList() {

        String sessionId = "" ;
        sessionId = parentActivity.sessionID ;
        Log.d("Hellyeah", "Reached faav " +sessionId) ;
        AsyncHttpClient client = new AsyncHttpClient() ;
        client.get( "http://api.themoviedb.org/3/account/id/favorite/movies?api_key=98e6d988c1f0793f75610352a871a7e7&session_id=" + sessionId, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("Hellyeah", "Reached faav success" ) ;
                String response ="" ;
                try {
                    response = new String(responseBody , "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                try {
                    JSONObject jsonData = new JSONObject(response);
                //    Log.d("Hellyeah" , jsonData.toString()) ;
                    JSONArray jsonArray = jsonData.getJSONArray("results");
                    for( int i = 0 ; i < jsonArray.length() ; i++) {
                        JSONObject ob = jsonArray.getJSONObject(i);
                    //    Log.i("Hellyeah", ob.getString("poster_path") + "\n") ;
                        favouriteList.add(ob.getString("poster_path"));
                    }

                }catch (Exception e) {
                    Log.d("Hellyeah", "Reached faav success catch" ) ;
                    e.printStackTrace();
                }
                favouriteAdapter.notifyDataSetChanged();
                Log.d("Hellyeah", "Reached faav success end ") ;
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Hellyeah favoritesFrag" , "failed " + error ) ;
            }
        });
    }
}
