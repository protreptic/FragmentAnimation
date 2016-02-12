package name.peterbukhal.android.fragmentanimation.widget.checkview;

import android.annotation.TargetApi;
import android.content.Context;
import android.view.View;

/**
 * Created on 12/02/16 12:09 by
 *
 * @author Peter Bukhal (petr@taxik.ru)
 */
@TargetApi(21)
public class CheckViewApi21 implements CheckViewImpl {

    @Override
    public void initialize(CheckViewDelegate checkView, Context context, int backgroundColor,
                           float radius, float elevation, float maxElevation) {
        final RoundRectDrawable backgroundDrawable = new RoundRectDrawable(backgroundColor, radius);
        checkView.setBackgroundDrawable(backgroundDrawable);
        View view = (View) checkView;
        view.setClipToOutline(true);
        view.setElevation(elevation);
        setMaxElevation(checkView, maxElevation);
    }

    @Override
    public void setRadius(CheckViewDelegate checkView, float radius) {
        ((RoundRectDrawable) (checkView.getBackground())).setRadius(radius);
    }

    @Override
    public void initStatic() {
    }

    @Override
    public void setMaxElevation(CheckViewDelegate checkView, float maxElevation) {
        ((RoundRectDrawable) (checkView.getBackground())).setPadding(maxElevation,
                checkView.getUseCompatPadding(), checkView.getPreventCornerOverlap());
        updatePadding(checkView);
    }

    @Override
    public float getMaxElevation(CheckViewDelegate checkView) {
        return ((RoundRectDrawable) (checkView.getBackground())).getPadding();
    }

    @Override
    public float getMinWidth(CheckViewDelegate checkView) {
        return getRadius(checkView) * 2;
    }

    @Override
    public float getMinHeight(CheckViewDelegate checkView) {
        return getRadius(checkView) * 2;
    }

    @Override
    public float getRadius(CheckViewDelegate checkView) {
        return ((RoundRectDrawable) (checkView.getBackground())).getRadius();
    }

    @Override
    public void setElevation(CheckViewDelegate checkView, float elevation) {
        ((View) checkView).setElevation(elevation);
    }

    @Override
    public float getElevation(CheckViewDelegate checkView) {
        return ((View) checkView).getElevation();
    }

    @Override
    public void updatePadding(CheckViewDelegate checkView) {
        if (!checkView.getUseCompatPadding()) {
            checkView.setShadowPadding(0, 0, 0, 0);
            return;
        }
        float elevation = getMaxElevation(checkView);
        final float radius = getRadius(checkView);
        int hPadding = (int) Math.ceil(RoundRectDrawableWithShadow
                .calculateHorizontalPadding(elevation, radius, checkView.getPreventCornerOverlap()));
        int vPadding = (int) Math.ceil(RoundRectDrawableWithShadow
                .calculateVerticalPadding(elevation, radius, checkView.getPreventCornerOverlap()));
        checkView.setShadowPadding(hPadding, vPadding, hPadding, vPadding);
    }

    @Override
    public void onCompatPaddingChanged(CheckViewDelegate checkView) {
        setMaxElevation(checkView, getMaxElevation(checkView));
    }

    @Override
    public void onPreventCornerOverlapChanged(CheckViewDelegate checkView) {
        setMaxElevation(checkView, getMaxElevation(checkView));
    }

    @Override
    public void setBackgroundColor(CheckViewDelegate checkView, int color) {
        ((RoundRectDrawable) (checkView.getBackground())).setColor(color);
    }
}