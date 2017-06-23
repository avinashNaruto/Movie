package com.example.avinash.moviebaaz;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.avinash.moviebaaz.R;

import java.util.ArrayList;

/**
 * Created by avinash on 30/4/16.
 */
public class NavigationAdapter extends BaseAdapter {

    private ArrayList<String> navigationList = new ArrayList<>();
    private Context context;

    public OnNavigationItemClick onNavigationItemClick;

    public interface OnNavigationItemClick {
        public void onClick(int position);
    }

    public void setOnNavigationItemcClick(OnNavigationItemClick onNavigationItemcClick) {
        this.onNavigationItemClick = onNavigationItemcClick;
    }

    public NavigationAdapter(ArrayList<String> navigationList , Context context) {
        this.navigationList = navigationList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return navigationList.size();
    }

    @Override
    public String getItem(int position) {
        Log.d("HellyeahBase" , navigationList.get(position));
        return navigationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            Log.d("HellyeahN", "called " +position);
            LayoutInflater inflater  = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.navigation_item_view , null);
        }
        TextView tvNavigation = (TextView)convertView.findViewById(R.id.tv_navigation_item);
        tvNavigation.setText(navigationList.get(position));
        tvNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNavigationItemClick != null) {
                    onNavigationItemClick.onClick(position);
                }
            }
        });
        return convertView;
    }
}
