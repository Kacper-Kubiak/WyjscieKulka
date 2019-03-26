// TO ZMIENIC NA WŁASNE
package pl.s230473.kulkasnake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

public class Kolo extends View {

    private Paint p;
    int pos_x = 0; // Pozycja kulki w osi x
    int pos_y = 0; // Pozycja kulki w osi y
    private final static int koloSize = 400; // stały rozmiar naszej kulki
    RectF rectF; //zmienna pomocnicza do rysowania pola
    int start = 10; // ustawiamy poczatek rysowania obszaru zamknietego
    int sweep = 340; // i ile stopni ma ten obszar mieć. Im więcej tym większy obszar zamkniety
    // musi być mniejszy niż 360 stopni. jeżeli damy 340 to oznacza, że obszar do wygranej to tylko 20 stopni bo 360-340 = 20

    public Kolo(Context context, AttributeSet attrs) {
        super(context, attrs);
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        //p.setColor(Color.RED); // ustawiamy kolor obszaru
    }

    public void setCenterPos() {
        pos_x = getWidth()/2; // pobieramy szerokość ekrany dzielimy przez 2 czyli mamy środek i tam  ustawiamy nasza kulke
        pos_y = getHeight()/2;// pobieramy szerokość ekrany dzielimy przez 2 czyli mamy środek i tam  ustawiamy nasza kulke

        Random rand = new Random(); // Losujemy wartość losową
        start = rand.nextInt(360); // tak aby zawsze miejsce do wygranej było różne.
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //rysukemy nasz obszar na środku ekranu
        rectF = new RectF(pos_x-koloSize/2, pos_y-koloSize/2, koloSize/2+pos_x, koloSize/2+pos_y);
        p.setColor(Color.BLUE); // kolor niebieski
        p.setStrokeWidth(10); // szerokość rysowania
        p.setStyle(Paint.Style.STROKE);
        /*rectF <-obszar rysowania,
         start <- poczatek obszaru zamknietego,
          sweep <- ile stopni ma miec obszar zamkniety,
           false <- false czyli nie rysujemy zejscia do srodka z obszaru otwartego
            p <- co rysujemy czyli PAINT
         */
        canvas.drawArc (rectF, start, sweep, false, p);
    }

    public int getArcExitStart() {
        // Pobieramy poczatek obszaru

        //Jeżeli kąt mniejszy od 360 to dokonujemy korekty
        if(start-(360-sweep) < 0)
        {
            //zwracamy po korekcie i obliczeniu początek obszaru wygranej
            return 360+(start-(360-sweep));
        }
        //jeżeli wszystko ok to zwracamy po prostu obliczony początek obszaru wygranej
        return start-(360-sweep);
    }

    public int getArcExitEnd() {
        //Koniec obszaru wygranej to poczatek obszaru zamknietego
        return start;
    }

    public int getArc2Exit()
    {
        //Jak duży jest obszar wygranej - nigdzie tego nie używam ale może się przydać
        return 360-sweep;
    }

    public Point getPos() {
        //Zwracam pozycje środka obszaru
        return new Point(pos_x, pos_y);
    }
}
