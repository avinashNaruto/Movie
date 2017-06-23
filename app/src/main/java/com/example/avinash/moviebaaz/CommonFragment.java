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
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by avinash on 5/5/16.
 */
public class CommonFragment extends BaseFragment {

    private RecyclerView rvCommon;
    private CommmonAdapter commonAdapter;
    private ArrayList<String> namesList = new ArrayList<>();
    private ArrayList<String> imagesURl = new ArrayList<>();
    private LinearLayoutManager rvLayoutManager ;
    private ArrayList<String> ratingList = new ArrayList<>();
    private ArrayList<String> idList = new ArrayList<>();
    private boolean isMoviesCalled = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentActivity = (MainActivity)getActivity();
        View v = inflater.inflate(R.layout.rated_widget, container , false);
        rvCommon = (RecyclerView)v.findViewById(R.id.rv_top_rated);
        commonAdapter = new CommmonAdapter(parentActivity.getApplicationContext() , namesList, imagesURl , ratingList , idList);
        rvLayoutManager = new LinearLayoutManager(parentActivity) ;
        rvLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvCommon.setLayoutManager(rvLayoutManager);
        rvCommon.setAdapter(commonAdapter);

        commonAdapter.setOnLayoutClick(new CommmonAdapter.OnLayoutClick() {
            @Override
            public void onClick(String id) {
                if(isMoviesCalled) {
                    parentActivity.openDetailsFragment(id , "Movies");
                } else {
                    parentActivity.openDetailsFragment(id , "TV");
                }
            }
        });

