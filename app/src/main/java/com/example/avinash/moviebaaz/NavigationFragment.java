package com.example.avinash.moviebaaz;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.avinash.moviebaaz.R;

import java.util.ArrayList;

/**
 * Created by avinash on 30/4/16.
 */
public class NavigationFragment extends BaseFragment {

    private ArrayList<String> navigationList = new ArrayList<>();
    private NavigationAdapter navigationAdapter;
    private ListView lvNavigation ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentActivity = (MainActivity)getActivity();
        View v = inflater.inflate(R.layout.navigation_widget ,container , false);
        lvNavigation = (ListView)v.findViewById(R.id.lv_navigation_list);
        navigationList.add("Home") ;
        navigationList.add("Movies");
        navigationList.add("TV");
        navigationList.add("Celebs");
        navigationList.add("News");
        navigationAdapter = new NavigationAdapter(navigationList , parentActivity.getApplicationContext());
        navigationAdapter.setOnNavigationItemcClick(new NavigationAdapter.OnNavigationItemClick() {
            @Override
            public void onClick(int position) {
                if(position == 1) {
                    parentActivity.openMoviesFragment();
                    parentActivity.navigationDrawer.closeDrawer(GravityCompat.START);
                }
                if(position == 2) {
                    parentActivity.openTVFragment();
                    parentActivity.navigationDrawer.closeDrawer(GravityCompat.START);
                }
                if(position == 0 ){
                    parentActivity.reloadHomePage();
                }
            }
        });
        lvNavigation.setAdapter(navigationAdapter);
        navigationAdapter.notifyDataSetChanged();
        return v;
    }
}
