package ru.egor_d.instagramcollage.api;

import android.os.Handler;
import android.os.Message;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
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
        mService.getUserId(username, new Callback<InstagramResponse<InstagramUser>>() {
            @Override
            public void success(InstagramResponse<InstagramUser> instagramUserInstagramResponse, Response response) {
                Message msg = new Message();
                msg.obj = instagramUserInstagramResponse.data.id;
                mHandler.sendMessage(msg);
            }

            @Override
            public void failure(RetrofitError error) {
                Message msg = new Message();
                msg.obj = "";
                mHandler.sendMessage(msg);
            }
        });
    }
}
