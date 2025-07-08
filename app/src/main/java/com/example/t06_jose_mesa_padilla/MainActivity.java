package com.example.t06_jose_mesa_padilla;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.t06_jose_mesa_padilla.databinding.ActivityMainBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity implements SensorEventListener, OnMapReadyCallback {
    private ActivityMainBinding binding;
    private SensorManager sensorManager;
    private Sensor sensorHumedad;
    private Sensor sensorTemperatura;
    private GoogleMap mMap;
    private LocationManager locationManager;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityMainBinding.inflate(getLayoutInflater())).getRoot());

        // Configuramos humedad y temperatura
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorTemperatura = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sensorHumedad = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);

        // Verificar si el sensor de humedad está presente
        if (sensorHumedad == null) {
            // Mostrar Toast indicando que el sensor de humedad no está disponible
            Toast.makeText(this, "El sensor de humedad no está disponible en este dispositivo", Toast.LENGTH_SHORT).show();
        } else if (sensorTemperatura == null) {
            // Mostrar Toast indicando que el sensor de humedad no está disponible
            Toast.makeText(this, "El sensor de temperatura no está disponible en este dispositivo", Toast.LENGTH_SHORT).show();
        }

        // Configuramos el mapa
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Comprobamos el tipo de sensor
        if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            // Es un sensor de temperatura
            binding.tvTempValue.setText(String.valueOf(event.values[0]));
        } else if (event.sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY) {
            // Es un sensor de humedad
            binding.tvHumValue.setText(String.valueOf(event.values[0]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Detecta que el sensor ha cambiado de precisión.
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorTemperatura, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorHumedad, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this);
        super.onPause();
        pedirPermisos();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                localizarDispositivo();
            }
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        pedirPermisos();
    }

    private void pedirPermisos() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION,}, 1);
    }


    private void localizarDispositivo() {
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                LatLng coordenadas = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(coordenadas).title("Usted está aquí"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(coordenadas));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordenadas, 15)); // Ajuste del zoom aquí
            }
        });
    }
}