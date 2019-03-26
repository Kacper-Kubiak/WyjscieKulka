package pl.s230473.kulkasnake;

import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityThread implements Runnable, SensorListener, SensorEventListener {

    Kolo kolo;
    Kulka kulka;
    Handler handler;
    TextView winnerText;
    Button restartButton;
    boolean gameover = false;
    boolean winner = false;

    public ActivityThread(Kulka kul, Kolo k, TextView w, Button br, Handler h) {
        kulka = kul;
        kolo = k;
        winnerText = w;
        restartButton = br;
        handler = h;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        kulka.setMovePosX((int) Math.ceil(-event.values[0]));
        kulka.setMovePosY((int) Math.ceil(event.values[1]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(int sensor, float[] values) {

    }

    @Override
    public void onAccuracyChanged(int sensor, int accuracy) {

    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            kolo.setCenterPos();
            kulka.setCenterPos();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (true) {
            if(gameover || winner) {
                continue;
            }
            try {
                Thread.sleep(100);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        kulka.move();
                        kolo.invalidate();
                        kulka.invalidate();

                        double p = (Math.pow((kulka.getPos().x - kolo.getPos().x), 2) / Math.pow(200, 2))
                                + (Math.pow((kulka.getPos().y - kolo.getPos().y), 2) / Math.pow(200, 2));

                        if(p >= 1.0) {
                            int angleInArc = (int) (Math.toDegrees(Math.atan2(kolo.getPos().y - kulka.getPos().y, kolo.getPos().x - kulka.getPos().x)));
                            angleInArc += 180;
                            int startAngle = kolo.getArcExitStart();
                            int endAngle = kolo.getArcExitEnd();


                            if(endAngle < startAngle)
                            {
                                if(angleInArc > endAngle || angleInArc < startAngle) {
                                    winner = true;
                                    winnerText.setVisibility(View.VISIBLE);
                                }
                            }
                            else
                            {
                                if(angleInArc > startAngle && angleInArc < endAngle) {
                                    winner = true;
                                    winnerText.setVisibility(View.VISIBLE);
                                }
                            }

                            gameover = true;
                            restartButton.setVisibility(View.VISIBLE);
                        }

                    }
                });
            }
            catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void restart() {
        restartButton.setVisibility(View.INVISIBLE);
        gameover = false;
        winner = false;
        kolo.setCenterPos();
        kulka.setCenterPos();
        kolo.invalidate();
        kulka.invalidate();
    }
}
