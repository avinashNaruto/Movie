package com.example.avinash.moviebaaz;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.avinash.moviebaaz.R;

public class MainActivity extends AppCompatActivity {

    public String sessionID ;
    public static String FAVOURITE_FRAGMENT_TAG = "favouritesFragment";
    public static String CAROUSEL_FRAGMENT_TAG = "carouselFragment";
    public static String HOME_PAGE_FRAGMENT_TAG = "homePageFragment";
    public static String MOVIE_FRAGMENT_TAG = "movieFragment";
    public static String TV_FRAGMENT_TAG = "tvFragment";
    public static String TV_GENRE_LIST_FRAGMENT = "tvGenreListFragment";
    public static String MOVIES_GENRE_LIST_FRAGMENT = "moviesGenreListFragment";
    public static String TOP_RATED_MOVIES_FRAGMENT = "topRatedMoviesFragment";
    public static String TOP_RATED_TV_FRAGMENT = "topRatedTVFragment";
    public static String UPCOMING_MOVIES_FRAGMENT = "upcomingMoviesFragment";
    public static String ON_THE_AIR_TV_FRAGMENT = "onTheAirTvFragment";
    public static String AIRING_TODAY_FRAGMENT = "playingNowFragment";
    public static String MOVIES_BY_GENRE = "MoviesByGenre";
    public static String DETAILS_FRAGMENT = "DetailsFragment";
    public Fragment currentFragment;
    public ActionBarDrawerToggle actionBarDrawerToggle ;
    public DrawerLayout navigationDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationDrawer = (DrawerLayout)findViewById(R.id.navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this , navigationDrawer ,R.string.spidy , R.string.spidy){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        navigationDrawer.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        // getSupportActionBar().setIcon(R.drawable.spiderman);

//        actionBar = getActionBar();
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    //www.theappguruz.com/blog/android-tab-layout-with-swipeable-views#sthash.pM6mxG3f.dpuf
       // getSupportActionBar().setHomeButtonEnabled(true);
       // navigationContainer = findViewById(R.id.drawer_layout);

//        AsyncHttpClient client = new AsyncHttpClient();
//        client.get(MainActivity.this, "http://api.themoviedb.org/3/discover/tv?api_key=98e6d988c1f0793f75610352a871a7e7", new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                String response ="" ;
//                try {
//                    response = new String(responseBody , "UTF-8");
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    JSONObject jsonData = new JSONObject(response);
//                    JSONArray jsonArray = jsonData.getJSONArray("results") ;
//                    for( int i = 0 ; i < jsonArray.length() ; i++) {
//                        JSONObject ob = jsonArray.getJSONObject(i);
//                        Log.i("Hellyeah" , ob.getString("overview") +"\n") ;
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                Log.i("Hellyeah" , "Such a pussy") ;
//            }
//        });

        Intent intent = getIntent();
        sessionID = intent.getStringExtra("sessionId");
        Log.d("Hellyeah sessionId" ,sessionID ) ;

        if (findViewById(R.id.drawer_layout) != null) {

            NavigationFragment navigationFragment = new NavigationFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.drawer_layout,navigationFragment,"NavigationFragment").commit();

        }
        openHomePageFragment() ;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {

        super.onNewIntent(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Log.d("MenuHell" , "reached");
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.favourites) {
            openFavouritesFragment();
        }
        if(id == R.id.home) {
            reloadHomePage();
            //recreate();
        }
        /*if(id == R.id.swipe_tab) {
            openCarouselFragment();
        }*/
        if(id == R.id.my_account) {
        }
        if(id == R.id.tv) {
            openTVFragment();
        }
        if(id == R.id.movies) {
            openMoviesFragment();
        }
        if(id == R.id.rated) {
            //openCarouselFragment();
        }
        if(id == R.id.log_out) {
            final AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(MainActivity.this, R.style.Base_Theme_AppCompat_Light_Dialog_Alert);
            builder.setTitle("You are Screwed") ;
            builder.setMessage("Fuck You!!!!! You can't bitch .... Its my App");
            builder.setPositiveButton("I Accept", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }) ;
            builder.setNegativeButton("Don't Accept", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(MainActivity.this, R.style.Base_Theme_AppCompat_Light_Dialog_Alert);
                    builder1.setTitle("How dare you?");
                    builder1.setMessage("Just Accept that you are fucked");
                    builder1.setPositiveButton("I Accept", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }) ;
                    builder1.show();
                }
            });
            builder.show();
        }
        return actionBarDrawerToggle.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(navigationDrawer.isDrawerOpen(GravityCompat.START)) {
            navigationDrawer.closeDrawers();
        } else {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.home_page_container);
            if (fragment instanceof HomePageFragment) {
                Log.d("homeSweetHome", "Home");
                finish();
                //System.exit(0);
            } else if (fragment instanceof DetailsFragment) {
                DetailsFragment newFragment = (DetailsFragment) fragment;
                newFragment.pauseVideo();
                super.onBackPressed();
            } else {
                super.onBackPressed();
            }
        }
    }

    public void  openFavouritesFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("fragment" , "FavouritesFragment");
        CommonFragment fragment = new CommonFragment();
        fragment.setArguments(bundle);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.home_page_container , fragment , FAVOURITE_FRAGMENT_TAG) ;
        ft.addToBackStack(FAVOURITE_FRAGMENT_TAG) ;
        ft.commit();

    }

    public void openCarouselFragment(String type) {
        Bundle bundle = new Bundle();
        bundle.putString("type" , type);
        CarouselFragment fragment = new CarouselFragment();
        fragment.setArguments(bundle);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.home_page_container , fragment , CAROUSEL_FRAGMENT_TAG);
        ft.addToBackStack(CAROUSEL_FRAGMENT_TAG);
        ft.commit();
    }

    public void openHomePageFragment() {
        HomePageFragment fragment = new HomePageFragment();
        FragmentManager fm =  getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.home_page_container , fragment ,HOME_PAGE_FRAGMENT_TAG ) ;
        ft.addToBackStack(HOME_PAGE_FRAGMENT_TAG);
        ft.commit();
    }

    public void openMoviesFragment(){
        MoviesFragment fragment = new MoviesFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.home_page_container, fragment ,MOVIE_FRAGMENT_TAG );
        ft.addToBackStack(MOVIE_FRAGMENT_TAG);
        ft.commit();
    }

    public void openTVFragment() {
        TVFragment fragment = new TVFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.home_page_container , fragment , TV_FRAGMENT_TAG);
        ft.addToBackStack(TV_FRAGMENT_TAG);
        ft.commit();
    }

    public void clearAllFragments(){

        FragmentManager fm = getSupportFragmentManager();
        if( fm.getBackStackEntryCount() > 1) {
            for( int i = 0 ; i < fm.getBackStackEntryCount() -1 ; i++) {
                fm.popBackStack();
            }
        }
    }

    public void reloadHomePage() {
        if(navigationDrawer.isDrawerOpen(GravityCompat.START)) {
            navigationDrawer.closeDrawers();
        }
        clearAllFragments();
        openHomePageFragment();
       // recreate();
    }

    public void openMoviesGenreListFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("fragment" , "GenreListFragment");
        GenreListFragment fragment = new GenreListFragment();
        fragment.setArguments(bundle);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.home_page_container , fragment , MOVIES_GENRE_LIST_FRAGMENT);
        ft.addToBackStack(MOVIES_GENRE_LIST_FRAGMENT);
        ft.commit();
    }

    public void openTVGenreListFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("fragment" , "TVGenreListFragment");
        GenreListFragment fragment = new GenreListFragment();
        fragment.setArguments(bundle);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.home_page_container , fragment , TV_GENRE_LIST_FRAGMENT);
        ft.addToBackStack(TV_GENRE_LIST_FRAGMENT);
        ft.commit();
    }

    public void openTopRatedMoviesFragment(){
        Bundle bundle  = new Bundle();
        bundle.putString("fragment" , "TopRatedMoviesFragment");
        CommonFragment fragment = new CommonFragment();
        fragment.setArguments(bundle);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.home_page_container , fragment , TOP_RATED_MOVIES_FRAGMENT );
        ft.addToBackStack(TOP_RATED_MOVIES_FRAGMENT);
        ft.commit();
    }

    public void openTopRatedTVFragment(){
        Bundle bundle  = new Bundle();
        bundle.putString("fragment" , "TopRatedTVFragment");
        CommonFragment fragment = new CommonFragment();
        fragment.setArguments(bundle);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.home_page_container , fragment , TOP_RATED_TV_FRAGMENT );
        ft.addToBackStack(TOP_RATED_TV_FRAGMENT);
        ft.commit();
    }

    public void openUpcomingMoviesFragment(){
        Bundle bundle = new Bundle();
        bundle.putString("fragment", "CommonFragment");
        CommonFragment fragment = new CommonFragment();
        fragment.setArguments(bundle);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.home_page_container , fragment , UPCOMING_MOVIES_FRAGMENT );
        ft.addToBackStack(UPCOMING_MOVIES_FRAGMENT);
        ft.commit();
    }
    public void openOnTheAirTVFragment(){
        Bundle bundle = new Bundle();
        bundle.putString("fragment" , "UpcomingTvFragment");
        CommonFragment fragment = new CommonFragment();
        fragment.setArguments(bundle);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.home_page_container , fragment , ON_THE_AIR_TV_FRAGMENT );
        ft.addToBackStack(ON_THE_AIR_TV_FRAGMENT);
        ft.commit();
    }

    public void openAiringTodayTVFragment(){
        Bundle bundle = new Bundle();
        bundle.putString("fragment" , "AiringTodayTvFragment");
        CommonFragment fragment = new CommonFragment();
        fragment.setArguments(bundle);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.home_page_container , fragment , AIRING_TODAY_FRAGMENT);
        ft.addToBackStack(AIRING_TODAY_FRAGMENT);
        ft.commit();
    }

    public void openMoviesByGenreFragment(String id) {
        Bundle bundle = new Bundle();
        bundle.putString("fragment" , "MoviesByGenreFragment");
        bundle.putString("id" , id);
        CommonFragment fragment = new CommonFragment();
        fragment.setArguments(bundle);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.home_page_container , fragment , MOVIES_BY_GENRE);
        ft.addToBackStack(MOVIES_BY_GENRE);
        ft.commit();
    }

    public void openDetailsFragment(String id , String type) {
        Bundle bundle = new Bundle();
        bundle.putString("id" , id);
        bundle.putString("type", type);
        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(bundle);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.home_page_container , fragment , DETAILS_FRAGMENT);
        ft.addToBackStack(DETAILS_FRAGMENT);
        ft.commit();
    }
}
