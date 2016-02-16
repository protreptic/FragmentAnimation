package name.peterbukhal.android.fragmentanimation.widget.checkview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import name.peterbukhal.android.fragmentanimation.R;

/**
 * Created on 12/02/16 11:44 by
 *
 * @author Peter Bukhal (petr@taxik.ru)
 */
public class CheckView extends FrameLayout {

    private boolean mCompatPadding;

    private final Rect mContentPadding = new Rect();
    private final Rect mShadowBounds = new Rect();

    public CheckView (Context context) {
        super(context);

        initialize(context, null, 0);
    }

    public CheckView (Context context, AttributeSet attrs) {
        super(context, attrs);

        initialize(context, attrs, 0);
    }

    public CheckView (Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initialize(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(2);
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setShadowLayer(2, 0, 3, Color.DKGRAY);

        Path p = new Path();

        int steps = canvas.getWidth() / 14;
        float stepSize = steps / 2;
        float x, y;

        x = 5.0f;
        y = canvas.getHeight() - 5.0f;

        p.reset();
        p.moveTo(x, 0);
        p.lineTo(x, y);

        for (int i = 0; i < steps; i++) {
            p.lineTo((x += stepSize), (y - stepSize));
            p.lineTo((x += stepSize), y);
        }

        p.lineTo(x, 0);
        p.close();

        canvas.drawPath(p, paint);
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        // NO OP
    }

    public void setPaddingRelative(int start, int top, int end, int bottom) {
        // NO OP
    }

    public boolean getUseCompatPadding() {
        return mCompatPadding;
    }

    public void setUseCompatPadding(boolean useCompatPadding) {
        if (mCompatPadding == useCompatPadding) {
            return;
        }
        mCompatPadding = useCompatPadding;
    }

    public void setContentPadding(int left, int top, int right, int bottom) {
        mContentPadding.set(left, top, right, bottom);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        switch (widthMode) {
//            case MeasureSpec.EXACTLY:
//            case MeasureSpec.AT_MOST:
//                final int minWidth = (int) Math.ceil(getMinWidth(this));
//                widthMeasureSpec = MeasureSpec.makeMeasureSpec(Math.max(minWidth,
//                        MeasureSpec.getSize(widthMeasureSpec)), widthMode);
//                break;
//        }
//
//        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        switch (heightMode) {
//            case MeasureSpec.EXACTLY:
//            case MeasureSpec.AT_MOST:
//                final int minHeight = (int) Math.ceil(IMPL.getMinHeight(this));
//                heightMeasureSpec = MeasureSpec.makeMeasureSpec(Math.max(minHeight,
//                        MeasureSpec.getSize(heightMeasureSpec)), heightMode);
//                break;
//        }
//
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }

    private void initialize(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CheckView, defStyleAttr,
                R.style.CheckView);
        int backgroundColor = a.getColor(R.styleable.CheckView_checkBackgroundColor, 0);
        float elevation = a.getDimension(R.styleable.CheckView_checkElevation, 0);
        float maxElevation = a.getDimension(R.styleable.CheckView_checkMaxElevation, 0);
        mCompatPadding = a.getBoolean(R.styleable.CheckView_checkUseCompatPadding, false);
        int defaultPadding = a.getDimensionPixelSize(R.styleable.CheckView_checkContentPadding, 0);
        mContentPadding.left = a.getDimensionPixelSize(R.styleable.CheckView_checkContentPaddingLeft,
                defaultPadding);
        mContentPadding.top = a.getDimensionPixelSize(R.styleable.CheckView_checkContentPaddingTop,
                defaultPadding);
        mContentPadding.right = a.getDimensionPixelSize(R.styleable.CheckView_checkContentPaddingRight,
                defaultPadding);
        mContentPadding.bottom = a.getDimensionPixelSize(R.styleable.CheckView_checkContentPaddingBottom,
                defaultPadding);
        int size = a.getInt(R.styleable.CheckView_checkSize, 10);
        if (elevation > maxElevation) {
            maxElevation = elevation;
        }
        a.recycle();
        //IMPL.initialize(this, context, backgroundColor, size);
    }

    /**
     * Updates the background color of the CheckView
     *
     * @param color The new color to set for the check background
     */
    public void setCheckBackgroundColor(int color) {
        //IMPL.setBackgroundColor(this, color);
    }

    /**
     * Returns the inner padding after the Check's left edge
     *
     * @return the inner padding after the Check's left edge
     */
    public int getContentPaddingLeft() {
        return mContentPadding.left;
    }

    /**
     * Returns the inner padding before the Check's right edge
     *
     * @return the inner padding before the Check's right edge
     */
    public int getContentPaddingRight() {
        return mContentPadding.right;
    }

    public int getContentPaddingTop() {
        return mContentPadding.top;
    }

    public int getContentPaddingBottom() {
        return mContentPadding.bottom;
    }

    public void setShadowPadding(int left, int top, int right, int bottom) {
        mShadowBounds.set(left, top, right, bottom);
        super.setPadding(left + mContentPadding.left, top + mContentPadding.top,
                right + mContentPadding.right, bottom + mContentPadding.bottom);
    }

}
