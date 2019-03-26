package pl.s230473.kulkasnake;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Kulka kulka;
    private Kolo kolo;
    private SensorManager sensorManager;
    private Sensor sensor;
    private ActivityThread activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        kolo = findViewById(R.id.kolo);
        kulka = findViewById(R.id.kulka);
        TextView winner = findViewById(R.id.winner);

        final Button restartButton = findViewById(R.id.restart);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.restart();
            }
        });

        activity = new ActivityThread(kulka, kolo, winner, restartButton, new Handler());
        Thread thread = new Thread(activity);
        thread.start();

    }


    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(activity, sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener((SensorListener) activity);
    }
}
