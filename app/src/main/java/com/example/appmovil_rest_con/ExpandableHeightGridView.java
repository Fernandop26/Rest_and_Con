package com.example.appmovil_rest_con;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RelativeLayout;

/**
 * ScrollViewの中のGridViewでも高さを可変にする<br>
 * http://stackoverflow.com/questions/8481844/gridview-height-gets-cut
 */
public class ExpandableHeightGridView extends GridView
{

    boolean expanded = false;

    public ExpandableHeightGridView(Context context)
    {
        super(context);
    }

    public ExpandableHeightGridView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public ExpandableHeightGridView(Context context, AttributeSet attrs,
            int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public boolean isExpanded()
    {
        return expanded;
    }

    public void setMargins(int l, int t, int r, int b) {
        if (getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) getLayoutParams();
            p.setMargins(l, t, r, b);
            requestLayout();
        }
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        // HACK! TAKE THAT ANDROID!
        if (isExpanded())
        {
            // Calculate entire height by providing a very large height hint.
            // View.MEASURED_SIZE_MASK represents the largest height possible.
            int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                    MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec );

            ViewGroup.LayoutParams params = getLayoutParams();
            setMargins(0,0,0,100);

            params.height =getMeasuredHeight();
        }
        else
        {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public void setExpanded(boolean expanded)
    {
        this.expanded = expanded;
    }


    /***
     * This function returns the actual height the layout. The getHeight() function returns the current height which might be zero if
     * the layout's visibility is GONE
     * @param layout
     * @return
     */
    public static int getFullHeight(ViewGroup layout) {
        int specWidth = View.MeasureSpec.makeMeasureSpec(0 /* any */, View.MeasureSpec.UNSPECIFIED);
        int specHeight = View.MeasureSpec.makeMeasureSpec(0 /* any */, View.MeasureSpec.UNSPECIFIED);

        //measure(specWidth,specHeight);
        layout.measure(specWidth,specHeight);
        int totalHeight = 0;//layout.getMeasuredHeight();
        int initialVisibility = layout.getVisibility();
        layout.setVisibility(View.VISIBLE);
        int numberOfChildren = layout.getChildCount();
        for(int i = 0;i<numberOfChildren;i++) {
            View child = layout.getChildAt(i);
            if(child instanceof ViewGroup) {
                totalHeight+=getFullHeight((ViewGroup)child);
            }else {
                int desiredWidth = View.MeasureSpec.makeMeasureSpec(layout.getWidth(),
                        View.MeasureSpec.AT_MOST);
                child.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight+=child.getMeasuredHeight();
            }

        }
        layout.setVisibility(initialVisibility);
        return totalHeight;
    }
}
