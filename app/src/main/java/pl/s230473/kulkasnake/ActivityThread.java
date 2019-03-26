// TO ZMIENIC NA WŁASNE
package pl.s230473.kulkasnake;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityThread implements Runnable, SensorListener, SensorEventListener {

    //to są zmienne z MainActivity które przekazujemy w konstruktorze
    Kolo kolo;
    Kulka kulka;
    Handler handler;
    TextView winnerText;
    Button restartButton;

    //Zmienne pomocnicze o stanie gry
    boolean gameover = false;
    boolean winner = false;

    //Konstruktor w którym przekazujemy nasze zmienne
    public ActivityThread(Kulka kul, Kolo k, TextView w, Button br, Handler h) {
        kulka = kul;
        kolo = k;
        winnerText = w;
        restartButton = br;
        handler = h;
    }

    // Obsługa sensora
    @Override
    public void onSensorChanged(SensorEvent event) {
        //values przechowuje informacje od akcelometru. [0] to x, [1] to y
        //kulka.setMovePos <- przekazujemy do kulki informacje o ruchu
        kulka.setMovePosX((int) Math.ceil(-event.values[0]));
        kulka.setMovePosY((int) Math.ceil(event.values[1]));
    }

    // Musi być - zostaje puste
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    // Musi być - zostaje puste
    @Override
    public void onSensorChanged(int sensor, float[] values) {

    }

    // Musi być - zostaje puste
    @Override
    public void onAccuracyChanged(int sensor, int accuracy) {

    }

    // Nasza główna logika aplikacji ( override z runnable)
    @Override
    public void run() {
        try {
            //Pierwsze uruchomienie wiec czekamy i resetujemy wszystko na start
            //Można też uzżyć funkcji z View o gotowości
            Thread.sleep(1000);
            kolo.setCenterPos(); // ustaiwmay nasz obszar na środek
            kulka.setCenterPos(); // I ustawiamy jeszcze kulke na środek
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (true) {
            if(gameover || winner) {
                continue; // jeżeli koniec gry to nie idź dalej.
            }
            try {
                Thread.sleep(100); // Aby nie odświeżać za często to ograniczamy się Sleepem
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        kulka.move(); // Odświeżenie ruchu kulki
                        kolo.invalidate(); //Wymuszamy ponowne rysowanie
                        kulka.invalidate();//Wymuszamy ponowne rysowanie

                        /*
                            Tutaj sprawdzamy czy nasza kulka wyszła poza nasz obszar.
                            prosta funkcja matematyczna.
                            Zamiast 200 wpisać rozmiar pola podzielony przez 2
                            W moim wypadku to 400 / 2 = 200
                         */
                        double p = (Math.pow((kulka.getPos().x - kolo.getPos().x), 2) / Math.pow(200, 2))
                                + (Math.pow((kulka.getPos().y - kolo.getPos().y), 2) / Math.pow(200, 2));

                        // Jeżeli jesteśmy poza obszarem (czyli p>=1.0)
                        if(p >= 1.0) {
                            //Sprawdzamy jaki jest kąt wyjścia kulki
                            int angleInArc = (int) (Math.toDegrees(Math.atan2(kolo.getPos().y - kulka.getPos().y, kolo.getPos().x - kulka.getPos().x)));
                            angleInArc += 180; // i przystosowujemy ten kąt do naszych oznaczeń osi
                            int startAngle = kolo.getArcExitStart(); // pobieramy poczatek obszaru wygranej
                            int endAngle = kolo.getArcExitEnd(); // i koniec obszaru wygranej

                            // Jeżeli obszar koncowy jest mniejszy od poczatkowej
                            // czyli np koncowa to 10 stopni a poczatkowa to 350 stopni
                            // to dokonujemy korekty aby poprawnie sprawdzić czy wygraliśmy
                            if(endAngle < startAngle)
                            {
                                // sprawdzamy czy sie miescimy w obszarze wygranej
                                if(angleInArc > endAngle || angleInArc < startAngle) {
                                    winner = true; // ustawiamy zmienna na wygrana
                                    winnerText.setVisibility(View.VISIBLE); // i pokazujemy tekst o wygranej
                                }
                            }
                            // jeżeli koniec jest wiekszy od poczatku ( wiekszość przypadków)
                            else
                            {
                                // to sprawdzamy czy sie miescimy w naszych widełkach
                                if(angleInArc > startAngle && angleInArc < endAngle) {
                                    winner = true;// ustawiamy zmienna na wygrana
                                    winnerText.setVisibility(View.VISIBLE);// i pokazujemy tekst o wygranej
                                }
                            }

                            gameover = true; // Ustaiwiamy zmienna na koniec gry - bo jesteśmy poza obszarem
                            restartButton.setVisibility(View.VISIBLE); // i pokazujemy button umożliwiający restart gry
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

    // Obsługa restartu gry
    public void restart() {
        restartButton.setVisibility(View.INVISIBLE); // Ukrywamy przycisk do restartu
        gameover = false; // ustawiamy, że jeszcze nie jest koniec gry
        winner = false; // oraz, że nie wygraliśmy
        kolo.setCenterPos(); // resetujemy nasze pole ( i losujemy miejse wygranej ale o tym w Kolo.java)
        kulka.setCenterPos(); // i resetujemy położenie kulki na środek
        kolo.invalidate(); // Wymuszamy ponowne rysowanie pola
        kulka.invalidate();// Wymuszamy ponowne rysowanie kulki
    }
}
