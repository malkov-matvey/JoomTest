package name.malkov.joomtest.ui.imagelist;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import name.malkov.joomtest.R;
import name.malkov.joomtest.ui.FrescoDrawingProtocol;
import name.malkov.joomtest.ui.observable.PagingRecyclerObservable;
import name.malkov.joomtest.ui.observable.SwipeRefreshObservable;
import name.malkov.joomtest.ui.preview.PreviewFragment;
import name.malkov.joomtest.viewmodel.GiphyTrendingViewModel;
import name.malkov.joomtest.viewmodel.model.ImageItem;

public class GiphyListFragment extends Fragment {

    private final CompositeDisposable disposable = new CompositeDisposable();

    private final ClickConsumer<ImageItem> click = this::openPreview;

    public static Fragment newInstance() {
        return new GiphyListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_giphy_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final SwipeRefreshLayout refresh = view.findViewById(R.id.refreshRoot);
        final RecyclerView list = view.findViewById(R.id.list);

        final int gridSize = getResources().getInteger(R.integer.grid_size);
        int smallOffsetPx = getResources().getDimensionPixelOffset(R.dimen.offset_small);
        list.addItemDecoration(new EndlessGridSpacingDecorator(gridSize, smallOffsetPx));

        final ImageListAdapter adapter = new ImageListAdapter(new FrescoDrawingProtocol(), click);
        final StaggeredGridLayoutManager lm = new StaggeredGridLayoutManager(gridSize, StaggeredGridLayoutManager.VERTICAL);
        final Observable<Integer> paging = PagingRecyclerObservable.paging(list, 0.75f);
        final Observable<Boolean> refreshSignal = SwipeRefreshObservable.swipe(refresh, true);

        lm.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        list.setLayoutManager(lm);
        list.setAdapter(adapter);

        final GiphyTrendingViewModel vm = ViewModelProviders
                .of(this)
                .get(GiphyTrendingViewModel.class);
        disposable.add(vm.bind(paging, refreshSignal)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(items -> refresh.setRefreshing(false))
                .subscribe(adapter::addItems));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    private void openPreview(ImageItem item) {
        final FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            final String tag = PreviewFragment.class.getSimpleName();
            final FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.add(R.id.fragmentFrame, PreviewFragment.newInstanceItem(item), tag);
            ft.addToBackStack(tag);
            ft.commit();
        }
    }
}
