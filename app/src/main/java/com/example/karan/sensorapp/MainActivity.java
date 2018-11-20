package com.example.karan.sensorapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener{


    private TextView txt;
    private SensorManager sensorManager;


    private Sensor pressureSensor;
    private Sensor lightSensor;
    private Sensor ambientTemperature;

    TextView light_available, proximityText;

    private StringBuilder msg = new StringBuilder(2048);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = findViewById(R.id.txt);
        light_available = (TextView)findViewById(R.id.light_available);
        proximityText = (TextView) findViewById(R.id.proximity);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);


        pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        lightSensor =  sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        ambientTemperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        String sensor_error = getResources().getString(R.string.error_no_sensor);

        if (lightSensor == null) {
            light_available.setText(sensor_error);
        }
        if (ambientTemperature == null) {
            proximityText.setText(sensor_error);
        }
        if (pressureSensor != null) {
            txt.setText(sensor_error);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Listeners for the sensors are registered in this callback and
        // can be unregistered in onPause().
        //
        // Check to ensure sensors are available before registering listeners.
        // Both listeners are registered with a "normal" amount of delay
        // (SENSOR_DELAY_NORMAL)
        if (ambientTemperature != null) {
            sensorManager.registerListener((SensorEventListener) this, ambientTemperature,
                    sensorManager.SENSOR_DELAY_NORMAL);
        }
        if (lightSensor != null) {
            sensorManager.registerListener((SensorEventListener) this, lightSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (pressureSensor != null) {
            sensorManager.registerListener((SensorEventListener) this, pressureSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Unregister all sensor listeners in this callback so they don't
        // continue to use resources when the app is paused.
        sensorManager.unregisterListener((SensorEventListener) this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        // The sensor type (as defined in the Sensor class).
        int sensors = sensorEvent.sensor.getType();

        // The new data value of the sensor.  Both the light and proximity
        // sensors report one value at a time, which is always the first
        // element in the values array.
        float currentValue = sensorEvent.values[0];

        switch (sensors) {
            // Event came from the light sensor.
            case Sensor.TYPE_LIGHT:
                // Set the light sensor text view to the light sensor string
                // from the resources, with the placeholder filled in.
                light_available.setText(getResources().getString(
                        R.string.label_light, currentValue));
                break;

            case Sensor.TYPE_PRESSURE:
                // Set the light sensor text view to the light sensor string
                // from the resources, with the placeholder filled in.
                txt.setText(getResources().getString(
                        R.string.label_pressure, currentValue));
                break;

            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                // Set the proximity sensor text view to the light sensor
                // string from the resources, with the placeholder filled in.
                proximityText.setText(getResources().getString(
                        R.string.label_ambientTemp, currentValue));
                break;
            default:
                // do nothing
        }
    }

    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}




