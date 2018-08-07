package name.malkov.joomtest.ui;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class GridSpacingDecorator extends RecyclerView.ItemDecoration {

    private int spans;
    private int offset;

    public GridSpacingDecorator(int spans, int offsetBetweenPx) {
        this.spans = spans;
        this.offset = offsetBetweenPx;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView rv, @NonNull RecyclerView.State rvState) {
        final int pos = ((RecyclerView.LayoutParams)view.getLayoutParams()).getViewAdapterPosition();
        if (pos >= spans) {
            outRect.top = offset;
        } else {
            outRect.top = 0;
        }
        if (pos % spans == 0) {
            outRect.right = offset / 2;
            outRect.left = 0;
        } else {
            outRect.right = 0;
            outRect.left = offset / 2;
        }
        outRect.bottom = 0;
    }
}
