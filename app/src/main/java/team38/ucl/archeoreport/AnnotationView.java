package team38.ucl.archeoreport;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import team38.ucl.archeoreport.Models.AnnotatedImage;

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
    private int brushSize = 10;
    private ArrayList<String> defects= new ArrayList<String>();
    private String currentDefect;
    private String invNum;

    AnnotatedImage anImage;


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
                if(!eraserMode){
                    defects.add(currentDefect);
                }
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
        drawPaint.setStrokeWidth(brushSize);
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

    public void setPenSize(int size)
    {
        brushSize = size;
        drawPaint.setStrokeWidth(brushSize);
        invalidate();
    }

    public void setEraserMode(Boolean erase)
    {
        eraserMode = erase;
        if (eraserMode){
            drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        }
        else{
            drawPaint.setXfermode(null);
        }
    }
    public void setDefect(String defect)
    {
        this.currentDefect = defect;
    }

    public String getInvNum() {
        return invNum;
    }

    public ArrayList<String> getDefects(){
        return defects;
    }
}
