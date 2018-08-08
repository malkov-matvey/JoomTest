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

public class ImagesViewModel extends ViewModel {

    private final GiphyRestAdapter giphyAdapter;
    private final String apiKey;

    public ImagesViewModel() {
        giphyAdapter = GiphyRestAdaptersFactory.getInstance();
        apiKey = "303pB4pvFt2rurFmoeR2916L676zmtXg";
    }

    public Observable<List<ImageItem>> bind(Observable<Integer> paging, Observable<Boolean> refresh) {
        return refresh
                .switchMap(v -> paging.startWith(0)
                        .observeOn(Schedulers.io())
                        .flatMap(offset -> giphyAdapter.trending(apiKey, 30, offset))
                        .map(ResponseConverter::convertGiphyList)
                        .scan(Collections.emptyList(), Utils::mergeLists));
    }

    public Observable<ImageItem> bindById(String id) {
        return giphyAdapter.gifById(id, apiKey)
                .subscribeOn(Schedulers.io())
                .map(ResponseConverter::convertGiphySingleItem);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
