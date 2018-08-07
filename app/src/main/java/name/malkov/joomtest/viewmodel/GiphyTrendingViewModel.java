package name.malkov.joomtest.viewmodel;

import android.arch.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import name.malkov.joomtest.network.GiphyRestAdapter;
import name.malkov.joomtest.network.GiphyRestAdaptersFactory;
import name.malkov.joomtest.network.model.GiphyItem;
import name.malkov.joomtest.network.model.Result;

public class GiphyTrendingViewModel extends ViewModel {

    private final GiphyRestAdapter giphyAdapter;

    public GiphyTrendingViewModel() {
        giphyAdapter = GiphyRestAdaptersFactory.getInstance();
    }

    public Observable<List<GiphyItem>> bind(Observable<Integer> paging, Observable<Boolean> refresh) {
        return paging.startWith(0)
                .flatMap(offset -> giphyAdapter.trending("303pB4pvFt2rurFmoeR2916L676zmtXg", 30, offset))
                .map(Result::getList)
                .subscribeOn(Schedulers.io());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
