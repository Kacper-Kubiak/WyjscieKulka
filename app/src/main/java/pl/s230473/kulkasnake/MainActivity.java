// TO ZMIENIC NA WŁASNE
package pl.s230473.kulkasnake;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //Tworzenie zmiennych pomocniczych
    private Kulka kulka; //Zmienna przechowywująca kulkę którą poruszamy
    private Kolo kolo; //Zmienna przechowywująca pole z ktorego mamy wyjsć
    private SensorManager sensorManager; // SensorManager
    private Sensor sensor; // Sensor ( w tym wypadku tylko ACCELEROMETER )
    private ActivityThread activity; // Aktywnośc w której dokonujemy wszystkich obliczeń itp

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //Wymuszenie trybu portretowego bez możliwości zmiany
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE); // Przypisanie do zmiennej sensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); // Przypisanie do zmiennej sensor naszego akcelometru

        kolo = findViewById(R.id.kolo); // przypisanie do zmiennej kolo obszaru z którego mamy wyjść po ID z activity_main
        kulka = findViewById(R.id.kulka); // przypisanie do zmiennej kulka naszej kulki po ID z activity_main
        TextView winner = findViewById(R.id.winner); // Przypisanie zmiennej o tym, że wygraliśmy. ID z activity_main

        // Obsługa przycisku resetującego gre po wygranej lub przegranej
        final Button restartButton = findViewById(R.id.restart);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.restart(); // funkcja w klasie activity która resetuje gre
            }
        });

        //Tworzymy naszą klase z grą i przekazujemy potrzebne zmienne.
        activity = new ActivityThread(kulka, kolo, winner, restartButton, new Handler());

        // I uruchamiamy nasza klase w wątku
        Thread thread = new Thread(activity);
        thread.start();

    }

    // To jest do sensora... nwm
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
