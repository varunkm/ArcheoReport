package team38.ucl.archeoreport;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.content.Context;
import android.util.AttributeSet;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;

/**
 * Created by varunmathur on 06/03/16.
 */
public class AnnotationView extends View {
    private Bitmap bgimg;
    //drawing path
    private Path drawPath;
    //drawing and canvas paint
    private Paint drawPaint, canvasPaint;
    //initial color
    private int paintColor = 0xFF660000;
    //canvas
    private Canvas drawCanvas;
    //canvas bitmap
    private Bitmap canvasBitmap;
    private boolean eraserMode= false;
    private String defect;
    public AnnotationView(Context context, AttributeSet attrs)
    {
        super(context,attrs);
        this.bgimg = bgimg;
        this.setBackground(new BitmapDrawable(context.getResources(),bgimg));
        this.setDrawingCacheEnabled(true);
        setupDrawing();
    }
    public void setBgimg(Bitmap img)
    {
        this.bgimg = img;
        this.setBackground(new BitmapDrawable(getContext().getResources(), bgimg));
    }
    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        float touchX = event.getX();
        float touchY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                drawCanvas.drawPath(drawPath, drawPaint);

                drawPath.reset();
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }

    private void setupDrawing()
    {
        drawPaint = new Paint();
        drawPath  = new Path();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(20);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);

        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    public void setColor(String color) {
        color = color.toLowerCase();
        if (paintColor != Color.parseColor(color)){
            Log.i("Color Change",color+"  "+Color.parseColor(color));
            this.paintColor = Color.parseColor(color);
            drawPaint.setColor(paintColor);
            invalidate();
        }
    }

    public void setEraserMode(Boolean erase)
    {
        eraserMode = erase;
    }
    public void setDefect(String defect)
    {
        this.defect = defect;
    }
}
