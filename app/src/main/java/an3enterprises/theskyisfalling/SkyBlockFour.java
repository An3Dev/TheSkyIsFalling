package an3enterprises.theskyisfalling;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class SkyBlockFour implements GameObject{
    private Rect rectangle;
    private int color;

    public SkyBlockFour(Rect rectangle, int color) {
        this.rectangle = rectangle;
        this.color = color;
    }

    public Rect getRectangle() {
        return rectangle;
    }

    public void incrementY(float y) {
        rectangle.top += y;
        rectangle.bottom += y;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);
    }

    @Override
    public void update() {

    }

    public void update(Point point) {
        rectangle.set(point.x - rectangle.width() / 2, point.y - rectangle.height() / 2, point.x + rectangle.width() / 2, point.y + rectangle.height()/2);
    }

    public boolean hitGround(Grass grass) {
        return rectangle.equals(grass);
    }
}