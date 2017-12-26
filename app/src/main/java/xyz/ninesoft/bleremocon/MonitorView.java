package xyz.ninesoft.bleremocon;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by illiacDev on 2017-12-22.
 */

public class MonitorView extends View {

    private Disposable subscribe;

    public void stop() {
        subscribe.dispose();
        subscribe = null;
    }

    static public class Probe {

        Queue<Float> floats = new ArrayDeque<>();
        public int color = Color.WHITE;

        Float prev = null;

        public void putData(float fx) {
            floats.add(fx);
//            subject.onNext(iout -> iout.onData(prev, fx));
        }
    }

    public void addProbe(Probe probe) {

        probes.add(probe);

    }

    public ArrayList<Probe> probes = new ArrayList<>();
    public int interval = 1000;
    boolean runFlag = false;

    private Paint paint, brush, grid_brush;
    Bitmap bitmap;
    Canvas buffer;

    Bitmap bitmap_temp;
    Canvas buffer_temp;

    Bitmap bitmap4_grid;
    Canvas buffer4_grid;

    public void start(Integer interval_ms) {
        interval = interval_ms;
        //            invalidate();
        subscribe = Observable.interval(interval_ms, TimeUnit.MILLISECONDS).subscribe(
                aLong -> {
                    ((Activity) getContext()).runOnUiThread(() -> invalidate());
//            invalidate();
                });
    }

    public MonitorView(Context context) {
        super(context);
        init();
    }

    public MonitorView(Context context,
                       @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);

        brush = new Paint();
        brush.setColor(Color.WHITE);
        brush.setStrokeWidth(3);
        brush.setStyle(Paint.Style.STROKE);

        grid_brush = new Paint();
        grid_brush.setColor(Color.GREEN);
        grid_brush.setStrokeWidth(2);
        grid_brush.setStyle(Paint.Style.STROKE);

    }

    Long prevDrawTimestamp = null;

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

        long curr = System.currentTimeMillis();
        if ((prevDrawTimestamp != null) && (curr - prevDrawTimestamp < interval)) {
            long elepsed = curr - prevDrawTimestamp;
            System.out.println("retuen ++++++++++++++++++++++++++++++ " + elepsed);
            canvas.drawBitmap(bitmap4_grid, 0, 0, null);
            canvas.drawBitmap(bitmap, 0, 0, null);
            return;
        }

        if(subscribe == null){
            canvas.drawBitmap(bitmap4_grid, 0, 0, null);
            canvas.drawBitmap(bitmap, 0, 0, null);
            return;
        }

        System.out.println("draw *************  ++++++++++++++++++++++++++++++");
        scrollImageBuffer();
        plotData();
        prevDrawTimestamp = curr;

        canvas.drawBitmap(bitmap4_grid, 0, 0, null);
        canvas.drawBitmap(bitmap, 0, 0, null);
    }

    private void plotData() {
        for (Probe probe : probes)
            drawProbe(probe);
    }

    private void drawProbe(Probe probe) {
        backupColor(brush);
        brush.setColor(probe.color);

        if (probe.floats.isEmpty()) {
            if (probe.prev != null)
                buffer.drawPoint(0, getFx(probe.prev), brush);
            restoreColor(brush);
            return;
        }

        int arraySize = probe.floats.size() * 2 + ((probe.prev != null) ? 2 : 0);
        int length = probe.floats.size() + ((probe.prev != null) ? 1 : 0);
        int startIdx = ((probe.prev != null) ? 1 : 0);

        float[] array = new float[arraySize];
        if (startIdx == 1) {
            array[0] = 0;
            array[1] = getFx(probe.prev);
            System.out.println("이전 값 존재 !!!!!!!!!!!!!!!!!!!! " + length);
            System.out.println("array list ------------> " + array[1]);
        }

        for (int i = startIdx; i < length; i++) {
            array[i * 2] = 0;
            float v = probe.floats.poll();
            probe.prev = v;

            array[i * 2 + 1] = getFx(v);
            System.out.println("array list ------------> " + array[i * 2 + 1]);
//            buffer.drawPoint(0,array[i*2+1],brush);
        }
        System.out.println(
                "-------------------------------------------------------------------------------");


        buffer.drawLines(array, brush);
        restoreColor(brush);
    }

    int backup_color;
    private void restoreColor(Paint brush) {
        brush.setColor(backup_color);
    }

    private void backupColor(Paint brush) {
        backup_color = brush.getColor();
    }

    private void plotPrevData(Probe probe) {

    }

    private void scrollImageBuffer() {
        buffer_temp.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        buffer_temp.drawBitmap(bitmap, 1, 0, null);
//        buffer_temp.drawPoint(0, fx, brush);
        buffer.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        buffer.drawBitmap(bitmap_temp, 0, 0, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        initForeground(width, height);

        initTempBuffer(width, height);

        initBackgroundGridView(width, height);

    }

    private void initTempBuffer(int width, int height) {
        bitmap_temp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        buffer_temp = new Canvas(bitmap_temp);
        buffer_temp.drawColor(Color.TRANSPARENT);
    }

    private void initForeground(int width, int height) {
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        buffer = new Canvas(bitmap);
        buffer.drawColor(Color.TRANSPARENT);
    }

    private void initBackgroundGridView(int width, int height) {
        bitmap4_grid = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        buffer4_grid = new Canvas(bitmap4_grid);
        buffer4_grid.drawColor(Color.BLACK);
        int gridSize = height / 4;
        for (int i = 0; i < height; i += gridSize)
            buffer4_grid.drawLine(0, i, width, i, grid_brush);

        for (int i = 0; i < width; i += gridSize)
            buffer4_grid.drawLine(i, 0, i, height, grid_brush);

    }

    private void pubData(float prev, float data) {
        if (true)
            return;

        float fx = getFx(data);

        buffer_temp.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        buffer_temp.drawBitmap(bitmap, 1, 0, null);
        buffer_temp.drawPoint(0, fx, brush);
        buffer.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        buffer.drawBitmap(bitmap_temp, 0, 0, null);
        invalidate();

    }

    private float getFx(float data) {
        float d1 = data;
        float d2 = 1 - data;
        float a = d1;
        float b = d2;
        float fx1 = 0;
        float fx2 = getHeight();
        return b * fx1 + a * fx2;
    }

    public void clear(){
        for (Probe probe : probes)
            probe.floats.clear();

        buffer.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        invalidate();
    }
}
