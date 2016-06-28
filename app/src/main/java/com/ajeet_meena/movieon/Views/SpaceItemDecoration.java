package com.ajeet_meena.movieon.Views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by saurabhjain on 26/11/15.
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private final int mVerticalSpaceHeight;
    private final int mHorizontalSpaceHeight;
    private Context context;
    private Drawable mDivider;

    public SpaceItemDecoration(int mVerticalSpaceHeight, int mHorizontalSpaceHeight, Context context) {
        this.context = context;
        this.mVerticalSpaceHeight = convertDpToPixel(mVerticalSpaceHeight);
        this.mHorizontalSpaceHeight = convertDpToPixel(mHorizontalSpaceHeight);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        int pos = parent.getChildAdapterPosition(view);
        outRect.bottom = mVerticalSpaceHeight;
        outRect.left = mHorizontalSpaceHeight / 2;
        outRect.right = mHorizontalSpaceHeight / 2;
        if (pos == 0) {
            outRect.left = 0;
            outRect.right = 0;
            outRect.bottom = 0;
        }
        if(pos==1||pos==2){
            outRect.top = mVerticalSpaceHeight;

        }

    }


    public int convertDpToPixel(int dp) {
        Resources resources = context.getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
        return (int) px;
    }


}
