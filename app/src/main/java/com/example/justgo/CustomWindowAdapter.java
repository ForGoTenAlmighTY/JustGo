package com.example.justgo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;


public class CustomWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View window;
    private Context mContext;

    public CustomWindowAdapter(Context context) {
        mContext = context;
        window = LayoutInflater.from(context).inflate(R.layout.customwindow, null);
    }

    private void renderwindowtext(Marker marker, View view) {
        String title = marker.getTitle();
        TextView txTittle = view.findViewById(R.id.tittle);
        if (!title.equals("")) {
            txTittle.setText(title);
        }

        String snippet = marker.getSnippet();
        TextView txSnippet = view.findViewById(R.id.snippet);
        if (!snippet.equals("")) {
            txSnippet.setText(snippet);
        }
    }

    @Override
    public View getInfoContents(@NonNull Marker marker) {
        renderwindowtext(marker, window);
        return window;
    }


    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        renderwindowtext(marker, window);
        return window;
    }
}
