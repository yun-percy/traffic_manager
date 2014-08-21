package com.yusun.traffic_manager;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
public class CircularProgressDrawable extends Drawable {
    public static final int PROGRESS_FACTOR = -360;
    public static final String CIRCLE_FILL_PROPERTY = "circleScale";
    public static final String PROGRESS_PROPERTY = "progress";
    public static final String TAG = "CircularProgressDrawable";
    private final Paint paint;
    protected float progress;
    protected int outlineColor;
    protected int ringColor;
    protected int centerColor;
    protected final RectF arcElements;
    protected final int ringWidth;
    protected float circleScale;
    protected boolean indeterminate;
    public CircularProgressDrawable(int ringWidth, int outlineColor, int ringColor, int centerColor) {
        this.progress = 0;
        this.outlineColor = outlineColor;
        this.ringColor = ringColor;
        this.centerColor = centerColor;
        this.paint = new Paint();
        this.paint.setAntiAlias(true);
        this.ringWidth = ringWidth;
        this.arcElements = new RectF();
        this.circleScale = 1;
        this.indeterminate = false;
    }
    @Override
    public void draw(Canvas canvas) {
        final Rect bounds = getBounds();
        int size = bounds.height() > bounds.width() ? bounds.width() : bounds.height();
        float outerRadius = ((size / 2) * 0.75f) * 0.937f;
        float innerRadius = ((size / 2) * 0.75f) * 0.75f;
        float offsetX = (bounds.width() - outerRadius * 2) / 2;
        float offsetY = (bounds.height() - outerRadius * 2) / 2;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        paint.setColor(outlineColor);
        canvas.drawCircle(bounds.centerX(), bounds.centerY(), outerRadius, paint);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(centerColor);
        canvas.drawCircle(bounds.centerX(), bounds.centerY(), innerRadius * circleScale, paint);
        int halfRingWidth = ringWidth / 2;
        float arcX0 = offsetX + halfRingWidth;
        float arcY0 = offsetY + halfRingWidth;
        float arcX = offsetX + outerRadius * 2 - halfRingWidth;
        float arcY = offsetY + outerRadius * 2 - halfRingWidth;
        paint.setColor(ringColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(ringWidth);
        paint.setStrokeCap(Paint.Cap.ROUND);
        arcElements.set(arcX0, arcY0, arcX, arcY);
        if (indeterminate) {
            canvas.drawArc(arcElements, progress, 90, false, paint);
        } else {
            canvas.drawArc(arcElements, 89, progress, false, paint);
        }
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
    }
    @Override
    public int getOpacity() {
        return paint.getAlpha();
    }
    public float getProgress() {
        return progress / PROGRESS_FACTOR;
    }
    public void setProgress(float progress) {
        if (indeterminate) {
            this.progress = progress;
        } else {
            this.progress = PROGRESS_FACTOR * progress;
        }
        invalidateSelf();
    }
    public float getCircleScale() {
        return circleScale;
    }
    public void setCircleScale(float circleScale) {
        this.circleScale = circleScale;
        invalidateSelf();
    }
    public boolean isIndeterminate() {
        return indeterminate;
    }
    public void setIndeterminate(boolean indeterminate) {
        this.indeterminate = indeterminate;
    }
    public int getOutlineColor() {
        return outlineColor;
    }
    public int getRingColor() {
        return ringColor;
    }
    public int getCenterColor() {
        return centerColor;
    }
    public void setOutlineColor(int outlineColor) {
        this.outlineColor = outlineColor;
        invalidateSelf();
    }
    public void setRingColor(int ringColor) {
        this.ringColor = ringColor;
        invalidateSelf();
    }
    public void setCenterColor(int centerColor) {
        this.centerColor = centerColor;
        invalidateSelf();
    }
}