package com.example.avinash.moviebaaz;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.astuetz.PagerSlidingTabStrip;
import com.avinash.moviebaaz.R;

import java.util.ArrayList;

/**
 * Created by avinash on 27/4/16.
 */
public class CarouselFragment extends BaseFragment {

    private ImageView ivCarouselImage;
    private CarouselFragmentAdapter carouselAdapter;
    MainActivity parentActivity ;
    ArrayList<String> imageURL = new ArrayList<>();
    private PagerSlidingTabStrip pagerSlidingTabStrip;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v ;
        v = inflater.inflate(R.layout.carousel_fragment_widget, container , false);
        vpCarousel = (ViewPager)v.findViewById(R.id.vp_swipe_tabs);
        pagerSlidingTabStrip = (PagerSlidingTabStrip)v.findViewById(R.id.tabs);
        parentActivity = (MainActivity)getActivity();
        Bundle bundle = getArguments();
        String type = bundle.getString("type");
        carouselAdapter = new CarouselFragmentAdapter(getChildFragmentManager() , type);
//        carouselAdapter.setTimer(vpCarousel, 3);
        vpCarousel.setAdapter(carouselAdapter);
        pagerSlidingTabStrip.setViewPager(vpCarousel);

//        for (int i = 0; i < 3; i++) {
//            parentActivity.actionBar.addTab(parentActivity.actionBar.newTab().setText(i)
//                    .setTabListener(new ActionBar.TabListener() {
//                        @Override
//                        public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
//                            vpCarousel.setCurrentItem(tab.getPosition());
//
//                        }
//
//                        @Override
//                        public void onTabUnselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
//
//                        }
//
//                        @Override
//                        public void onTabReselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
//
//                        }
//                    }));
//        }
//        vpCarousel.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageSelected(int postion) {
//                parentActivity.actionBar.setSelectedNavigationItem(postion);
//            }
//            @Override
//            public void onPageScrolled(int arg0, float arg1, int arg2) {
//            }
//            @Override
//            public void onPageScrollStateChanged(int arg0) {
//            }
//        });
        return v;
    }
}
