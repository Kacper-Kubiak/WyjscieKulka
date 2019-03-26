package pl.s230473.kulkasnake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class Kulka extends View {
    private Paint p;
    int pos_x = 10;
    int pos_y = 10;
    int move_pos_x = 0;
    int move_pos_y = 0;
    private final static int kulkaSize = 10;

    public Kulka(Context context, AttributeSet attrs) {
        super(context, attrs);
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.RED);
    }

    public void setMovePosX(int x) {
        move_pos_x = x;
    }

    public void setMovePosY(int y) {
        move_pos_y = y;
    }

    public void setCenterPos() {
        pos_x = getMeasuredWidthAndState()/2;
        pos_y = getMeasuredHeightAndState()/2;
    }

    public double getCenterDistance() {
        int x1 = getWidth()/2;
        int x2 = pos_x;
        int y1 = getHeight()/2;
        int y2 = pos_y;
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }

    public void move () {
        pos_x += move_pos_x;
        pos_y += move_pos_y;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(pos_x, pos_y, kulkaSize, p);
    }

    public Point getPos() {
        return new Point(pos_x, pos_y);
    }
}
