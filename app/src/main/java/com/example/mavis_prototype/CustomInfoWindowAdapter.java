package com.example.mavis_prototype;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private Context context;

    public CustomInfoWindowAdapter(Context c) {
        context = c;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity)context).getLayoutInflater().inflate(R.layout.marker_info_window, null);
        TextView tv_name = view.findViewById(R.id.name);
        TextView tv_description = view.findViewById(R.id.description);
        ImageView img = view.findViewById(R.id.pic);
        TextView tv_opening_hours = view.findViewById(R.id.opening_hours);
        TextView tv_bus_stops = view.findViewById(R.id.bus_stops);

        tv_name.setText(marker.getTitle());
        tv_description.setText(marker.getSnippet());

        InfoWindowData infoWindowData = (InfoWindowData) marker.getTag();

        int imageId = context.getResources().getIdentifier(infoWindowData.getImage().toLowerCase(),"drawable",context.getPackageName());
        img.setImageResource(imageId);

        tv_opening_hours.setText(infoWindowData.getOpeningHours());
        tv_bus_stops.setText(infoWindowData.getBusStops());

        return view;
    }

    /*
    private final View mWindow;
    private Context mContext;

    public CustomInfoWindowAdapter(Context context) {
        mContext = context;
        mWindow = LayoutInflater.from(context).inflate(R.layout.marker_info_window, null);
    }

    private void rendowWindowText(Marker marker, View view) {
        String title = marker.getTitle();
        TextView tvTitle = (TextView) view.findViewById(R.id.title);

        if (!title.equals("")) {
            tvTitle.setText(title);
        }

        String snippet = marker.getSnippet();
        TextView tvSnippet = (TextView) view.findViewById(R.id.snippet);

        if (!snippet.equals("")) {
            tvSnippet.setText(snippet);
        }
    }

    @Override
    public View getInfoWindow(Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
    }
    */
}
