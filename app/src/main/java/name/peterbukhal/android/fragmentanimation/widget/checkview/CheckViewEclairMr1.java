package name.peterbukhal.android.fragmentanimation.widget.checkview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

/**
 * Created on 12/02/16 12:03 by
 *
 * @author Peter Bukhal (petr@taxik.ru)
 */
public class CheckViewEclairMr1 implements CheckViewImpl {

    final RectF sCornerRect = new RectF();

    @Override
    public void initStatic() {
        // Draws a round rect using 7 draw operations. This is faster than using
        // canvas.drawRoundRect before JBMR1 because API 11-16 used alpha mask textures to draw
        // shapes.
        RoundRectDrawableWithShadow.sRoundRectHelper
                = new RoundRectDrawableWithShadow.RoundRectHelper() {
            @Override
            public void drawRoundRect(Canvas canvas, RectF bounds, float cornerRadius,
                                      Paint paint) {
                final float twoRadius = cornerRadius * 2;
                final float innerWidth = bounds.width() - twoRadius - 1;
                final float innerHeight = bounds.height() - twoRadius - 1;
                // increment it to account for half pixels.
                if (cornerRadius >= 1f) {
                    cornerRadius += .5f;
                    sCornerRect.set(-cornerRadius, -cornerRadius, cornerRadius, cornerRadius);
                    int saved = canvas.save();
                    canvas.translate(bounds.left + cornerRadius, bounds.top + cornerRadius);
                    canvas.drawArc(sCornerRect, 180, 90, true, paint);
                    canvas.translate(innerWidth, 0);
                    canvas.rotate(90);
                    canvas.drawArc(sCornerRect, 180, 90, true, paint);
                    canvas.translate(innerHeight, 0);
                    canvas.rotate(90);
                    canvas.drawArc(sCornerRect, 180, 90, true, paint);
                    canvas.translate(innerWidth, 0);
                    canvas.rotate(90);
                    canvas.drawArc(sCornerRect, 180, 90, true, paint);
                    canvas.restoreToCount(saved);
                    // draw top and bottom pieces
                    canvas.drawRect(bounds.left + cornerRadius - 1f, bounds.top,
                            bounds.right - cornerRadius + 1f, bounds.top + cornerRadius,
                            paint);
                    canvas.drawRect(bounds.left + cornerRadius - 1f,
                            bounds.bottom - cornerRadius + 1f, bounds.right - cornerRadius + 1f,
                            bounds.bottom, paint);
                }
                // center
                canvas.drawRect(bounds.left, bounds.top + Math.max(0, cornerRadius - 1f),
                        bounds.right, bounds.bottom - cornerRadius + 1f, paint);
            }
        };
    }

    @Override
    public void initialize(CheckViewDelegate cardView, Context context, int backgroundColor,
                           float radius, float elevation, float maxElevation) {
        RoundRectDrawableWithShadow background = createBackground(context, backgroundColor, radius,
                elevation, maxElevation);
        background.setAddPaddingForCorners(cardView.getPreventCornerOverlap());
        cardView.setBackgroundDrawable(background);
        updatePadding(cardView);
    }

    RoundRectDrawableWithShadow createBackground(Context context, int backgroundColor,
                                                 float radius, float elevation, float maxElevation) {
        return new RoundRectDrawableWithShadow(context.getResources(), backgroundColor, radius,
                elevation, maxElevation);
    }

    @Override
    public void updatePadding(CheckViewDelegate cardView) {
        Rect shadowPadding = new Rect();
        getShadowBackground(cardView).getMaxShadowAndCornerPadding(shadowPadding);
        ((View) cardView).setMinimumHeight((int) Math.ceil(getMinHeight(cardView)));
        ((View) cardView).setMinimumWidth((int) Math.ceil(getMinWidth(cardView)));
        cardView.setShadowPadding(shadowPadding.left, shadowPadding.top,
                shadowPadding.right, shadowPadding.bottom);
    }

    @Override
    public void onCompatPaddingChanged(CheckViewDelegate checkView) {
        // NO OP
    }

    @Override
    public void onPreventCornerOverlapChanged(CheckViewDelegate checkView) {
        getShadowBackground(checkView).setAddPaddingForCorners(checkView.getPreventCornerOverlap());
        updatePadding(checkView);
    }

    @Override
    public void setBackgroundColor(CheckViewDelegate checkView, int color) {
        getShadowBackground(checkView).setColor(color);
    }

    @Override
    public void setRadius(CheckViewDelegate checkView, float radius) {
        getShadowBackground(checkView).setCornerRadius(radius);
        updatePadding(checkView);
    }

    @Override
    public float getRadius(CheckViewDelegate checkView) {
        return getShadowBackground(checkView).getCornerRadius();
    }

    @Override
    public void setElevation(CheckViewDelegate checkView, float elevation) {
        getShadowBackground(checkView).setShadowSize(elevation);
    }

    @Override
    public float getElevation(CheckViewDelegate checkView) {
        return getShadowBackground(checkView).getShadowSize();
    }

    @Override
    public void setMaxElevation(CheckViewDelegate checkView, float maxElevation) {
        getShadowBackground(checkView).setMaxShadowSize(maxElevation);
        updatePadding(checkView);
    }

    @Override
    public float getMaxElevation(CheckViewDelegate checkView) {
        return getShadowBackground(checkView).getMaxShadowSize();
    }

    @Override
    public float getMinWidth(CheckViewDelegate checkView) {
        return getShadowBackground(checkView).getMinWidth();
    }

    @Override
    public float getMinHeight(CheckViewDelegate checkView) {
        return getShadowBackground(checkView).getMinHeight();
    }

    private RoundRectDrawableWithShadow getShadowBackground(CheckViewDelegate checkView) {
        return ((RoundRectDrawableWithShadow) checkView.getBackground());
    }
}

