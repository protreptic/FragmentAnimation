package name.peterbukhal.android.fragmentanimation.widget.checkview;

import android.content.Context;

/**
 * Created on 12/02/16 11:46 by
 *
 * @author Peter Bukhal (petr@taxik.ru)
 */
public interface CheckViewImpl {

    void initialize(CheckViewDelegate cardView, Context context, int backgroundColor, float radius,
                    float elevation, float maxElevation);

    void setRadius(CheckViewDelegate cardView, float radius);

    float getRadius(CheckViewDelegate cardView);

    void setElevation(CheckViewDelegate cardView, float elevation);

    float getElevation(CheckViewDelegate cardView);

    void initStatic();

    void setMaxElevation(CheckViewDelegate cardView, float maxElevation);

    float getMaxElevation(CheckViewDelegate cardView);

    float getMinWidth(CheckViewDelegate cardView);

    float getMinHeight(CheckViewDelegate cardView);

    void updatePadding(CheckViewDelegate cardView);

    void onCompatPaddingChanged(CheckViewDelegate cardView);

    void onPreventCornerOverlapChanged(CheckViewDelegate cardView);

    void setBackgroundColor(CheckViewDelegate cardView, int color);

}
