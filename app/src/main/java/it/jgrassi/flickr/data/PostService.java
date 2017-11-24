package it.jgrassi.flickr.data;

import it.jgrassi.flickr.model.FeedResponse;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by j.grassi on 24/11/2017.
 */

public interface PostService {
    @POST("photos_public.gne")
    Call<FeedResponse> fetchFeed(@Query("format") String format, @Query("tags") String tags);
}
