package com.kbstar.n01gps;

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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnGPS;
    private TextView display;

    private static String TAG = "MyGPS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGPS = findViewById(R.id.btnGPS);
        display = findViewById(R.id.display);

        btnGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getGPSInfo();
            }
        });

        // Step 5. Permission 허가 코드 추가
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

    public void getGPSInfo() {
        // Step 1. 위치 관리 객체 참조
        printDebug("Step 1");
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

    public void printDebug(String msg) {
        display.append(msg + "\n");
    }

    // Inner Class
    // Step 2. 위치 리스너 구현
    class GPSListener implements LocationListener {

        @Override
        public void onLocationChanged(@NonNull Location location) {
            double lat = location.getLatitude();
            double lon = location.getLongitude();
            printDebug("Current Location : (Lat, Lon) = (" + lat + ", " + lon + ")");
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
}