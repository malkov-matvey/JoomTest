package name.malkov.joomtest.network;


import io.reactivex.Observable;
import name.malkov.joomtest.network.model.Result;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GiphyRestAdapter {

    @GET("/v1/gifs/trending")
    Observable<Result> trending(@Query("api_key") final String apiKey, @Query("limit") int count, @Query("offset") int offset);

}
