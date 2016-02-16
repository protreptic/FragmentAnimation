package name.peterbukhal.android.fragmentanimation.widget.checkview;

import android.content.Context;

/**
 * Created on 12/02/16 11:46 by
 *
 * @author Peter Bukhal (petr@taxik.ru)
 */
public interface CheckViewImpl {

    void initialize(CheckViewDelegate delegate, Context context, int size, int backgroundColor);

    float getMinWidth(CheckViewDelegate delegate);

    float getMinHeight(CheckViewDelegate delegate);

    void updatePadding(CheckViewDelegate delegate);

    void onCompatPaddingChanged(CheckViewDelegate delegate);

    void setBackgroundColor(CheckViewDelegate delegate, int color);

}
