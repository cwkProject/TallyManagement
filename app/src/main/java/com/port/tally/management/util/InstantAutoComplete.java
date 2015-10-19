package com.port.tally.management.util;

/**
 * Created by song on 2015/10/18.
 */
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.AutoCompleteTextView;

public class InstantAutoComplete extends AutoCompleteTextView {

    public InstantAutoComplete(Context context) {
        super(context);
    }

    public InstantAutoComplete(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
    }

    public InstantAutoComplete(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);
    }

    @Override
    public boolean enoughToFilter() {
        return true;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction,
                                  Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused) {
            Log.i("getText()", "" + getText());
            if (!getText().equals(""))
            performFiltering(getText(), 0);
        }
    }

}
