package name.peterbukhal.android.fragmentanimation.widget.checkview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import name.peterbukhal.android.fragmentanimation.R;

/**
 * Created on 12/02/16 11:44 by
 *
 * @author Peter Bukhal (petr@taxik.ru)
 */
public class CheckView  extends FrameLayout implements CheckViewDelegate {

    private static final CheckViewImpl IMPL;

    static {
        if (Build.VERSION.SDK_INT >= 21) {
            IMPL = new CheckViewApi21();
        } else if (Build.VERSION.SDK_INT >= 17) {
            IMPL = new CheckViewJellybeanMr1();
        } else {
            IMPL = new CheckViewEclairMr1();
        }
        IMPL.initStatic();
    }

    private boolean mCompatPadding;

    private boolean mPreventCornerOverlap;

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
    public void setPadding(int left, int top, int right, int bottom) {
        // NO OP
    }

    public void setPaddingRelative(int start, int top, int end, int bottom) {
        // NO OP
    }

    /**
     * Returns whether CheckView will add inner padding on platforms L and after.
     *
     * @return True CheckView adds inner padding on platforms L and after to have same dimensions
     * with platforms before L.
     */
    @Override
    public boolean getUseCompatPadding() {
        return mCompatPadding;
    }

    /**
     * CheckView adds additional padding to draw shadows on platforms before L.
     * <p>
     * This may cause Checks to have different sizes between L and before L. If you need to align
     * CheckView with other Views, you may need api version specific dimension resources to account
     * for the changes.
     * As an alternative, you can set this flag to <code>true</code> and CheckView will add the same
     * padding values on platforms L and after.
     * <p>
     * Since setting this flag to true adds unnecessary gaps in the UI, default value is
     * <code>false</code>.
     *
     * @param useCompatPadding True if CheckView should add padding for the shadows on platforms L
     *                         and above.
     */
    public void setUseCompatPadding(boolean useCompatPadding) {
        if (mCompatPadding == useCompatPadding) {
            return;
        }
        mCompatPadding = useCompatPadding;
        IMPL.onCompatPaddingChanged(this);
    }

