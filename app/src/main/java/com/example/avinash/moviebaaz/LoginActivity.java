package com.example.avinash.moviebaaz;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avinash.moviebaaz.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;

/**
 * Created by avinash on 21/4/16.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText etUsername ;
    private EditText etPassword ;
    private Button btLogin ;
    private String username ;
    private String password ;
    private ProgressDialog progressDialog ;
    public static final String PREFS_LOGIN = "shared_login" ;
    public LoginActivity loginActivity ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences editor = getSharedPreferences(PREFS_LOGIN , 0) ;
        String checkLogin = editor.getString("isLoggedIn" , "missing") ;
        if(checkLogin.equals("true")) {
            callMainActivity();
        }
        etUsername = (EditText)findViewById(R.id.et_login_email_phonenumber);
        etPassword = (EditText)findViewById(R.id.et_password) ;
        btLogin = (Button)findViewById(R.id.bt_login) ;
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = etUsername.getText().toString();
                password = etPassword.getText().toString();
                progressDialog = ProgressDialog.show(LoginActivity.this, "" , "Fucking wait u bitch......" , false);
                requestLogin() ;
            }
        });
    }
    private void requestLogin() {

        AsyncHttpClient client = new AsyncHttpClient() ;
        client.get(LoginActivity.this, "http://api.themoviedb.org/3/authentication/token/new?api_key=98e6d988c1f0793f75610352a871a7e7", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = "";
                try {
                    response = new String(responseBody, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                try {
                    JSONObject jsonData = new JSONObject(response);
                    Log.i("Hellyeah", jsonData.getString("request_token"));
                    String requestToken = jsonData.getString("request_token");
                    requestAuthentication(requestToken);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
    private void requestAuthentication(final String requestToken) {

        AsyncHttpClient client = new AsyncHttpClient() ;
        client.get(LoginActivity.this, "http://api.themoviedb.org/3/authentication/token/validate_with_login?api_key=98e6d988c1f0793f75610352a871a7e7&request_token=" + requestToken + "&username=" + username + "&password=" + password, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String response = "";
                try {
                    response = new String(responseBody, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                try {
                    JSONObject jsonData = new JSONObject(response);
                    Log.i("Hellyeah periods", jsonData.getString("request_token"));
                    String callbackRequestToken = jsonData.getString("request_token");
                    if(requestToken.equals(callbackRequestToken)) {
                        requestSessionId(requestToken);
                    } else {
                        Toast.makeText(LoginActivity.this , "Fuck you moron" , Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        }) ;
    }

    private void requestSessionId(final String requestToken) {
        AsyncHttpClient client = new AsyncHttpClient() ;
        client.get(LoginActivity.this, "http://api.themoviedb.org/3/authentication/session/new?api_key=98e6d988c1f0793f75610352a871a7e7&request_token=" + requestToken, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = "";
                try {
                    response = new String(responseBody, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                try {
                    JSONObject jsonData = new JSONObject(response);
                    Log.i("Hellyeah sessionId", jsonData.getString("session_id"));
                    String sessionId = jsonData.getString("session_id");
                    progressDialog.dismiss();
                    SharedPreferences.Editor editor = getSharedPreferences(PREFS_LOGIN , 0 ).edit() ;
                    editor.putString("isLoggedIn" , "true") ;
                    editor.putString("sessionId" , sessionId) ;
                    editor.commit() ;
                    callMainActivity();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        }) ;
    }

    public void callMainActivity() {
        Intent mainActivityIntent = new Intent(LoginActivity.this ,MainActivity.class) ;
        SharedPreferences editor = getSharedPreferences(PREFS_LOGIN , 0 );
        String sessionId = editor.getString("sessionId" , "missing");
        mainActivityIntent.putExtra("sessionId" , sessionId) ;
        startActivity(mainActivityIntent);
        finish();
    }
}
