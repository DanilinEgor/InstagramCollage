package ru.egor_d.instagramcollage.api;

import android.os.Handler;
import android.os.Message;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import ru.egor_d.instagramcollage.CollageActivity;
import ru.egor_d.instagramcollage.model.InstagramMedia;
import ru.egor_d.instagramcollage.model.InstagramPhoto;
import ru.egor_d.instagramcollage.model.InstagramResponse;
import ru.egor_d.instagramcollage.model.InstagramUser;

/**
 * Created by Egor Danilin on 15.01.2015.
 */
public class API {
    public final static String client_id = "8bec0ea3b6304a3c94595420411197b3";
    RestAdapter mRestAdapter;
    IInstagramService mService;
    Handler mHandler;

    public API(Handler handler) {
        mRestAdapter = new RestAdapter.Builder().setEndpoint("https://api.instagram.com/v1/users").build();
        mService = mRestAdapter.create(IInstagramService.class);
        mHandler = handler;
    }

    public void getUserID(String username) {
        mService.getUserId(username, client_id, new Callback<InstagramResponse<List<InstagramUser>>>() {
            @Override
            public void success(InstagramResponse<List<InstagramUser>> instagramUsers, Response response) {
                Message msg = new Message();
                if (instagramUsers.data.size() > 0)
                    msg.obj = instagramUsers.data.get(0).id;
                else
                    msg.obj = "";
                if (mHandler != null)
                    mHandler.sendMessage(msg);
            }

            @Override
            public void failure(RetrofitError error) {
                Message msg = new Message();
                msg.obj = "";
                if (mHandler != null)
                    mHandler.sendMessage(msg);
            }
        });
    }

    public void getUserMedia(final String userID, String max_id) {
        mService.getPhotosList(userID, max_id, client_id, new Callback<InstagramResponse<List<InstagramMedia>>>() {
            @Override
            public void success(InstagramResponse<List<InstagramMedia>> listInstagramResponse, Response response) {
                List<InstagramMedia> mediaList = listInstagramResponse.data;
                for (InstagramMedia media : mediaList) {
                    InstagramPhoto photo = new InstagramPhoto();
                    photo.photo_id = media.id;
                    photo.likes = media.likes.count;
                    photo.url = media.images.standard_resolution.url;
//                    photo.save();
                }
                if (listInstagramResponse.pagination.next_url != null) {
                    getUserMedia(userID, listInstagramResponse.pagination.next_max_id);
                } else {
                    Message msg = new Message();
                    msg.arg1 = CollageActivity.OK;
                    if (mHandler != null)
                        mHandler.sendMessage(msg);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Message msg = new Message();
                msg.arg1 = CollageActivity.FAIL;
                if (mHandler != null)
                    mHandler.sendMessage(msg);
            }
        });
    }
}
