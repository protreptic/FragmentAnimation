package name.peterbukhal.android.fragmentanimation.widget.checkview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created on 12/02/16 12:09 by
 *
 * @author Peter Bukhal (petr@taxik.ru)
 */
public class CheckViewJellybeanMr1  extends CheckViewEclairMr1 {

    @Override
    public void initStatic() {
        RoundRectDrawableWithShadow.sRoundRectHelper
                = new RoundRectDrawableWithShadow.RoundRectHelper() {
            @Override
            public void drawRoundRect(Canvas canvas, RectF bounds, float cornerRadius,
                                      Paint paint) {
                canvas.drawRoundRect(bounds, cornerRadius, cornerRadius, paint);
            }
        };
    }

}
