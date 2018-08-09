package name.malkov.joomtest.ui.imagelist;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import name.malkov.joomtest.R;
import name.malkov.joomtest.ui.FrescoDrawingProtocol;
import name.malkov.joomtest.ui.observable.ClickObservable;
import name.malkov.joomtest.ui.observable.PagingRecyclerObservable;
import name.malkov.joomtest.ui.observable.SwipeRefreshObservable;
import name.malkov.joomtest.ui.preview.PreviewActivity;
import name.malkov.joomtest.viewmodel.ImagesViewModel;
import name.malkov.joomtest.viewmodel.model.ImageItem;

public class ImageListActivity extends AppCompatActivity {

    private final CompositeDisposable disposable = new CompositeDisposable();

    private final ClickConsumer<ImageItem> click = this::openPreview;
    private View retry;
    private RecyclerView list;
    private SwipeRefreshLayout refresh;
    private View loader;
    private ImageListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_image_list);

        refresh = findViewById(R.id.refreshRoot);
        list = findViewById(R.id.list);
        retry = findViewById(R.id.retry);
        loader = findViewById(R.id.loader);
        adapter = new ImageListAdapter(new FrescoDrawingProtocol(), click);

        final int gridSize = getResources().getInteger(R.integer.grid_size);
        int smallOffsetPx = getResources().getDimensionPixelOffset(R.dimen.offset_small);
        list.addItemDecoration(new EndlessGridSpacingDecorator(gridSize, smallOffsetPx));
        final StaggeredGridLayoutManager lm = new StaggeredGridLayoutManager(
                gridSize,
                StaggeredGridLayoutManager.VERTICAL
        );
        lm.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        list.setLayoutManager(lm);
        list.setAdapter(adapter);

        startFlow();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    private void startFlow() {
        final Observable<Integer> paging = PagingRecyclerObservable.paging(list, 0.75f);
        final Observable<Boolean> refreshSignal = SwipeRefreshObservable.swipe(refresh, true);
        Observable<Boolean> retrySignal = ClickObservable.clicks(retry, false);
        Observable<Boolean> startAgainSignal =
                Observable.merge(refreshSignal, retrySignal).doOnNext(b -> stateLoading());

        final ImagesViewModel vm = ViewModelProviders
                .of(this)
                .get(ImagesViewModel.class);

        disposable.add(vm.bind(paging, startAgainSignal)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(items -> refresh.setRefreshing(false))
                .subscribe(items -> {
                    if (items.isEmpty()) {
                        stateRetry();
                    } else {
                        stateSuccess();
                        adapter.addItems(items);
                    }
                }));
    }

    private void stateLoading() {
        list.setVisibility(View.GONE);
        retry.setVisibility(View.GONE);
        loader.setVisibility(View.VISIBLE);
    }

    private void stateRetry() {
        list.setVisibility(View.GONE);
        retry.setVisibility(View.VISIBLE);
        loader.setVisibility(View.GONE);
    }

    private void stateSuccess() {
        list.setVisibility(View.VISIBLE);
        retry.setVisibility(View.GONE);
        loader.setVisibility(View.GONE);
    }

    private void openPreview(final ImageItem item) {
        final Intent intent = PreviewActivity.prepareOpenIntent(this, item);
        startActivity(intent);
    }

}
