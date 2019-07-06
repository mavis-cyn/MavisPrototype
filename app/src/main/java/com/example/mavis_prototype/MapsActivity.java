package com.example.mavis_prototype;

import java.util.List;
import java.util.ArrayList;

import android.os.Bundle;
import androidx.core.view.MenuItemCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, AdapterView.OnItemSelectedListener {

    private GoogleMap mMap;

    //restricting camera view within NUS
    private static final LatLngBounds NUSBound = new LatLngBounds(new LatLng(1.285312, 103.766594), new LatLng(1.306341, 103.785059));

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

        //Spinner element
        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);

        // Spinner click listener
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        //Creating adapter for spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Attaching data adapter to spinner
        spinner.setAdapter(adapter);
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        String selectedCategory = parent.getItemAtPosition(pos).toString();

        //when a category is selected, make the markers in the category visible and the markers in other categories invisible.
        if (selectedCategory.equals("Food and Beverages")){
            for (Marker m : food_and_beverage_list){
                m.setVisible(true);
            }
            for (Marker m : study_spots_list){
                m.setVisible(false);
            }
            for (Marker m : bus_stops_list){
                m.setVisible(false);
            }
        }
        else if (selectedCategory.equals("Study Spots")){
            for (Marker m : study_spots_list){
                m.setVisible(true);
            }
            for (Marker m : food_and_beverage_list){
                m.setVisible(false);
            }
            for (Marker m : bus_stops_list){
                m.setVisible(false);
            }
        }
        else if (selectedCategory.equals("Bus Stops")){
            for (Marker m : bus_stops_list){
                m.setVisible(true);
            }
            for (Marker m : food_and_beverage_list){
                m.setVisible(false);
            }
            for (Marker m : study_spots_list){
                m.setVisible(false);
            }
        }
        else {
            for (Marker m : food_and_beverage_list){
                m.setVisible(true);
            }
            for (Marker m : study_spots_list){
                m.setVisible(true);
            }
            for (Marker m : bus_stops_list){
                m.setVisible(true);
            }
        }

        // Constrain the camera target to the NUS bounds.
        mMap.setLatLngBoundsForCameraTarget(NUSBound);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
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

    //Location permission
    private boolean mLocationPermissionGranted = false;

    //NUS marker - for app to open and zoom into NUS campus
    private static final LatLng NUS = new LatLng(1.296739, 103.776372);

    //NUS Locations: Food and Beverages
    private static final LatLng DECK = new LatLng(1.294713, 103.772480);
    private static final LatLng SCIENCE_FRONTIER = new LatLng(1.296779, 103.780629);
    private static final LatLng PLATYPUS_FOODBAR = new LatLng(1.296847, 103.780326);

    //NUS Locations: Study Spots
    private static final LatLng CENTRAL_LIBRARY = new LatLng(1.297203, 103.773229);
    private static final LatLng YALE_NUS_LIBRARY = new LatLng(1.307230, 103.772545);
    private static final LatLng EDUCATION_RESOURCE_CENTRE = new LatLng(1.305711, 103.772798);

    //Markers variables
    private Marker mNUS;

    //Markers variable: Food and Beverages
    private Marker mDeck;
    private Marker mFrontier;
    private Marker mPlatypus;

    //Markers variable: Study Spots
    private Marker mCentralLib;
    private Marker mYaleLib;
    private Marker mERC;

    //Initialise marker array list to store markers of each category
    List<Marker> food_and_beverage_list = new ArrayList<>();
    List<Marker> study_spots_list = new ArrayList<>();
    List<Marker> bus_stops_list = new ArrayList<>();

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(MapsActivity.this));

        // Add a marker in NUS and move the camera to a specific zoom level to show NUS campus
        mNUS = mMap.addMarker(new MarkerOptions()
                .position(NUS)
                .title("Marker in NUS"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(NUS, 15));
        // Set NUS marker to invisible
        mNUS.setVisible(false);

        //add NUS markers to map
        //Food and Beverages category
        mDeck = mMap.addMarker(new MarkerOptions()
                .position(DECK)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .title("Deck")
                .snippet("FASS canteen"));
        InfoWindowData info_deck = new InfoWindowData();
        info_deck.setImage("deck");
        info_deck.setOpeningHours("Opening hours: 9am - 6pm");
        info_deck.setBusStops("Bus Stops: AS5");
        CustomInfoWindowAdapter customInfoWindowDeck = new CustomInfoWindowAdapter(this);
        mMap.setInfoWindowAdapter(customInfoWindowDeck);
        mDeck.setTag(info_deck);
        mDeck.showInfoWindow();

        mFrontier = mMap.addMarker(new MarkerOptions()
                .position(SCIENCE_FRONTIER)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .title("Science Frontier")
                .snippet("Science Canteen"));
        InfoWindowData info_frontier = new InfoWindowData();
        info_frontier.setImage("frontier");
        info_frontier.setOpeningHours("Opening hours: add opening hours");
        info_frontier.setBusStops("Bus Stops: LT27");
        CustomInfoWindowAdapter customInfoWindowFrontier = new CustomInfoWindowAdapter(this);
        mMap.setInfoWindowAdapter(customInfoWindowFrontier);
        mFrontier.setTag(info_frontier);
        mFrontier.showInfoWindow();

        mPlatypus = mMap.addMarker(new MarkerOptions()
                .position(PLATYPUS_FOODBAR)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .title("Platypus Foodbar")
                .snippet("Add snippet for platypus"));
        InfoWindowData info_platypus = new InfoWindowData();
        info_platypus.setImage("platypus");
        info_platypus.setOpeningHours("Opening hours: add opening hours");
        info_platypus.setBusStops("Bus Stops: LT27");
        CustomInfoWindowAdapter customInfoWindowPlatypus = new CustomInfoWindowAdapter(this);
        mMap.setInfoWindowAdapter(customInfoWindowPlatypus);
        mPlatypus.setTag(info_platypus);
        mPlatypus.showInfoWindow();

        //Study Spots category
        mCentralLib = mMap.addMarker(new MarkerOptions()
                .position(CENTRAL_LIBRARY)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))
                .title("Central Library")
                .snippet("how many floors etc"));
        InfoWindowData info_central_lib = new InfoWindowData();
        info_central_lib.setImage("central_library");
        info_central_lib.setOpeningHours("Opening hours: add opening hours");
        info_central_lib.setBusStops("Bus Stops: Central Library");
        CustomInfoWindowAdapter customInfoWindowCentralLib = new CustomInfoWindowAdapter(this);
        mMap.setInfoWindowAdapter(customInfoWindowCentralLib);
        mCentralLib.setTag(info_central_lib);
        mCentralLib.showInfoWindow();

        mYaleLib = mMap.addMarker(new MarkerOptions()
                .position(YALE_NUS_LIBRARY)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))
                .title("Yale-NUS Library")
                .snippet("Library in Yale NUS"));
        InfoWindowData info_yale_lib = new InfoWindowData();
        info_yale_lib.setImage("yale_lib");
        info_yale_lib.setOpeningHours("Opening hours: add opening hours");
        info_yale_lib.setBusStops("Bus Stops: University Town");
        CustomInfoWindowAdapter customInfoWindowYaleLib = new CustomInfoWindowAdapter(this);
        mMap.setInfoWindowAdapter(customInfoWindowYaleLib);
        mYaleLib.setTag(info_yale_lib);
        mYaleLib.showInfoWindow();

        mERC = mMap.addMarker(new MarkerOptions()
                .position(EDUCATION_RESOURCE_CENTRE)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))
                .title("Education Resource Centre")
                .snippet("Holds 3 computer commons: Collaborative Commons, Mac Commons and PC Commons"));
        InfoWindowData info_erc = new InfoWindowData();
        info_erc.setImage("erc");
        info_erc.setOpeningHours("Opening hours: add opening hours");
        info_erc.setBusStops("Bus Stops: University Town");
        CustomInfoWindowAdapter customInfoWindowERC = new CustomInfoWindowAdapter(this);
        mMap.setInfoWindowAdapter(customInfoWindowERC);
        mERC.setTag(info_erc);
        mERC.showInfoWindow();

        //Adding marker to array list according to category
        food_and_beverage_list.add(mDeck);
        food_and_beverage_list.add(mFrontier);
        food_and_beverage_list.add(mPlatypus);
        study_spots_list.add(mCentralLib);
        study_spots_list.add(mYaleLib);
        study_spots_list.add(mERC);

        //set markers in the list to be invisible until a category is chosen
        for (Marker m : food_and_beverage_list) {
            m.setVisible(false);
        }
        for (Marker m : study_spots_list) {
            m.setVisible(false);
        }
        for (Marker m : food_and_beverage_list) {
            m.setVisible(false);
        }
    }
}
