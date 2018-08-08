package name.malkov.joomtest.viewmodel;

import android.arch.lifecycle.ViewModel;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import name.malkov.joomtest.Utils;
import name.malkov.joomtest.network.GiphyRestAdapter;
import name.malkov.joomtest.network.GiphyRestAdaptersFactory;
import name.malkov.joomtest.network.model.ResponseConverter;
import name.malkov.joomtest.viewmodel.model.ImageItem;

public class GiphyTrendingViewModel extends ViewModel {

    private final GiphyRestAdapter giphyAdapter;

    public GiphyTrendingViewModel() {
        giphyAdapter = GiphyRestAdaptersFactory.getInstance();
    }

    public Observable<List<ImageItem>> bind(Observable<Integer> paging, Observable<Boolean> refresh) {
        return refresh
                .switchMap(v -> paging.startWith(0)
                        .observeOn(Schedulers.io())
                        .flatMap(offset -> giphyAdapter.trending("303pB4pvFt2rurFmoeR2916L676zmtXg", 30, offset))
                        .map(ResponseConverter::convertGiphyList)
                        .scan(Collections.emptyList(), Utils::mergeLists));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