        Bundle bundle = getArguments();
        String fragment = bundle.getString("fragment");
        if(fragment.equals("CommonFragment")) {

            for (int i = 1 ; i <= 10 ; i++) {
                fetchUpComingMoviesList(Integer.toString(i));
            }
        } else if(fragment.equals("UpcomingTvFragment")) {
            for (int i = 1 ; i <= 10 ; i++) {
                fetchOnTheAirTVList(Integer.toString(i));
            }
        } else if(fragment.equals("AiringTodayTvFragment")) {
            for (int i = 1 ; i <= 10 ; i++) {
                fetchAiringTodayTVList(Integer.toString(i));
            }
        } else if(fragment.equals("MoviesByGenreFragment")) {
            String id = bundle.getString("id");
            for (int i = 1 ; i <= 10 ; i++) {
                fetchMoviesByGenre(Integer.toString(i) , id);
            }
        } else if(fragment.equals("MoviesAdapter")) {
            int pos = bundle.getInt("position");
            if(pos == 0 ){
                for (int i = 1 ; i <= 10 ; i++) {
                    fetchUpComingMoviesList(Integer.toString(i));
                }
            } else if( pos == 1 ) {
                for (int i = 1 ; i <= 10 ; i++) {
                    fetchTopRatedMoviesList(Integer.toString(i));
                }
            }
        } else if(fragment.equals("TvAdapter")) {
            int pos = bundle.getInt("position");
            if(pos == 0 ){
                for (int i = 1 ; i <= 10 ; i++) {
                    fetchTopRatedTVList(Integer.toString(i));
                }
            } else if( pos == 1 ) {
                for (int i = 1 ; i <= 10 ; i++) {
                    fetchOnTheAirTVList(Integer.toString(i));
                }
            } else if (pos == 2 ) {
                for (int i = 1 ; i <= 10 ; i++) {
                    fetchAiringTodayTVList(Integer.toString(i));
                }
            }
        }
        else if(fragment.equals("FavouritesFragment")) {
                fetchFavouriteList();
        } else if(fragment.equals("TopRatedMoviesFragment")) {
            for (int i = 1 ; i <= 10 ; i++) {
                fetchTopRatedMoviesList(Integer.toString(i));
            }
        } else if(fragment.equals("TopRatedTVFragment")) {
            for (int i = 1 ; i <= 10 ; i++) {
                fetchTopRatedTVList(Integer.toString(i));
            }
        }
        return v;
    }

    public void fetchUpComingMoviesList(String page) {

        isMoviesCalled = true;
        String sessionId = "" ;
        sessionId = parentActivity.sessionID ;
        Log.d("Hellyeah", "Reached faav " + sessionId) ;
        AsyncHttpClient client = new AsyncHttpClient() ;
        client.get("http://api.themoviedb.org/3/movie/upcoming?api_key=98e6d988c1f0793f75610352a871a7e7&page=" +page ,new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
            }
        });
        client.get("http://api.themoviedb.org/3/movie/upcoming?api_key=98e6d988c1f0793f75610352a871a7e7&page=" +page , new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("Hellyeah", "Reached TopRatedMoviesFragment success");
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
                        idList.add(ob.getString("id"));
                        namesList.add(ob.getString("original_title"));
                        imagesURl.add(ob.getString("poster_path"));
                        ratingList.add(ob.getString("vote_average"));
                    }

                } catch (Exception e) {
                    Log.d("Hellyeah", "Reached TopRatedMoviesFragment success catch");
                    e.printStackTrace();
                }
                commonAdapter.notifyDataSetChanged();
                Log.d("Hellyeah", "Reached TopRatedMoviesFragment success end ");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Hellyeah topRatedFrag", "failed " + error);
            }
        });
    }

    public void fetchOnTheAirTVList(String page) {

        isMoviesCalled = false;
        String sessionId = "" ;
        sessionId = parentActivity.sessionID ;
        Log.d("Hellyeah", "Reached faav " + sessionId) ;
        AsyncHttpClient client = new AsyncHttpClient() ;
        client.get("http://api.themoviedb.org/3/tv/on_the_air?api_key=98e6d988c1f0793f75610352a871a7e7&page=" +page , new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("Hellyeah", "Reached TopRatedMoviesFragment success");
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
                        idList.add(ob.getString("id"));
                        namesList.add(ob.getString("original_name"));
                        imagesURl.add(ob.getString("poster_path"));
                        ratingList.add(ob.getString("vote_average"));
                    }

                } catch (Exception e) {
                    Log.d("Hellyeah", "Reached TopRatedMoviesFragment success catch");
                    e.printStackTrace();
                }
                commonAdapter.notifyDataSetChanged();
                Log.d("Hellyeah", "Reached TopRatedMoviesFragment success end ");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Hellyeah topRatedFrag", "failed " + error);
            }
        });
    }

    public void fetchAiringTodayTVList(String page) {

        isMoviesCalled = false;
        String sessionId = "" ;
        sessionId = parentActivity.sessionID ;
        Log.d("Hellyeah", "Reached faav " + sessionId) ;
        AsyncHttpClient client = new AsyncHttpClient() ;
        client.get("http://api.themoviedb.org/3/tv/airing_today?api_key=98e6d988c1f0793f75610352a871a7e7&page=" +page , new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("Hellyeah", "Reached TopRatedMoviesFragment success");
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
                        idList.add(ob.getString("id"));
                        namesList.add(ob.getString("original_name"));
                        imagesURl.add(ob.getString("poster_path"));
                        ratingList.add(ob.getString("vote_average"));

                    }

                } catch (Exception e) {
                    Log.d("Hellyeah", "Reached TopRatedMoviesFragment success catch");
                    e.printStackTrace();
                }
                commonAdapter.notifyDataSetChanged();
                Log.d("Hellyeah", "Reached TopRatedMoviesFragment success end ");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Hellyeah topRatedFrag", "failed " + error);
            }
        });
    }
    public void fetchMoviesByGenre(String page , String id) {

        isMoviesCalled = true;
        String sessionId = "" ;
        sessionId = parentActivity.sessionID ;
        Log.d("Hellyeah", "Reached faav " + sessionId) ;
        AsyncHttpClient client = new AsyncHttpClient() ;
        client.get("http://api.themoviedb.org/3/genre/" + id + "/movies?api_key=98e6d988c1f0793f75610352a871a7e7&page=" +page , new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("Hellyeah", "Reached TopRatedMoviesFragment success");
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
                        idList.add(ob.getString("id"));
                        namesList.add(ob.getString("original_title"));
                        imagesURl.add(ob.getString("poster_path"));
                        ratingList.add(ob.getString("vote_average"));

                    }

                } catch (Exception e) {
                    Log.d("Hellyeah", "Reached TopRatedMoviesFragment success catch");
                    e.printStackTrace();
                }
                commonAdapter.notifyDataSetChanged();
                Log.d("Hellyeah", "Reached TopRatedMoviesFragment success end ");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Hellyeah topRatedFrag", "failed " + error);
            }
        });
    }

    public void fetchFavouriteList() {

        isMoviesCalled =true;
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
                        idList.add(ob.getString("id"));
                        namesList.add(ob.getString("original_title"));
                        imagesURl.add(ob.getString("poster_path"));
                        ratingList.add(ob.getString("vote_average"));
                    }

                }catch (Exception e) {
                    Log.d("Hellyeah", "Reached faav success catch" ) ;
                    e.printStackTrace();
                }
                commonAdapter.notifyDataSetChanged();
                Log.d("Hellyeah", "Reached faav success end ") ;
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Hellyeah favoritesFrag" , "failed " + error ) ;
            }
        });
    }

    public void fetchLowestRatedMoviesList(String page) {


        String sessionId = "" ;
        sessionId = parentActivity.sessionID ;
        Log.d("Hellyeah", "Reached faav " + sessionId) ;
        AsyncHttpClient client = new AsyncHttpClient() ;
        client.get("http://api.themoviedb.org/3/movie/top_rated?api_key=98e6d988c1f0793f75610352a871a7e7&page=" +page , new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("Hellyeah", "Reached TopRatedMoviesFragment success");
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
                        namesList.add(ob.getString("original_title"));
                        imagesURl.add(ob.getString("poster_path"));
                        ratingList.add(ob.getString("vote_average"));
                    }

                } catch (Exception e) {
                    Log.d("Hellyeah", "Reached TopRatedMoviesFragment success catch");
                    e.printStackTrace();
                }
                commonAdapter.notifyDataSetChanged();
                Log.d("Hellyeah", "Reached TopRatedMoviesFragment success end ");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Hellyeah topRatedFrag", "failed " + error);
            }
        });
    }

    public void fetchTopRatedMoviesList(String page) {

        isMoviesCalled = true;
        String sessionId = "" ;
        sessionId = parentActivity.sessionID ;
        Log.d("Hellyeah", "Reached faav " + sessionId) ;
        AsyncHttpClient client = new AsyncHttpClient() ;
        client.get("http://api.themoviedb.org/3/movie/top_rated?api_key=98e6d988c1f0793f75610352a871a7e7&page=" +page , new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("Hellyeah", "Reached TopRatedMoviesFragment success");
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
                        idList.add(ob.getString("id"));
                        namesList.add(ob.getString("original_title"));
                        imagesURl.add(ob.getString("poster_path"));
                        ratingList.add(ob.getString("vote_average"));
                        Log.d("Hellyeahyo", "" +ob.getString("vote_average").toString());
                    }

                } catch (Exception e) {
                    Log.d("Hellyeah", "Reached TopRatedMoviesFragment success catch");
                    e.printStackTrace();
                }
                commonAdapter.notifyDataSetChanged();
                Log.d("Hellyeah", "Reached TopRatedMoviesFragment success end ");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Hellyeah topRatedFrag", "failed " + error);
            }
        });
    }

    private void fetchTopRatedTVList(String page) {

        isMoviesCalled = false;
        String sessionId = "" ;
        sessionId = parentActivity.sessionID ;
        Log.d("Hellyeah", "Reached faav " + sessionId) ;
        AsyncHttpClient client = new AsyncHttpClient() ;
        client.get("http://api.themoviedb.org/3/tv/top_rated?api_key=98e6d988c1f0793f75610352a871a7e7&page=" +page , new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("Hellyeah", "Reached TopRatedMoviesFragment success");
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
                        idList.add(ob.getString("id"));
                        namesList.add(ob.getString("original_name"));
                        imagesURl.add(ob.getString("poster_path"));
                        ratingList.add(ob.getString("vote_average"));
                    }

                } catch (Exception e) {
                    Log.d("Hellyeah", "Reached TopRatedMoviesFragment success catch");
                    e.printStackTrace();
                }
                commonAdapter.notifyDataSetChanged();
                Log.d("Hellyeah", "Reached TopRatedMoviesFragment success end ");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Hellyeah topRatedFrag", "failed " + error);
            }
        });
    }
}
