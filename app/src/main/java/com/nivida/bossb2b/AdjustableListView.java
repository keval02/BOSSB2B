package com.nivida.bossb2b;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Dhruv on 31/08/2017.
 */

public class AdjustableListView extends ListView{

    public AdjustableListView(Context context) {
        super(context);
    }

    public AdjustableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdjustableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
