package name.peterbukhal.android.fragmentanimation;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Adapter;
import android.widget.LinearLayout;

/**
 * A container that arranges its children in list like manner,
 * also supports {@link Adapter} interface ({@link #setAdapter(Adapter)})
 */
public class StaticListView extends LinearLayout {

    private Adapter adapter;

    public StaticListView(Context context) {
        super(context);

        init();
    }

    public StaticListView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initAttrs(attrs);
        init();
    }

    public StaticListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttrs(attrs);
        init();
    }

    private void init() {
        setOrientation(LinearLayout.VERTICAL);
    }

    private void initAttrs(AttributeSet attrs) {

    }

    public void setAdapter(Adapter adapter) {
        if (adapter == null) return;

        this.adapter = adapter;
        removeAllViewsInLayout();

        for (int position = 0; position < adapter.getCount(); position++) {
            addView(adapter.getView(position, null, null));
        }

        invalidate();
        requestLayout();
    }

    public Adapter getAdapter() {
        return adapter;
    }

}
