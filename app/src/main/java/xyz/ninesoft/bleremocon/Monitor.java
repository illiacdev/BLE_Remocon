package xyz.ninesoft.bleremocon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by illiacDev on 2017-12-22.
 */

public class Monitor extends View {

    private Paint paint;
    Bitmap bitmap;

    public Monitor(Context context) {
        super(context);
        init();
    }

    public Monitor(Context context,
                   @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect clipBounds = canvas.getClipBounds();
        canvas.drawRect(0, 0, clipBounds.right, clipBounds.bottom, paint);
        canvas.drawLine(0, 0, clipBounds.right, clipBounds.bottom, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int size2 = MeasureSpec.getSize(heightMeasureSpec);

        Log.i("BL", "onMeasure: " + size + " : " + +size2);

        bitmap = Bitmap.createBitmap(size, size2, Bitmap.Config.ARGB_8888);

    }
}
