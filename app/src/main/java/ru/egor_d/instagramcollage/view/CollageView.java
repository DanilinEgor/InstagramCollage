package ru.egor_d.instagramcollage.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Egor Danilin on 19.01.2015.
 */
public class CollageView extends View {
    private List<Bitmap> mStore = new ArrayList<>();

    public CollageView(Context context) {
        super(context);
    }

    public CollageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CollageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CollageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int x = 0, y = 0;
        int dx = getWidth() / 3;
        int dy = getHeight() / 3;
        for (Bitmap bitmap : mStore) {
            canvas.drawBitmap(Bitmap.createScaledBitmap(bitmap, dx, dy, false), x, y, null);
            x += dx;
            y += dy;
        }
    }

    public void setBitmaps(List<Bitmap> bitmaps) {
        mStore = bitmaps;
        invalidate();
    }

    public Bitmap getCollage() {
        Bitmap b = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        draw(c);
        return b;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int size = Math.min(width, height);
        setMeasuredDimension(size, size);
    }
}
