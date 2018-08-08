package name.malkov.joomtest.ui.observable;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import io.reactivex.Observable;
import io.reactivex.internal.disposables.CancellableDisposable;

public class PagingRecyclerObservable {

    //threshold shows in what percent of item whe should pass before loading new
    //[0 .. 1]
    public static Observable<Integer> paging(final RecyclerView rv, final float threshold) {
        final Observable<Integer> paging = Observable.create(emitter -> {
            final RecyclerView.LayoutManager lm = rv.getLayoutManager();
            if (!(lm instanceof StaggeredGridLayoutManager)) {
                throw new IllegalStateException("only GridLayoutManager supported by PagingObs");
            }
            final StaggeredGridLayoutManager llm = (StaggeredGridLayoutManager) lm;
            final int[] pos = new int[llm.getSpanCount()];

            final RecyclerView.OnScrollListener sl = new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    RecyclerView.Adapter adapter = recyclerView.getAdapter();
                    if (adapter != null) {
                        llm.findLastVisibleItemPositions(pos);
                        if (adapter.getItemCount() * threshold < pos[0]) {
                            emitter.onNext(adapter.getItemCount());
                        }
                    }
                }
            };
            rv.addOnScrollListener(sl);
            emitter.setDisposable(new CancellableDisposable(() -> rv.removeOnScrollListener(sl)));
        });
        return paging.distinctUntilChanged();
    }
}

