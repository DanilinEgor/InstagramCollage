package ru.egor_d.instagramcollage.api;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import ru.egor_d.instagramcollage.model.InstagramMedia;
import ru.egor_d.instagramcollage.model.InstagramResponse;
import ru.egor_d.instagramcollage.model.InstagramUser;

/**
 * Created by Egor Danilin on 15.01.2015.
 */
public interface IInstagramService {
    @GET("/search")
    void getUserId(@Query("q") String username, @Query("client_id") String client_id, Callback<InstagramResponse<List<InstagramUser>>> cb);

    @GET("/{user_id}/media/recent/")
    void getPhotosList(@Path("user_id") String user_id, @Query("max_id") String max_id, @Query("client_id") String client_id, Callback<InstagramResponse<List<InstagramMedia>>> cb);
}
