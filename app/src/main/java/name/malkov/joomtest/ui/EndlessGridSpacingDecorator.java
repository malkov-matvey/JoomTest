package name.malkov.joomtest.ui;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

public class EndlessGridSpacingDecorator extends RecyclerView.ItemDecoration {

    private int spans;
    private int offset;

    EndlessGridSpacingDecorator(int spans, int offsetBetweenPx) {
        this.spans = spans;
        this.offset = offsetBetweenPx;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView rv, @NonNull RecyclerView.State rvState) {
        final int span = ((StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams())
                .getSpanIndex();
        if (span == 0) {
            outRect.right = offset / 2;
            outRect.left = offset;
        } else if (span == spans - 1) {
            outRect.right = offset;
            outRect.left = offset / 2;
        } else {
            outRect.right = offset / 2;
            outRect.left = offset / 2;
        }
        //since it's endless, we can skip bottom padding
        outRect.top = offset;
        outRect.bottom = 0;
    }
}