    /**
     * Sets the padding between the Check's edges and the children of CheckView.
     * <p>
     * Depending on platform version or {@link #getUseCompatPadding()} settings, CheckView may
     * update these values before calling {@link android.view.View#setPadding(int, int, int, int)}.
     *
     * @param left   The left padding in pixels
     * @param top    The top padding in pixels
     * @param right  The right padding in pixels
     * @param bottom The bottom padding in pixels
     */
    public void setContentPadding(int left, int top, int right, int bottom) {
        mContentPadding.set(left, top, right, bottom);
        IMPL.updatePadding(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!(IMPL instanceof CheckViewApi21)) {
            final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
            switch (widthMode) {
                case MeasureSpec.EXACTLY:
                case MeasureSpec.AT_MOST:
                    final int minWidth = (int) Math.ceil(IMPL.getMinWidth(this));
                    widthMeasureSpec = MeasureSpec.makeMeasureSpec(Math.max(minWidth,
                            MeasureSpec.getSize(widthMeasureSpec)), widthMode);
                    break;
            }

            final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
            switch (heightMode) {
                case MeasureSpec.EXACTLY:
                case MeasureSpec.AT_MOST:
                    final int minHeight = (int) Math.ceil(IMPL.getMinHeight(this));
                    heightMeasureSpec = MeasureSpec.makeMeasureSpec(Math.max(minHeight,
                            MeasureSpec.getSize(heightMeasureSpec)), heightMode);
                    break;
            }
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    private void initialize(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CheckView, defStyleAttr,
                R.style.CheckView);
        int backgroundColor = a.getColor(R.styleable.CheckView_checkBackgroundColor, 0);
        float radius = a.getDimension(R.styleable.CheckView_checkCornerRadius, 0);
        float elevation = a.getDimension(R.styleable.CheckView_checkElevation, 0);
        float maxElevation = a.getDimension(R.styleable.CheckView_checkMaxElevation, 0);
        mCompatPadding = a.getBoolean(R.styleable.CheckView_checkUseCompatPadding, false);
        mPreventCornerOverlap = a.getBoolean(R.styleable.CheckView_checkPreventCornerOverlap, true);
        int defaultPadding = a.getDimensionPixelSize(R.styleable.CheckView_checkContentPadding, 0);
        mContentPadding.left = a.getDimensionPixelSize(R.styleable.CheckView_checkContentPaddingLeft,
                defaultPadding);
        mContentPadding.top = a.getDimensionPixelSize(R.styleable.CheckView_checkContentPaddingTop,
                defaultPadding);
        mContentPadding.right = a.getDimensionPixelSize(R.styleable.CheckView_checkContentPaddingRight,
                defaultPadding);
        mContentPadding.bottom = a.getDimensionPixelSize(R.styleable.CheckView_checkContentPaddingBottom,
                defaultPadding);
        if (elevation > maxElevation) {
            maxElevation = elevation;
        }
        a.recycle();
        IMPL.initialize(this, context, backgroundColor, radius, elevation, maxElevation);
    }

    /**
     * Updates the background color of the CheckView
     *
     * @param color The new color to set for the check background
     */
    public void setCardBackgroundColor(int color) {
        IMPL.setBackgroundColor(this, color);
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

    /**
     * Returns the inner padding after the Check's top edge
     *
     * @return the inner padding after the Check's top edge
     */
    public int getContentPaddingTop() {
        return mContentPadding.top;
    }

    /**
     * Returns the inner padding before the Check's bottom edge
     *
     * @return the inner padding before the Check's bottom edge
     */
    public int getContentPaddingBottom() {
        return mContentPadding.bottom;
    }

    /**
     * Updates the corner radius of the CheckView.
     *
     * @param radius The radius in pixels of the corners of the rectangle shape
     * @see #setRadius(float)
     */
    public void setRadius(float radius) {
        IMPL.setRadius(this, radius);
    }

    /**
     * Returns the corner radius of the CheckView.
     *
     * @return Corner radius of the CheckView
     * @see #getRadius()
     */
    public float getRadius() {
        return IMPL.getRadius(this);
    }

    /**
     * Internal method used by CheckView implementations to update the padding.
     */
    @Override
    public void setShadowPadding(int left, int top, int right, int bottom) {
        mShadowBounds.set(left, top, right, bottom);
        super.setPadding(left + mContentPadding.left, top + mContentPadding.top,
                right + mContentPadding.right, bottom + mContentPadding.bottom);
    }

    /**
     * Updates the backward compatible elevation of the CheckView.
     *
     * @param radius The backward compatible elevation in pixels.
     * @see #getCheckElevation()
     * @see #setMaxCheckElevation(float)
     */
    public void setCheckElevation(float radius) {
        IMPL.setElevation(this, radius);
    }

    /**
     * Returns the backward compatible elevation of the CheckView.
     *
     * @return Elevation of the CheckView
     * @see #setCheckElevation(float)
     * @see #getMaxCheckElevation()
     */
    public float getCheckElevation() {
        return IMPL.getElevation(this);
    }

    /**
     * Updates the backward compatible elevation of the CheckView.
     * <p>
     * Calling this method has no effect if device OS version is L or newer and
     * {@link #getUseCompatPadding()} is <code>false</code>.
     *
     * @param radius The backward compatible elevation in pixels.
     * @see #setCheckElevation(float)
     * @see #getMaxCheckElevation()
     */
    public void setMaxCheckElevation(float radius) {
        IMPL.setMaxElevation(this, radius);
    }

    /**
     * Returns the backward compatible elevation of the CheckView.
     *
     * @return Elevation of the CheckView
     * @see #setMaxCheckElevation(float)
     * @see #getCheckElevation()
     */
    public float getMaxCheckElevation() {
        return IMPL.getMaxElevation(this);
    }

    /**
     * Returns whether CheckView should add extra padding to content to avoid overlaps with rounded
     * corners on API versions 20 and below.
     *
     * @return True if CheckView prevents overlaps with rounded corners on platforms before L.
     *         Default value is <code>true</code>.
     */
    @Override
    public boolean getPreventCornerOverlap() {
        return mPreventCornerOverlap;
    }

    /**
     * On API 20 and before, CheckView does not clip the bounds of the Check for the rounded corners.
     * Instead, it adds padding to content so that it won't overlap with the rounded corners.
     * You can disable this behavior by setting this field to <code>false</code>.
     * <p>
     * Setting this value on API 21 and above does not have any effect unless you have enabled
     * compatibility padding.
     *
     * @param preventCornerOverlap Whether CheckView should add extra padding to content to avoid
     *                             overlaps with the CheckView corners.
     * @see #setUseCompatPadding(boolean)
     */
    public void setPreventCornerOverlap(boolean preventCornerOverlap) {
        if (preventCornerOverlap == mPreventCornerOverlap) {
            return;
        }
        mPreventCornerOverlap = preventCornerOverlap;
        IMPL.onPreventCornerOverlapChanged(this);
    }

}
