package ru.egor_d.instagramcollage.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
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
        setBackgroundColor(Color.parseColor("#E1F5FE"));
        switch (mStore.size()) {
            case 1: {
                int x = 0, y = 0;
                for (Bitmap bitmap : mStore) {
                    canvas.drawBitmap(bitmap, x, y, null);
                }
                break;
            }
            case 2: {
                int x = 0, y = getHeight() / 4;
                int dx = getWidth() / 2;
                for (Bitmap bitmap : mStore) {
                    canvas.drawBitmap(bitmap, x, y, null);
                    x += dx;
                }
                break;
            }
            case 3: {
                int x = 0, y = 0;
                int dx = getWidth() / 2;
                int dy = getHeight() / 2;
                canvas.drawBitmap(mStore.get(0), x, y, null);
                x += dx;
                canvas.drawBitmap(mStore.get(1), x, y, null);
                x -= dx / 2;
                y += dy;
                canvas.drawBitmap(mStore.get(2), x, y, null);
                break;
            }
            case 4: {
                int x = 0, y = 0;
                int dx = getWidth() / 2;
                int dy = getHeight() / 2;
                for (int i = 0; i < 4; ++i) {
                    canvas.drawBitmap(mStore.get(i), x, y, null);
                    x += (i % 2 == 0 ? 1 : -1) * dx;
                    y += (i % 2 == 0 ? 0 : 1) * dy;
                }
                break;
            }
            case 5: {
                int x = 0, y = getWidth() / 6;
                int dx = getWidth() / 3;
                int dy = getHeight() / 3;
                for (int i = 0; i < 3; ++i) {
                    Bitmap bitmap = mStore.get(i);
                    canvas.drawBitmap(bitmap, x, y, null);
                    x += dx;
                }
                y += dy;
                x = dx / 2;
                for (int i = 3; i < 5; ++i) {
                    Bitmap bitmap = mStore.get(i);
                    canvas.drawBitmap(bitmap, x, y, null);
                    x += dx;
                }
                break;
            }
            case 6: {
                int x = 0, y = getWidth() / 6;
                int dx = getWidth() / 3;
                int dy = getHeight() / 3;
                for (int i = 0; i < 3; ++i) {
                    Bitmap bitmap = mStore.get(i);
                    canvas.drawBitmap(bitmap, x, y, null);
                    x += dx;
                }
                x = 0;
                y += dy;
                for (int i = 3; i < 6; ++i) {
                    Bitmap bitmap = mStore.get(i);
                    canvas.drawBitmap(bitmap, x, y, null);
                    x += dx;
                }
                break;
            }
            case 7: {
                int dx = getWidth() / 3;
                int dy = getHeight() / 3;
                int x = dx / 2, y = 0;
                for (int i = 0; i < 2; ++i) {
                    Bitmap bitmap = mStore.get(i);
                    canvas.drawBitmap(bitmap, x, y, null);
                    x += dx;
                }
                x = 0;
                y += dy;
                for (int i = 2; i < 5; ++i) {
                    Bitmap bitmap = mStore.get(i);
                    canvas.drawBitmap(bitmap, x, y, null);
                    x += dx;
                }
                x = dx / 2;
                y += dy;
                for (int i = 5; i < 7; ++i) {
                    Bitmap bitmap = mStore.get(i);
                    canvas.drawBitmap(bitmap, x, y, null);
                    x += dx;
                }
                break;
            }
            case 8: {
                int x = 0, y = 0;
                int dx = getWidth() / 3;
                int dy = getHeight() / 3;
                for (int i = 0; i < 3; ++i) {
                    Bitmap bitmap = mStore.get(i);
                    canvas.drawBitmap(bitmap, x, y, null);
                    x += dx;
                }
                x = 0;
                y += dy;
                for (int i = 3; i < 6; ++i) {
                    Bitmap bitmap = mStore.get(i);
                    canvas.drawBitmap(bitmap, x, y, null);
                    x += dx;
                }
                x = dx / 2;
                y += dy;
                for (int i = 6; i < 8; ++i) {
                    Bitmap bitmap = mStore.get(i);
                    canvas.drawBitmap(bitmap, x, y, null);
                    x += dx;
                }
                break;
            }
            case 9: {
                int x = 0, y = 0;
                int dx = getWidth() / 3;
                int dy = getHeight() / 3;
                for (int i = 0; i < 9; ++i) {
                    Bitmap bitmap = mStore.get(i);
                    canvas.drawBitmap(bitmap, x, y, null);
                    x += ((i + 1) % 3 == 0 ? -2 : 1) * dx;
                    y += ((i + 1) % 3 == 0 ? 1 : 0) * dy;
                }
                break;
            }
        }
    }

    public void setBitmaps(List<Bitmap> bitmaps) {
        mStore.clear();
        switch (bitmaps.size()) {
            case 1: {
                int dx = getWidth();
                int dy = getHeight();
                for (Bitmap bitmap : bitmaps) {
                    mStore.add(Bitmap.createScaledBitmap(bitmap, dx, dy, false));
                }
                break;
            }
            case 2:
            case 3:
            case 4: {
                int dx = getWidth() / 2;
                int dy = getHeight() / 2;
                for (Bitmap bitmap : bitmaps) {
                    mStore.add(Bitmap.createScaledBitmap(bitmap, dx, dy, false));
                }
                break;
            }
            case 5:
            case 6:
            case 7:
            case 8:
            case 9: {
                int dx = getWidth() / 3;
                int dy = getHeight() / 3;
                for (Bitmap bitmap : bitmaps) {
                    mStore.add(Bitmap.createScaledBitmap(bitmap, dx, dy, false));
                }
                break;
            }
        }
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
