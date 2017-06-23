package com.example.avinash.moviebaaz;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.avinash.moviebaaz.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;

/**
 * Created by avinash on 15/5/16.
 */
public class DetailsFragment extends BaseFragment {

    private WebView wvTrailer ;
    public TextView tvName;
    public ImageView ivImage;
    private TextView tvRating;
    private TextView tvDetails;
    private ProgressDialog progressDialog;
    private static String BASE_URL = "http://image.tmdb.org/t/p/w500/" ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentActivity = (MainActivity)getActivity();
        View v = inflater.inflate(R.layout.details_widget , container , false);
        Bundle bundle  = getArguments();
        String id = bundle.getString("id");
        String type = bundle.getString("type");
        if(type.equals("Movies")) {
            fetchMoviesDetails(id);
        } else {
            fetchTvDetails(id);
        }
        wvTrailer = (WebView)v.findViewById(R.id.wv_trailer);
        tvName = (TextView)v.findViewById(R.id.tv_top_rated_text);
        ivImage = (ImageView)v.findViewById(R.id.iv_top_rated_image);
        tvRating = (TextView)v.findViewById(R.id.tv_rating);
        tvDetails = (TextView)v.findViewById(R.id.tv_details);
        progressDialog = ProgressDialog.show(getActivity() , "" , "Wait Madi" , false);

        WebSettings webSettings = wvTrailer.getSettings();
        webSettings.setJavaScriptEnabled(true);
        WebChromeClient mWebChromeClient = new WebChromeClient();
        wvTrailer.setWebChromeClient(mWebChromeClient);

//        progressDialog.show();
        return v;
    }

    public void pauseVideo() {
        wvTrailer.onPause();;
    }

    public void fetchMoviesDetails(final String id ) {

        String sessionId = "" ;
        sessionId = parentActivity.sessionID ;
        Log.d("Hellyeah", "Reached faav " +sessionId) ;
        AsyncHttpClient client = new AsyncHttpClient() ;
        client.get( "http://api.themoviedb.org/3/movie/" +id +"?api_key=98e6d988c1f0793f75610352a871a7e7", new AsyncHttpResponseHandler() {
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
                    //    Log.i("Hellyeah", ob.getString("poster_path") + "\n") ;
                    tvName.setText(jsonData.getString("original_title"));
                    tvRating.setText(jsonData.getString("vote_average"));
                    tvDetails.setText(jsonData.getString("overview"));
                    Picasso.with(getActivity()).load(BASE_URL + jsonData.getString("poster_path")).into(ivImage);
                    fetchMovieTrailerVideo(id);

                }catch (Exception e) {
                    Log.d("Hellyeah", "Reached faav success catch" ) ;
                    e.printStackTrace();
                   // progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Hellyeah favoritesFrag" , "failed " + error ) ;
                progressDialog.dismiss();

            }
        });
    }

    public void fetchMovieTrailerVideo(String id ) {

        String sessionId = "" ;
        sessionId = parentActivity.sessionID ;
        Log.d("Hellyeah", "Reached faav " +sessionId) ;
        AsyncHttpClient client = new AsyncHttpClient() ;
        client.get( "http://api.themoviedb.org/3/movie/" +id +"/videos?api_key=98e6d988c1f0793f75610352a871a7e7", new AsyncHttpResponseHandler() {
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
                    Log.d("Yo" , jsonData.toString());
                    //    Log.d("Hellyeah" , jsonData.toString()) ;
                    //    Log.i("Hellyeah", ob.getString("poster_path") + "\n") ;
                    JSONArray jsonArray = jsonData.getJSONArray("results");
                    if(jsonArray != null) {
                        JSONObject ob = jsonArray.getJSONObject(0);
                        String playVideo = "<html><body><iframe class=\"youtube-player\" type=\"text/html\" width=\"340\" height=\"400\" src=\"http://www.youtube.com/embed/" + ob.getString("key") + "\" frameborder=\"0\"></body></html>";

                        wvTrailer.loadData(playVideo, "text/html", "utf-8");
                    }

                    progressDialog.dismiss();

                }catch (Exception e) {
                    Log.d("Yo", "Reached faav success catch " +e) ;
                    e.printStackTrace();
                    progressDialog.dismiss();
                    // progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Hellyeah favoritesFrag" , "failed " + error ) ;
                progressDialog.dismiss();

            }
        });
    }

    public void fetchTvDetails(String id ) {

        String sessionId = "" ;
        sessionId = parentActivity.sessionID ;
        Log.d("Hellyeah", "Reached faav " +sessionId) ;
        AsyncHttpClient client = new AsyncHttpClient() ;
        client.get( "http://api.themoviedb.org/3/tv/" +id +"?api_key=98e6d988c1f0793f75610352a871a7e7", new AsyncHttpResponseHandler() {
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
                    //    Log.i("Hellyeah", ob.getString("poster_path") + "\n") ;
                    tvName.setText(jsonData.getString("original_name"));
                    tvRating.setText(jsonData.getString("vote_average"));
                    tvDetails.setText(jsonData.getString("overview"));
                    Picasso.with(getActivity()).load(BASE_URL + jsonData.getString("poster_path")).into(ivImage);
                    progressDialog.dismiss();

                }catch (Exception e) {
                    Log.d("Hellyeah", "Reached faav success catch" ) ;
                    e.printStackTrace();
                    // progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Hellyeah favoritesFrag" , "failed " + error ) ;
                progressDialog.dismiss();

            }
        });
    }

}
