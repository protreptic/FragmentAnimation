package name.peterbukhal.android.fragmentanimation;

import android.content.Context;
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
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.FrameLayout;

/**
 * Created on 15/02/16 15:49 by
 *
 * @author Peter Bukhal (petr@taxik.ru)
 */
public class CheckView extends FrameLayout {

    public CheckView(Context context) {
        super(context);

        init();
    }

    public CheckView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initAttrs(attrs, 0);
        init();
    }

    public CheckView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttrs(attrs, defStyleAttr);
        init();
    }

    private void initAttrs(AttributeSet attrs, int defStyleAttr) {

    }

    public void setViewBackground(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(drawable);
        } else {
            setBackgroundDrawable(drawable);
        }
    }

    private void init() {
        float size = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 7.0f, getResources().getDisplayMetrics());
        setViewBackground(new CheckDrawable(getResources(), Color.WHITE, size));
    }

    public static class CheckDrawable extends Drawable {

        private Paint mPaint;
        private final RectF mCheckBounds;
        private boolean mDirty = true;
        private final float mSize;

        public CheckDrawable(Resources resources, int backgroundColor, float size) {
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
            mPaint.setColor(backgroundColor);
            mCheckBounds = new RectF();
            mSize = size;
        }

        private Path p = new Path();

        @Override
        public void draw(Canvas canvas) {
            if (mDirty) {
                buildComponents(getBounds());
                mDirty = false;
            }

            float stepSize = mSize;
            int steps = (int) (mCheckBounds.width() / stepSize);

            float x, y;

            x = 0.0f;
            y = mCheckBounds.height();

            p.reset();
            p.moveTo(x, 0);
            p.lineTo(x, y);

            for (int i = 0; i < steps; i++) {
                p.lineTo((x += stepSize), (y - stepSize));
                p.lineTo((x += stepSize), y);
            }

            p.lineTo(x, 0);
            p.close();

            canvas.drawPath(p, mPaint);
        }

        private void buildComponents(Rect bounds) {
            mCheckBounds.set(bounds.left, bounds.top, bounds.right, bounds.bottom);
        }

        @Override
        public void setAlpha(int alpha) {
            mPaint.setAlpha(alpha);
        }

        @Override
        protected void onBoundsChange(Rect bounds) {
            super.onBoundsChange(bounds);

            mDirty = true;
        }

        @Override
        public void setColorFilter(ColorFilter cf) {
            mPaint.setColorFilter(cf);
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }

    }

}

