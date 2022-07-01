package com.kbstar.n02googlemap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.List;

// File > Project Structure > Dependencies > App > (+) > Library Dependency > play-services-maps 검색

public class MainActivity extends AppCompatActivity {

    private Button button;
    GoogleMap map;
    SupportMapFragment mapFragment;
    MarkerOptions myMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);    // 원래는 이렇게 type casting 필요!
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                Log.d("GMAP", "Ready...");

                map = googleMap;
                map.setMyLocationEnabled(true);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMap();
            }
        });

        AndPermission.with(this)
                .runtime()
                .permission(
                        Permission.ACCESS_FINE_LOCATION,
                        Permission.ACCESS_COARSE_LOCATION
                )
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        printDebug("Permitted Count : " + permissions.size());
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        printDebug("Denied Count : " + permissions.size());
                    }
                })
                .start();

    }

    public void showMap() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            printDebug("try");
            Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);  // 연결하다가 문제되면, 가장최근 로케이션 기억했다가 가져와줘

            if(location != null) {
                double lat = location.getLatitude();
                double lon = location.getLongitude();
                printDebug("LastKnown Location : (Lat, Lon) = (" + lat + ", " + lon + ")");
            }
            // Step 3. 위치 정보 갱신(update) 요청
            GPSListener gpsListener = new GPSListener();
            long minTime = 10000;
            float minDistance = 0f;
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, gpsListener);
            printDebug("정보 요청중..");

            // Step 4. 위험권한 코드 추가 (Gradle - yanzhenjie)

        } catch (Exception e){
            printDebug("Exception : " + e.getMessage());
        }
    }

    class GPSListener implements LocationListener {

        @Override
        public void onLocationChanged(@NonNull Location location) {
            double lat = location.getLatitude();
            double lon = location.getLongitude();
            printDebug("Current Location : (Lat, Lon) = (" + lat + ", " + lon + ")");

            move2MyLocation(lat, lon);
        }

        @Override
        public void onLocationChanged(@NonNull List<Location> locations) {
            LocationListener.super.onLocationChanged(locations);
        }

        @Override
        public void onFlushComplete(int requestCode) {
            LocationListener.super.onFlushComplete(requestCode);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            LocationListener.super.onStatusChanged(provider, status, extras);
        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {
            LocationListener.super.onProviderEnabled(provider);
        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {
            LocationListener.super.onProviderDisabled(provider);
        }
    }

    public void printDebug(String msg) {
        Log.d("Google Map", msg);
    }

    public void move2MyLocation(double lat, double lon) {
        LatLng point = new LatLng(lat, lon);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(point, 13));

        // Pin to MyLocation
        setPin(point);
    }

    public void setPin(LatLng point) {
        myMarker = new MarkerOptions();
        myMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin));
        myMarker.position(point);

        map.addMarker(myMarker);
    }
}