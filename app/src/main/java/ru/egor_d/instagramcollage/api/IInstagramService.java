package ru.egor_d.instagramcollage.api;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import ru.egor_d.instagramcollage.model.InstagramResponse;
import ru.egor_d.instagramcollage.model.InstagramUser;

/**
 * Created by Egor Danilin on 15.01.2015.
 */
public interface IInstagramService {
    @GET("/search?q={username}&client_id=" + API.client_id)
    void getUserId(@Path("username") String username, Callback<InstagramResponse<InstagramUser>> cb);
}
