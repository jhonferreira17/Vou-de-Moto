package com.example.voudemotooficial.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.example.voudemotooficial.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class PassageiroActivity extends AppCompatActivity
        implements OnMapReadyCallback {
    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passageiro);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //resuperar localização do usuario
        recuperarLocalizacao();

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-3.135021, -58.438544);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Itacoatiara - Amazonas"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private void recuperarLocalizacao() {

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = location -> {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            LatLng meuLocal = new LatLng(latitude, longitude);

            mMap.clear();
            mMap.addMarker(
                    new MarkerOptions()
                            .position(meuLocal)
                            .title("Meu Local")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_localizacao))
            );

            mMap.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(meuLocal, 20)
            );
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    1000,
                    10,
                    locationListener
            );
            return;
        }


    }
}