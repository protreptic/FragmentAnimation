package name.peterbukhal.android.fragmentanimation.widget.checkview;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import name.peterbukhal.android.fragmentanimation.R;

/**
 * Created on 15/02/16 13:39 by
 *
 * @author Peter Bukhal (petr@taxik.ru)
 */
public class CheckDrawableWithShadow extends Drawable {
    // used to calculate content padding
    final static double COS_45 = Math.cos(Math.toRadians(45));

    final static float SHADOW_MULTIPLIER = 1.5f;

    final int mInsetShadow; // extra shadow to avoid gaps between card and shadow

    Paint mPaint;

    Paint mCornerShadowPaint;

    Paint mEdgeShadowPaint;

    final RectF mCheckBounds;

    Path mCornerShadowPath;

    // updated value with inset
    float mMaxShadowSize;

    // actual value set by developer
    float mRawMaxShadowSize;

    // multiplied value to account for shadow offset
    float mShadowSize;

    // actual value set by developer
    float mRawShadowSize;

    private boolean mDirty = true;

    /**
     * If shadow size is set to a value above max shadow, we print a warning
     */
    private boolean mPrintedShadowClipWarning = false;

    CheckDrawableWithShadow(Resources resources, int backgroundColor) {
        mInsetShadow = resources.getDimensionPixelSize(R.dimen.checkview_compat_inset_shadow);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(backgroundColor);
        mCornerShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mCornerShadowPaint.setStyle(Paint.Style.FILL);
        mCheckBounds = new RectF();
        mEdgeShadowPaint = new Paint(mCornerShadowPaint);
        mEdgeShadowPaint.setAntiAlias(true);
    }

    /**
     * Casts the value to an even integer.
     */
    private int toEven(float value) {
        int i = (int) (value + .5f);
        if (i % 2 == 1) {
            return i - 1;
        }
        return i;
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
        mCornerShadowPaint.setAlpha(alpha);
        mEdgeShadowPaint.setAlpha(alpha);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        mDirty = true;
    }

    void setShadowSize(float shadowSize, float maxShadowSize) {
        if (shadowSize < 0 || maxShadowSize < 0) {
            throw new IllegalArgumentException("invalid shadow size");
        }
        shadowSize = toEven(shadowSize);
        maxShadowSize = toEven(maxShadowSize);
        if (shadowSize > maxShadowSize) {
            shadowSize = maxShadowSize;
            if (!mPrintedShadowClipWarning) {
                mPrintedShadowClipWarning = true;
            }
        }
        if (mRawShadowSize == shadowSize && mRawMaxShadowSize == maxShadowSize) {
            return;
        }
        mRawShadowSize = shadowSize;
        mRawMaxShadowSize = maxShadowSize;
        mShadowSize = (int)(shadowSize * SHADOW_MULTIPLIER + mInsetShadow + .5f);
        mMaxShadowSize = maxShadowSize + mInsetShadow;
        mDirty = true;
        invalidateSelf();
    }

    @Override
    public boolean getPadding(Rect padding) {
        int vOffset = (int) Math.ceil(calculateVerticalPadding(mRawMaxShadowSize));
        int hOffset = (int) Math.ceil(calculateHorizontalPadding(mRawMaxShadowSize));

        padding.set(hOffset, vOffset, hOffset, vOffset);
        return true;
    }

    static float calculateVerticalPadding(float maxShadowSize) {
        return maxShadowSize * SHADOW_MULTIPLIER;
    }

