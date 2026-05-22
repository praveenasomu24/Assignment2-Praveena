package com.example.assignment2;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor gyroscope;

    private TextView accelText;
    private TextView gyroText;
    private TextView orientationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accelText = findViewById(R.id.accelText);
        gyroText = findViewById(R.id.gyroText);
        orientationText = findViewById(R.id.orientationText);

        // Initialize SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Initialize sensors
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        // Check if sensors are available
        if (accelerometer == null) {
            accelText.setText("Accelerometer sensor not available");
        }

        if (gyroscope == null) {
            gyroText.setText("Gyroscope sensor not available");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Register accelerometer listener
        if (accelerometer != null) {
            sensorManager.registerListener(
                    this,
                    accelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL
            );
        }

        // Register gyroscope listener
        if (gyroscope != null) {
            sensorManager.registerListener(
                    this,
                    gyroscope,
                    SensorManager.SENSOR_DELAY_NORMAL
            );
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Unregister sensor listeners
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            accelText.setText(
                    "X: " + x +
                            "\nY: " + y +
                            "\nZ: " + z
            );

            // Orientation estimation
            if (x > 7) {
                orientationText.setText("Tilted Left");
            }
            else if (x < -7) {
                orientationText.setText("Tilted Right");
            }
            else if (y > 7) {
                orientationText.setText("Upright");
            }
            else if (z > 7) {
                orientationText.setText("Face Up");
            }
            else if (z < -7) {
                orientationText.setText("Face Down");
            }
            else {
                orientationText.setText("Flat");
            }
        }

        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {

            float gx = event.values[0];
            float gy = event.values[1];
            float gz = event.values[2];

            gyroText.setText(
                    "X: " + gx +
                            "\nY: " + gy +
                            "\nZ: " + gz
            );
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}