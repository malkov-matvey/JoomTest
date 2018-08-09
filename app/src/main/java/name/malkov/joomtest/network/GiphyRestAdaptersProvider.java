package name.malkov.joomtest.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GiphyRestAdaptersProvider {

    private static final Object lock = new Object();
    private static GiphyRestAdapter _INSTANCE;

    public static GiphyRestAdapter getInstance() {
        if (_INSTANCE == null) {
            synchronized (lock) {
                if (_INSTANCE == null) {
                    _INSTANCE = createInstance();
                }
            }
        }
        return _INSTANCE;
    }

    private static GiphyRestAdapter createInstance() {
        final Retrofit r = new Retrofit.Builder().baseUrl(NetworkContants.giphyEndpoint)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return r.create(GiphyRestAdapter.class);
    }
}
