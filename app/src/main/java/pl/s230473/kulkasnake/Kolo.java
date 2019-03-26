package pl.s230473.kulkasnake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Random;

public class Kolo extends View {

    private Paint p;
    int pos_x = 0;
    int pos_y = 0;
    private final static int koloSize = 400;
    RectF rectF;
    int start = 10;
    int sweep = 340;

    public Kolo(Context context, AttributeSet attrs) {
        super(context, attrs);
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.RED);
    }

    public void setCenterPos() {
        pos_x = getWidth()/2;
        pos_y = getHeight()/2;

        Random rand = new Random();
        start = rand.nextInt(360);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        rectF = new RectF(pos_x-koloSize/2, pos_y-koloSize/2, koloSize/2+pos_x, koloSize/2+pos_y);
        p.setColor(Color.BLUE);
        p.setStrokeWidth(10);
        p.setStyle(Paint.Style.STROKE);
        canvas.drawArc (rectF, start, sweep, false, p);
    }

    public int getArcExitStart() {
        if(start-(360-sweep) < 0)
        {
            return 360+(start-(360-sweep));
        }
        return start-(360-sweep);
    }

    public int getArcExitEnd() {
        return start;
    }

    public int getArc2Exit()
    {
        return 360-sweep;
    }

    public Point getPos() {
        return new Point(pos_x, pos_y);
    }
}
