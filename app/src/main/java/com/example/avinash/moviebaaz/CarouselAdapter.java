package com.example.avinash.moviebaaz;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.avinash.moviebaaz.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by avinash on 27/4/16.
 */
public class CarouselAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater inflater;
    ArrayList<String> imageURL = new ArrayList<>();
    private static String BASE_URL = "http://image.tmdb.org/t/p/w500/" ;
    private int size ;

    /*public RealCount realCount;

    public interface RealCount {
        public int getRealCount();
    }

    public void setRealCount(RealCount realCount) {
        this.realCount = realCount;
    }*/

    public CarouselAdapter(Context context ,ArrayList<String> imageURL ) {
        this.imageURL = imageURL;
        this.context = context ;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
    }

    @Override
    public int getCount() {
        /*if(realCount != null) {
           int x =  realCount.getRealCount();
            return x;
        } else {
            return 20;
        }*/
        return 1000;
    }

    @Override
    public Object instantiateItem(ViewGroup parent, int position) {
        View v = inflater.inflate(R.layout.carousel_item_view , parent , false);
        ImageView ivCarouselImage = (ImageView)v.findViewById(R.id.iv_top_rated_image);
        position = position % 20;
        DisplayMetrics displaymetrics = context.getResources().getDisplayMetrics();
        float width = displaymetrics.widthPixels;
        float height = (281.0f/500.0f) *width;
        Log.d("CheckingBUBU" , "" + width);
        Log.d("CheckingBUBU" , "" + height);


        try {
            String url = BASE_URL + imageURL.get(position) ;
            Log.d("Hell carousel succ",  url);
            Picasso.with(context).load(url).resize((int)width, (int)height).into(ivCarouselImage);

        } catch (Exception e) {
            Log.d("CheckingBUBU Load", e.toString());
        }
        parent.addView(v);
        return v ;

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (LinearLayout)object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }

    final Handler handler = new Handler();
    public Timer timer;

    public void setTimer(final ViewPager viewPager, int seconds) {
        final int size = getCount();
        Log.d("HellyeahSize" , size+"");
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
