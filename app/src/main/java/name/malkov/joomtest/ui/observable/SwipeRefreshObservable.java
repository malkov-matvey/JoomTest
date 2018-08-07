package name.malkov.joomtest.ui.observable;

import android.support.v4.widget.SwipeRefreshLayout;

import io.reactivex.Observable;
import io.reactivex.internal.disposables.CancellableDisposable;


public class SwipeRefreshObservable {

    public static Observable<Boolean> swipe(final SwipeRefreshLayout swipeRefreshLayout, boolean initial) {
        return Observable.create(emitter -> {
            final SwipeRefreshLayout.OnRefreshListener listener = () -> emitter.onNext(true);
            swipeRefreshLayout.setOnRefreshListener(listener);
            emitter.setDisposable(
                    new CancellableDisposable(() -> swipeRefreshLayout.setOnRefreshListener(null))
            );
            if (initial) emitter.onNext(true);
        });
    }
}
