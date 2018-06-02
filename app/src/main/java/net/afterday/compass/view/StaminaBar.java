package net.afterday.compass.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import net.afterday.compass.R;
import net.afterday.compass.models.BoosterModifier;

public class StaminaBar extends View
{
    private static final String TAG = "StaminaBar";

    private static final int WIDGET_WIDTH = 889;
    private static final int WIDGET_HEIGHT = 126;

    // Dimension stuff
    private int mWidth;
    private int mHeight;

    private float mScaleFactorX;
    private float mScaleFactorY;

    // Paint stuff
    private Paint mPaint;
    private Matrix mMatrix;
    private RectF mRect;

    // Bitmaps
    private Bitmap mTopImage;

    private float mPercentage;
    private BoosterModifier mItem;

    public StaminaBar(Context context) {
        super(context);

        init();
    }

    public StaminaBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public StaminaBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    public void setItem(@Nullable BoosterModifier item) {
        mItem = item;

        if (mItem == null) {
            mPercentage = 0f;
            invalidate();
            return;
        }

        long remaining = mItem.getActivated() + mItem.getDuration() - System.currentTimeMillis();
        if (remaining > 0) {
            mPercentage = (float) remaining / mItem.getDuration();
        } else {
            mPercentage = 0f;
        }

        invalidate();
    }

    public void setPercents(double percents)
    {
        Log.d(TAG, "Percents: " + percents);
        if(mPercentage == percents)
        {
            return;
        }
        if(percents > 100)
        {
            percents = 100;
        }else if(percents < 0)
        {
            percents = 0;
        }
        mPercentage = (float) percents;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = (int) (6 * mPercentage);
        drawRect(width, 40, 132, 27, mRect);


        canvas.drawBitmap(mTopImage, mMatrix, null);
        canvas.drawRect(mRect, mPaint);
    }

    protected void init() {
        mTopImage = BitmapFactory.decodeResource(getResources(), R.drawable.bar_stamina);
        mMatrix = new Matrix();
        mRect = new RectF();
        mPaint = new Paint();
        mPaint.setARGB(255,255,200,100);
        mPaint.setAlpha(100);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Get sizes
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int finalMeasureSpecX = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
        int finalMeasureSpecY = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
        super.onMeasure(finalMeasureSpecX, finalMeasureSpecY);

        mWidth = widthSize;
        mHeight = heightSize;

        mScaleFactorX = (float) mWidth / WIDGET_WIDTH;
        mScaleFactorY = (float) mHeight / WIDGET_HEIGHT;
        mMatrix.reset();
        mMatrix.postScale(mScaleFactorX, mScaleFactorY);
        mMatrix.postTranslate(0, 0);
    }

    private void drawRect(int width, int height, int left, int top, RectF rect)
    {
        rect.set(
                left * mScaleFactorX,
                top * mScaleFactorY,
                (left + width) * mScaleFactorX,
                (top + height) * mScaleFactorY
        );
    }
}
