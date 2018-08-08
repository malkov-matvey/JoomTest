package name.malkov.joomtest.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.util.Pair;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import name.malkov.joomtest.Utils;
import name.malkov.joomtest.network.GiphyRestAdapter;
import name.malkov.joomtest.network.GiphyRestAdaptersFactory;
import name.malkov.joomtest.network.model.ResponseConverter;
import name.malkov.joomtest.viewmodel.model.ImageItem;
import retrofit2.HttpException;

public class ImagesViewModel extends ViewModel {

    public static int CODE_OK = 200;
    public static int CODE_NOT_FOUND = 404;
    public static int CODE_BAD_REQUEST = 400;

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

    public Observable<Pair<ImageItem, Integer>> bindById(String id, Observable<Boolean> loadingSignals) {
        return loadingSignals.switchMap(r ->
                giphyAdapter.gifById(id, apiKey)
                        .subscribeOn(Schedulers.io())
                        .map(response -> new Pair<>(ResponseConverter.convertGiphySingleItem(response), CODE_OK))
                        .onErrorReturn(throwable -> {
                            if (throwable instanceof HttpException) {
                                return new Pair<>(null, ((HttpException) throwable).code());
                            } else {
                                return new Pair<>(null, CODE_BAD_REQUEST);
                            }
                        }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}