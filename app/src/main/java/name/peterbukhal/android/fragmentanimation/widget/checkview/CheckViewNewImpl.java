package name.peterbukhal.android.fragmentanimation.widget.checkview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

/**
 * Created on 12/02/16 16:15 by
 *
 * @author Peter Bukhal (petr@taxik.ru)
 */
public class CheckViewNewImpl implements CheckViewImpl {

    @Override
    public void initialize(CheckViewDelegate delegate, Context context, int backgroundColor, int size) {
        CheckDrawableWithShadow background = createBackground(context, backgroundColor);
        delegate.setBackgroundDrawable(background);
        updatePadding(delegate);
    }

    CheckDrawableWithShadow createBackground(Context context, int backgroundColor) {
        return new CheckDrawableWithShadow(context.getResources(), backgroundColor);
    }

    @Override
    public void updatePadding(CheckViewDelegate delegate) {
        Rect shadowPadding = new Rect();
        getShadowBackground(delegate).getMaxShadowAndCornerPadding(shadowPadding);
        ((View) delegate).setMinimumHeight((int) Math.ceil(getMinHeight(delegate)));
        ((View) delegate).setMinimumWidth((int) Math.ceil(getMinWidth(delegate)));
        delegate.setShadowPadding(shadowPadding.left, shadowPadding.top,
                shadowPadding.right, shadowPadding.bottom);
    }

    @Override
    public void onCompatPaddingChanged(CheckViewDelegate delegate) {
        // NO OP
    }

    @Override
    public void setBackgroundColor(CheckViewDelegate delegate, int color) {
        getShadowBackground(delegate).setColor(color);
    }

    @Override
    public float getMinWidth(CheckViewDelegate delegate) {
        return getShadowBackground(delegate).getMinWidth();
    }

    @Override
    public float getMinHeight(CheckViewDelegate delegate) {
        return getShadowBackground(delegate).getMinHeight();
    }

    private CheckDrawableWithShadow getShadowBackground(CheckViewDelegate delegate) {
        return ((CheckDrawableWithShadow) delegate.getBackground());
    }

}
