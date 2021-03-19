package edu.wm.cs.cs301.AMazeByDavidSolinsky.gui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import edu.wm.cs.cs301.AMazeByDavidSolinsky.R;
import edu.wm.cs.cs301.AMazeByDavidSolinsky.generation.Wall;

public class MazePanel extends View implements P5Panel {
    Bitmap bitmap;
    Canvas canvas;
    Paint paint;
    double scaler, markerRadius;
    Typeface typeface;

    private static final String LogTag = "MazePanel";

    public MazePanel(Context context) {
        super(context);
        Log.v(LogTag, "MazePanel made");
        bitmap = Bitmap.createBitmap(359, 357, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint = new Paint();
//        Typeface plain = Typeface.createFromAsset(getContext().getAssets(), "font/abhayalibre_regular.ttf");
//        paint.setTypeface(plain);
        setFocusable(true);
    }

    public MazePanel(Context context, double scaler, double markerRadius, Typeface typeface) {
        super(context);
        this.scaler = scaler;
        this.markerRadius = markerRadius;
        this.typeface = typeface;
    }

    public MazePanel(Context context, AttributeSet attr) {
        super(context, attr);
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        }

    public void update(){
        commit();
    }

    @Override
    public void clear() {
//        canvas = new Canvas();
//        bitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Bitmap.Config.ARGB_8888);
    }

    @Override
    public void commit() {
        invalidate();
    }

    @Override
    public boolean isOperational() {
        return true;
    }

    @Override
    public void setColor(int rgb) { paint.setColor(rgb); }

    @Override
    public int getColor() {
        return paint.getColor();
    }

    /**
     * Takes in color integer values [0-255], returns corresponding color-int
     * value. @param integer color values for red green and blue
     */
    public static int getColorEncoding(int red, int green, int blue) {
        return Color.rgb(red, green, blue);
    }

    @Override
    public void addFilledRectangle(int x, int y, int width, int height) {
        Rect r = new Rect(x, y, width, height);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(r, paint);
    }

    /**
     * Adds a filled polygon.
     * The polygon is specified with {@code (x,y)} coordinates
     * for the n points it consists of. All x-coordinates
     * are given in a single array, all y-coordinates are
     * given in a separate array. Both arrays must have
     * same length n. The order of points in the arrays
     * matter as lines will be drawn from one point to the next
     * as given by the order in the array.
     *
     * @param xPoints are the x-coordinates of points for the polygon
     * @param yPoints are the y-coordinates of points for the polygon
     * @param nPoints is the number of points, the length of the arrays
     */
    @Override
    public void addFilledPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        Path linePath = new Path();
        linePath.reset();
        paint.setStyle(Paint.Style.FILL);
        linePath.moveTo (xPoints[0], yPoints[0]);
        for (int i = 0; i < nPoints; i++)
            linePath.lineTo(xPoints[i], yPoints[i]);
        linePath.lineTo(xPoints[0], yPoints[0]);
        canvas.drawPath(linePath, paint);
    }

    /**
     * Adds a polygon.
     * The polygon is not filled.
     * The polygon is specified with {@code (x,y)} coordinates
     * for the n points it consists of. All x-coordinates
     * are given in a single array, all y-coordinates are
     * given in a separate array. Both arrays must have
     * same length n. The order of points in the arrays
     * matter as lines will be drawn from one point to the next
     * as given by the order in the array.
     *
     * @param xPoints are the x-coordinates of points for the polygon
     * @param yPoints are the y-coordinates of points for the polygon
     * @param nPoints is the number of points, the length of the arrays
     */
    @Override
    public void addPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        Path linePath = new Path();
        linePath.reset();
        linePath.moveTo (xPoints[0], yPoints[0]);
        for (int i = 0; i < nPoints; i++)
            linePath.lineTo(xPoints[i], yPoints[i]);
        linePath.lineTo(xPoints[0], yPoints[0]);
        canvas.drawPath(linePath, paint);
    }

    @Override
    public void addLine(int startX, int startY, int endX, int endY) {
        canvas.drawLine(startX, startY, endX, endY, paint);
    }

    @Override
    public void addFilledOval(int x, int y, int width, int height) {
        RectF oval = new RectF(x, y, width, height);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawOval(oval, paint);
    }

    @Override
    public void addArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        canvas.drawArc(x, y, width, height, startAngle, arcAngle, true, paint);
   }

    @Override
    public void addMarker(float x, float y, String str) {
        canvas.drawText(str, x, y, paint);
    }
}
