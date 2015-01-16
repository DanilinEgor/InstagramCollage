package ru.egor_d.instagramcollage;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.activeandroid.query.Select;

import java.util.List;

import ru.egor_d.instagramcollage.api.API;
import ru.egor_d.instagramcollage.model.InstagramPhoto;

/**
 * Created by Egor Danilin on 15.01.2015.
 */
public class CollageActivity extends Activity {
    public static final int OK = 1;
    public static final int FAIL = 0;

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.arg1) {
                case OK:
                    foo();
                case FAIL:
                    Toast.makeText(CollageActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void foo() {
        List<InstagramPhoto> photos = new Select().from(InstagramPhoto.class).orderBy("likes DESC").execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new API(mHandler).getUserMedia(getIntent().getStringExtra(MainActivity.USER_ID), "");
    }
}
