package com.example.mavis_prototype;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.MenuItemCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, AdapterView.OnItemSelectedListener {

    private GoogleMap mMap;

    private LocationListener locationListener;
    private LocationManager locationManager;
    private LatLng latLng;

    //reference to database top node
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    //restricting camera view within NUS
    private static final LatLngBounds NUSBound = new LatLngBounds(new LatLng(1.285312, 103.766594), new LatLng(1.306341, 103.785059));

    //TextView textView1;

    String[] spinnerCategories = {"Select Category", "Food and Beverages", "Study Spots", "Bus Stops"};
    int[] spinnerImages = {R.drawable.icon_select_category, R.drawable.icon_food_and_beverages, R.drawable.icon_study_spots, R.drawable.icon_bus_stops};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        /*
        textView1 = (TextView) findViewById(R.id.textView1);
        findViewById(R.id.button_form).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send Feedback form method
                sendSuggestion(v);
            }
        });
        */

        //
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        //
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);

    }

    /*
    public void sendSuggestion(View button) {
        //Do click handling here
        Intent intent = new Intent(this, SuggestionFormActivity.class);
        startActivityForResult(intent, 2); //SuggestionForm activity is started with requestCode 2
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==2)
        {
            String message=data.getStringExtra("MESSAGE");
            textView1.setText(message);
        }
    }
    */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.spinner_menu, menu);


        //Spinner element
        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);

/*
        /////////Form element
        //MenuItem button = menu.findItem(R.id.button_form);

        //Spinner click listener
        spinner.setOnItemSelectedListener(this);

        //Creating adapter for spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Attaching data adapter to spinner
        spinner.setAdapter(adapter);
        */

        CustomAdapter mCustomAdapter = new CustomAdapter(MapsActivity.this, spinnerCategories, spinnerImages);
        spinner.setOnItemSelectedListener(MapsActivity.this);
        spinner.setAdapter(mCustomAdapter);

        return true;
    }

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.form:
                // Form item was selected
                return true;
                //may need to include spinner item case
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    */


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

        if (parent.getCount() > 0) {
            // An item was selected. You can retrieve the selected item using
            //String selectedCategory = parent.getItemAtPosition(pos).toString();
            String selectedCategory = spinnerCategories[pos];

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
        }


        // Constrain the camera target to the NUS bounds.
        //mMap.setLatLngBoundsForCameraTarget(NUSBound);
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

    //GPS location info
    private final long MIN_TIME = 5000; //5 sec
    private final long MIN_DIST = 5; //5 meters


    //Location permission
    private boolean mLocationPermissionGranted = false;

    //NUS marker - for app to open and zoom into NUS campus
    private static final LatLng NUS = new LatLng(1.296739, 103.776372);

    //Markers variables
    private Marker mNUS;

    //Initialise marker array list to store markers of each category
    List<Marker> food_and_beverage_list = new ArrayList<>();
    List<Marker> study_spots_list = new ArrayList<>();
    List<Marker> bus_stops_list = new ArrayList<>();

    List<LocationDetails> locationDetailsList = new ArrayList<>();

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mDatabase.addValueEventListener(new ValueEventListener() {
            double latitude;
            double longitude;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String busStop = child.child("Bus Stops").getValue().toString();
                    String category = child.child("Category").getValue().toString();
                    latitude = Double.parseDouble(child.child("Latitude").getValue().toString());
                    longitude = Double.parseDouble(child.child("Longitude").getValue().toString());
                    String name = child.child("Name").getValue().toString();
                    String openingHours = child.child("Opening Hours").getValue().toString();
                    String description = child.child("Description").getValue().toString();

                    //Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)));
                    Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)));

                    //Adding marker to array list and setting marker colour according to category
                    if (category.equals("Food and Beverages")) {
                        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                        food_and_beverage_list.add(marker);
                    }
                    else if (category.equals("Study Spots")) {
                        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
                        study_spots_list.add(marker);
                    }
                    else if (category.equals("Bus Stops")) {
                        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                        bus_stops_list.add(marker);
                    }

                    locationDetailsList.add(new LocationDetails(marker.getId(),busStop, category, latitude, longitude, name, openingHours, description));
                }

                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);

                        //searching marker ID in locationDetailsList to get info of a specific marker
                        for (int i = 0; i < locationDetailsList.size(); i++) {
                            LocationDetails l = locationDetailsList.get(i);
                            if (marker.getId().equals(l.getMarkerID())) {
                                builder.setTitle(l.getName());

                                builder.setMessage("Description: " + l.getDescription() + "\n" + "\n" + "Category: " + l.getCategory() + "\n" + "Opening Hours: " + l.getOpeningHours() + "\n" + "Bus Stop: " + l.getBusStop());
                            }
                        }

                        //Add a button
                        builder.setPositiveButton("Ok", null);

                        //Create and show the AlertDialog
                        AlertDialog dialog = builder.create();
                        dialog.show();

                        return false;
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Add a marker in NUS and move the camera to a specific zoom level to show NUS campus
        mNUS = mMap.addMarker(new MarkerOptions()
                .position(NUS)
                .title("Marker in NUS"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(NUS, 15));
        // Set NUS marker to invisible
        mNUS.setVisible(false);

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

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                try {
                    latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title("My position"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                }
                catch (SecurityException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DIST, locationListener);
        }
        catch (SecurityException e) {
            e.printStackTrace();
        }
    }
}
