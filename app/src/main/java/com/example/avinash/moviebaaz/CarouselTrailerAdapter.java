package com.example.avinash.moviebaaz;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.avinash.moviebaaz.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by avinash on 1/5/16.
 */
public class CarouselTrailerAdapter extends PagerAdapter {

    String BASE_URL = "http://www.youtube.com/embed/"  ;
    private Context context;
    private LayoutInflater inflater;
    ArrayList<String> trailerURL = new ArrayList<>();

    public CarouselTrailerAdapter(ArrayList<String> trailerURL , Context context) {
        this.trailerURL = trailerURL;
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
    }

    @Override
    public int getCount() {
        return trailerURL.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (LinearLayout)object ;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = inflater.inflate(R.layout.carousel_trailer_widget ,container , false);
        String url = BASE_URL + trailerURL.get(position);
        String playVideo= "<html><body>Youtube video .. <br> <iframe class=\"youtube-player\" type=\"text/html\" width=\"640\" height=\"385\" src=\"" +url + "\" frameborder=\"0\"></body></html>";
        WebView webTrailer = (WebView)v.findViewById(R.id.wv_trailer);
        WebSettings webSettings = webTrailer.getSettings();
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webTrailer.setWebChromeClient(new WebChromeClient() {
        });
        webTrailer.loadData(playVideo, "text/html", "utf-8");
        //webTrailer.loadUrl(url);

        Log.d("Hellyeahct" , url);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }

    final Handler handler = new Handler();
    public Timer timer;

    public void setTimer(final ViewPager viewPager, int seconds) {
        final int size = getCount();
        Log.d("HellyeahSize", size + "");
        final Runnable update = new Runnable() {
            int numberOfPages = size;
            int currentPage = 0 ;
            @Override
            public void run() {
                currentPage = viewPager.getCurrentItem();
                if (currentPage == numberOfPages - 1) {
                    currentPage = -1;
                }
                Log.d("Hellyeah page" , currentPage +"");
                viewPager.setCurrentItem(++currentPage, true);
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, 4000, 1000 * seconds);
    }
}
