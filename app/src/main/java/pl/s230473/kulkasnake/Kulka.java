// TO ZMIENIC NA WŁASNE
package pl.s230473.kulkasnake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

public class Kulka extends View {
    private Paint p;
    int pos_x = 10; // Aktualna Pozycja kulki w osi X
    int pos_y = 10; // Aktualna Pozycja kulki w osi Y
    int move_pos_x = 0; // Zmienna przechowywująca ruch kulki z akcelometru w osi X
    int move_pos_y = 0; // Zmienna przechowywująca ruch kulki z akcelometru w osi Y
    private final static int kulkaSize = 10; // Stały rozmiar naszej kulki

    public Kulka(Context context, AttributeSet attrs) {
        super(context, attrs);
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.RED); // Kolor naszej kulki na czerwony
    }

    // Funkcja która ustawia ruch <- wykorzystywana w akcelerometrze
    public void setMovePosX(int x) {
        move_pos_x = x;
    }

    // Funkcja która ustawia ruch <- wykorzystywana w akcelerometrze
    public void setMovePosY(int y) {
        move_pos_y = y;
    }

    // Ustawienie środka pozycji kulki
    public void setCenterPos() {
        // PObieramy szerokość i wysokość ekranu i dzielimy na 2 wtedy mamy środek
        pos_x = getMeasuredWidthAndState()/2;
        pos_y = getMeasuredHeightAndState()/2;
    }

    //Sprawdzamy dystans od środka <- nie użuwane ale może się przydać/
    public double getCenterDistance() {
        int x1 = getWidth()/2;
        int x2 = pos_x;
        int y1 = getHeight()/2;
        int y2 = pos_y;
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }

    // Zmieniamy pozycje naszej kulki o ruch z akcelerometu.
    public void move () {
        pos_x += move_pos_x;
        pos_y += move_pos_y;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // I rysujemy naszą kulke o rozmiarze kulkaSize w pozycji pos_x i pos_y
        canvas.drawCircle(pos_x, pos_y, kulkaSize, p);
    }

    public Point getPos() {
        //Zwracamy aktualną pozycje kulki
        return new Point(pos_x, pos_y);
    }
}
