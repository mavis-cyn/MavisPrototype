package com.example.mavis_prototype;

import java.util.List;
import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.spinner_menu, menu);

        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        return true;


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    //NUS Locations (Canteen)
    private static final LatLng DECK = new LatLng(1.294713, 103.772480);
    private static final LatLng SCIENCE_FRONTIER = new LatLng(1.296779, 103.780629);
    private static final LatLng PLATYPUS_FOODBAR = new LatLng(1.296847, 103.780326);

    private Marker mDeck;
    private Marker mFrontier;
    private Marker mPlatypus;

    //Initialise marker array list
    List<Marker> markersList = new ArrayList<>();

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in NUS and move the camera
        LatLng NUS = new LatLng(1.296739, 103.776372);
        mMap.addMarker(new MarkerOptions().position(NUS).title("Marker in NUS"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(NUS));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(NUS, 15));

        //add NUS markers to map and set a tag by category for each marker.
        mDeck = mMap.addMarker(new MarkerOptions()
                .position(DECK)
                .title("Deck"));
        mDeck.setTag("Food and Beverages");

        mFrontier = mMap.addMarker(new MarkerOptions()
                .position(SCIENCE_FRONTIER)
                .title("Science Frontier"));
        mFrontier.setTag("Food and Beverages");

        mPlatypus = mMap.addMarker(new MarkerOptions()
                .position(PLATYPUS_FOODBAR)
                .title("Platypus Foodbar"));
        mPlatypus.setTag("Food and Beverages");

        //Adding marker to array list
        markersList.add(mDeck);
        markersList.add(mFrontier);
        markersList.add(mPlatypus);

        //set markers in the list to be invisible until a category is chosen
        for (Marker m : markersList) {
            m.setVisible(false);
        }
    }
}
