package name.peterbukhal.android.fragmentanimation;

import android.content.Context;
import android.graphics.Color;
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

}

