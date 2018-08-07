package name.malkov.joomtest.viewmodel;

import android.arch.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.Observable;
import name.malkov.joomtest.network.GiphyRestAdaptersFactory;
import name.malkov.joomtest.network.model.GiphyItem;
import name.malkov.joomtest.network.model.Result;

public class GiphyTrendingViewModel extends ViewModel {

    public GiphyTrendingViewModel() {

    }

    public Observable<List<GiphyItem>> bind() {
        return GiphyRestAdaptersFactory.getInstance()
                .trending("303pB4pvFt2rurFmoeR2916L676zmtXg", 30, 0)
                .map(Result::getList);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
