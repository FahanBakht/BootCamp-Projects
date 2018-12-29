package com.example.farhan.parkingbookingsystem.Map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.farhan.parkingbookingsystem.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Boolean mapReady = false;
    public static Boolean oneTimeOnly = true;
    private GoogleMap mMap;
    private CameraPosition myLocation;
    private static double lat;
    private static double lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFrag);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapReady = true;
        mMap = googleMap;
        if (mapReady) {
            Log.e("MapActivity", "on Map Ready Runs");
            //setUpMapIfNeeded();
            onSomeButtonClick();
        }
    }


    private void flyTo(CameraPosition target) {
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(target), 5000, null);
    }

    private void setUpMapIfNeeded() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission not Granted", Toast.LENGTH_SHORT).show();
        } else {
            mMap.setMyLocationEnabled(true);
            // Check if we were successful in obtaining the map.
            if (mMap != null && oneTimeOnly) {
                mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                    @Override
                    public void onMyLocationChange(Location arg0) {

                        Log.e("MapActivity", "mLatitude " + arg0.getLatitude());
                        Log.e("MapActivity", "mLongitude " + arg0.getLongitude());
                        lat = arg0.getLatitude();
                        lng = arg0.getLongitude();

                        myLocation = CameraPosition.builder()
                                .target(new LatLng(arg0.getLatitude(), arg0.getLongitude()))
                                .zoom(17)
                                .bearing(90)
                                .tilt(45)
                                .build();
                        flyTo(myLocation);
                        mMap.addMarker(new MarkerOptions().position(new LatLng(arg0.getLatitude(), arg0.getLongitude())).title("It's Me!"));
                        oneTimeOnly = false;
                    }
                });
            } else {
                myLocation = CameraPosition.builder()
                        .target(new LatLng(lat, lng))
                        .zoom(17)
                        .bearing(90)
                        .tilt(45)
                        .build();
                flyTo(myLocation);
                mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title("It's Me!"));
                oneTimeOnly = false;
            }
        }
    }


    public void onSomeButtonClick() {
        if (!permissionsGranted()) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        } else setUpMapIfNeeded();

    }

    private Boolean permissionsGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                setUpMapIfNeeded();
            } else {
                // User refused to grant permission. You can add AlertDialog here
                Toast.makeText(this, "You didn't give permission to access device location", Toast.LENGTH_LONG).show();
            }
        }
    }

}

