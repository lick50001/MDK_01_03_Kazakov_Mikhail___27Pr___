package com.example.gps;

import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.LocaleList;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    LocationManager _LocationManager;
    TextView textAddress;
    MapView mapView;
    LocationListener _locationListioner = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            if(location != null){
                mapView.getMap().move(
                        new CameraPosition(
                                new Point(location.getLatitude(), location.getLongitude()), 15, 0, 0));
                mapView.getMap().getMapObjects().Clear();
                mapView.getMap().getMapObjects().AddPlacemark(
                        new Point(location.getLatitude(), location.getLongitude()),
                        ImageProvider.fromResource(MainActivity.this, R.drawable.location)
                );
                GetAddresByGPS getAddresByGPS = new GetAddresByGPS(
                        String.valueOf(location.getLongitude()) + "," + String.valueOf(location.getLatitude()),
                );
                getAddresByGPS.execute();
            }
        }
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapKitFactory.setApiKey("KEEEEEEEEEEEEEEEY");
        MapKitFactory.initialize(this);
        setContentView(R.layout.activity_main);
        mapView = findViewById(R.id.mapview);
        textAddress = findViewById(R.id.edittext);
        _LocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }
}