    static float calculateHorizontalPadding(float maxShadowSize) {
        return maxShadowSize;
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mPaint.setColorFilter(cf);
        mCornerShadowPaint.setColorFilter(cf);
        mEdgeShadowPaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public void draw(Canvas canvas) {
        if (mDirty) {
            buildComponents(getBounds());
            mDirty = false;
        }
        canvas.translate(0, mRawShadowSize / 2);
        drawShadow(canvas);
        canvas.translate(0, -mRawShadowSize / 2);
    }

    private void drawShadow(Canvas canvas) {
//        final float edgeShadowTop = -mCornerRadius - mShadowSize;
//        final float inset = mCornerRadius + mInsetShadow + mRawShadowSize / 2;
//        final boolean drawHorizontalEdges = mCheckBounds.width() - 2 * inset > 0;
//        final boolean drawVerticalEdges = mCheckBounds.height() - 2 * inset > 0;
//        // LT
//        int saved = canvas.save();
//        canvas.translate(mCheckBounds.left + inset, mCheckBounds.top + inset);
//        canvas.drawPath(mCornerShadowPath, mCornerShadowPaint);
//        if (drawHorizontalEdges) {
//            canvas.drawRect(0, edgeShadowTop,
//                    mCheckBounds.width() - 2 * inset, -mCornerRadius,
//                    mEdgeShadowPaint);
//        }
//        canvas.restoreToCount(saved);
//        // RB
//        saved = canvas.save();
//        canvas.translate(mCheckBounds.right - inset, mCheckBounds.bottom - inset);
//        canvas.rotate(180f);
//        canvas.drawPath(mCornerShadowPath, mCornerShadowPaint);
//        if (drawHorizontalEdges) {
//            canvas.drawRect(0, edgeShadowTop,
//                    mCheckBounds.width() - 2 * inset, -mCornerRadius + mShadowSize,
//                    mEdgeShadowPaint);
//        }
//        canvas.restoreToCount(saved);
//        // LB
//        saved = canvas.save();
//        canvas.translate(mCheckBounds.left + inset, mCheckBounds.bottom - inset);
//        canvas.rotate(270f);
//        canvas.drawPath(mCornerShadowPath, mCornerShadowPaint);
//        if (drawVerticalEdges) {
//            canvas.drawRect(0, edgeShadowTop,
//                    mCheckBounds.height() - 2 * inset, -mCornerRadius, mEdgeShadowPaint);
//        }
//        canvas.restoreToCount(saved);
//        // RT
//        saved = canvas.save();
//        canvas.translate(mCheckBounds.right - inset, mCheckBounds.top + inset);
//        canvas.rotate(90f);
//        canvas.drawPath(mCornerShadowPath, mCornerShadowPaint);
//        if (drawVerticalEdges) {
//            canvas.drawRect(0, edgeShadowTop,
//                    mCheckBounds.height() - 2 * inset, -mCornerRadius, mEdgeShadowPaint);
//        }
//        canvas.restoreToCount(saved);
    }

//    private void buildShadowCorners() {
//        RectF innerBounds = new RectF(-mCornerRadius, -mCornerRadius, mCornerRadius, mCornerRadius);
//        RectF outerBounds = new RectF(innerBounds);
//        outerBounds.inset(-mShadowSize, -mShadowSize);
//
//        if (mCornerShadowPath == null) {
//            mCornerShadowPath = new Path();
//        } else {
//            mCornerShadowPath.reset();
//        }
//        mCornerShadowPath.setFillType(Path.FillType.EVEN_ODD);
//        mCornerShadowPath.moveTo(-mCornerRadius, 0);
//        mCornerShadowPath.rLineTo(-mShadowSize, 0);
//        // outer arc
//        mCornerShadowPath.arcTo(outerBounds, 180f, 90f, false);
//        // inner arc
//        mCornerShadowPath.arcTo(innerBounds, 270f, -90f, false);
//        mCornerShadowPath.close();
//        float startRatio = mCornerRadius / (mCornerRadius + mShadowSize);
//        mCornerShadowPaint.setShader(new RadialGradient(0, 0, mCornerRadius + mShadowSize,
//                new int[]{mShadowStartColor, mShadowStartColor, mShadowEndColor},
//                new float[]{0f, startRatio, 1f}
//                , Shader.TileMode.CLAMP));
//
//        // we offset the content shadowSize/2 pixels up to make it more realistic.
//        // this is why edge shadow shader has some extra space
//        // When drawing bottom edge shadow, we use that extra space.
//        mEdgeShadowPaint.setShader(new LinearGradient(0, -mCornerRadius + mShadowSize, 0,
//                -mCornerRadius - mShadowSize,
//                new int[]{mShadowStartColor, mShadowStartColor, mShadowEndColor},
//                new float[]{0f, .5f, 1f}, Shader.TileMode.CLAMP));
//        mEdgeShadowPaint.setAntiAlias(false);
//    }

    private void buildComponents(Rect bounds) {
        // Card is offset SHADOW_MULTIPLIER * maxShadowSize to account for the shadow shift.
        // We could have different top-bottom offsets to avoid extra gap above but in that case
        // center aligning Views inside the CardView would be problematic.
        final float verticalOffset = mRawMaxShadowSize * SHADOW_MULTIPLIER;
        mCheckBounds.set(bounds.left + mRawMaxShadowSize, bounds.top + verticalOffset,
                bounds.right - mRawMaxShadowSize, bounds.bottom - verticalOffset);
//        buildShadowCorners();
    }

    void getMaxShadowAndCornerPadding(Rect into) {
        getPadding(into);
    }

    void setShadowSize(float size) {
        setShadowSize(size, mRawMaxShadowSize);
    }

    void setMaxShadowSize(float size) {
        setShadowSize(mRawShadowSize, size);
    }

    float getShadowSize() {
        return mRawShadowSize;
    }

    float getMaxShadowSize() {
        return mRawMaxShadowSize;
    }

    float getMinWidth() {
        final float content = 2 *
                Math.max(mRawMaxShadowSize, mInsetShadow + mRawMaxShadowSize / 2);
        return content + (mRawMaxShadowSize + mInsetShadow) * 2;
    }

    float getMinHeight() {
        final float content = 2 * Math.max(mRawMaxShadowSize, mInsetShadow
                + mRawMaxShadowSize * SHADOW_MULTIPLIER / 2);
        return content + (mRawMaxShadowSize * SHADOW_MULTIPLIER + mInsetShadow) * 2;
    }

    public void setColor(int color) {
        mPaint.setColor(color);
        invalidateSelf();
    }

    interface CheckHelper {
        void drawCheck(Canvas canvas, RectF bounds, Paint paint);
    }

}
