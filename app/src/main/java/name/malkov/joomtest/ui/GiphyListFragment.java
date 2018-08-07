package name.malkov.joomtest.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import name.malkov.joomtest.R;
import name.malkov.joomtest.viewmodel.GiphyTrendingViewModel;

public class GiphyListFragment extends Fragment {

    private final CompositeDisposable disposable = new CompositeDisposable();

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
        final View refresh = view.findViewById(R.id.refreshRoot);
        final RecyclerView list = view.findViewById(R.id.list);
        final GiphyTrendingViewModel vm = ViewModelProviders.of(this).get(GiphyTrendingViewModel.class);
        final ImageListAdapter adapter = new ImageListAdapter(2, view.getContext());
        StaggeredGridLayoutManager lm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        lm.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        list.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.onDraw(c, parent, state);
            }
        });
        list.setLayoutManager(lm);
        list.setAdapter(adapter);

        disposable.add(vm.bind().observeOn(AndroidSchedulers.mainThread()).subscribe(adapter::addItems));
    }
}
