package com.example.gps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.yandex.mapkit.Image;
import com.yandex.mapkit.MapKit;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

public class MainActivity extends AppCompatActivity {
    LocationManager _LocationManger;
    TextView textAddress;
    MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapKitFactory.setApiKey("Key_Klimov");
        MapKitFactory.initialize(this);
        setContentView(R.layout.activity_main);

        mapView = findViewById(R.id.mapView);
        textAddress = findViewById(R.id.editText);

        _LocationManger = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    LocationListener _locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            if (location != null) {
                mapView.getMap().move(
                        new CameraPosition(
                                new Point(location.getLatitude(), location.getLongitude()), 15, 0, 0));
                mapView.getMap().getMapObjects().clear();
                mapView.getMap().getMapObjects().addPlacemark(
                        new Point(location.getLatitude(), location.getLongitude()),
                        ImageProvider.fromResource(MainActivity.this, R.drawable.location)
                );

                GetAddressByGPS getAddressByGPS = new GetAddressByGPS(
                        String.valueOf(location.getLongitude()) + "," + String.valueOf(location.getLatitude()),
                        textAddress
                );
                getAddressByGPS.execute();
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();

        MapKitFactory.getInstance().onStart();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, 1);
        }

        _LocationManger.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10, _locationListener);
        _LocationManger.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, _locationListener);

        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();

        mapView.onStop();

        MapKitFactory.getInstance().onStop();
    }
}