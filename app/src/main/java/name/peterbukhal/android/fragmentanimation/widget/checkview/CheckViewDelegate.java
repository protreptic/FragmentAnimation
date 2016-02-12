package name.peterbukhal.android.fragmentanimation.widget.checkview;

import android.graphics.drawable.Drawable;

/**
 * Created on 12/02/16 11:48 by
 *
 * @author Peter Bukhal (petr@taxik.ru)
 */
public interface CheckViewDelegate {
    void setBackgroundDrawable(Drawable paramDrawable);
    Drawable getBackground();
    boolean getUseCompatPadding();
    boolean getPreventCornerOverlap();
    float getRadius();
    void setShadowPadding(int left, int top, int right, int bottom);
}
