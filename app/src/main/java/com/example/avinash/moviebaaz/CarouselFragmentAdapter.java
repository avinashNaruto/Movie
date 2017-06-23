package com.example.avinash.moviebaaz;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by avinash on 8/5/16.
 */
public class CarouselFragmentAdapter extends FragmentStatePagerAdapter {


    private String tabTitlesMovies[] = new String[] { "Upcoming Movies ", "Top Rated Movies" };
    private String tabTitlesTv[] = new String[] { "Top Rated", "On The Air" , "Airing Today    "};
    private String type;

    public CarouselFragmentAdapter(FragmentManager fm , String type) {
        super(fm);
        this.type = type;
    }

    @Override
    public Fragment getItem(int position) {
        CommonFragment fragment = new CommonFragment();
        Bundle bundle = new Bundle();
        if(type.equals("Movies")) {
            bundle.putString("fragment", "MoviesAdapter");
        } else {
            bundle.putString("fragment", "TvAdapter");
        }
        bundle.putInt("position" , position % 2);
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public int getCount() {
        if(type.equals("Movies")) {
            return 2;
        } else {
            return 3;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(type.equals("Movies")) {
            return tabTitlesMovies[position ];
        }else {
            return tabTitlesTv[position];
        }
    }
}
