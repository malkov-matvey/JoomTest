package name.malkov.joomtest.ui.observable;

import android.view.View;

import io.reactivex.Observable;
import io.reactivex.internal.disposables.CancellableDisposable;


public class ClickObservable {

    public static Observable<Boolean> clicks(final View view, boolean initial) {
        return Observable.create(emitter -> {
            final View.OnClickListener listener = v -> emitter.onNext(true);
            view.setOnClickListener(listener);
            if (initial) emitter.onNext(true);
            emitter.setDisposable(new CancellableDisposable(() -> view.setOnClickListener(null)));
        });
    }
}
