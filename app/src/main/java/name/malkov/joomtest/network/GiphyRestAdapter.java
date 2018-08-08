package name.malkov.joomtest.network;


import io.reactivex.Observable;
import name.malkov.joomtest.network.model.ListResult;
import name.malkov.joomtest.network.model.SingleResult;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GiphyRestAdapter {

    @GET("/v1/gifs/trending")
    Observable<ListResult> trending(@Query("api_key") final String apiKey, @Query("limit") int count, @Query("offset") int offset);

    @GET("/v1/gifs/{gifId}")
    Observable<SingleResult> gifById(@Path("gifId") String id, @Query("api_key") final String apiKey);

}
