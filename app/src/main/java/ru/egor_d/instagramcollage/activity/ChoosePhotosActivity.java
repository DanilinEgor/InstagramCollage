package ru.egor_d.instagramcollage.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import ru.egor_d.instagramcollage.R;
import ru.egor_d.instagramcollage.api.API;
import ru.egor_d.instagramcollage.model.InstagramPhoto;

/**
 * Created by Egor Danilin on 15.01.2015.
 */
public class ChoosePhotosActivity extends Activity {
    public static final int OK = 1;
    public static final int FAIL = 0;
    private final static String TAG = "InstagramCollage";
    private RecyclerView mRecyclerView;
    private Button mMakeCollageButton;

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.arg1) {
                case OK:
                    loadPhotos();
                    break;
                case FAIL:
                    Toast.makeText(ChoosePhotosActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private InstagramPhotosAdapter adapter;

    private void loadPhotos() {
        Log.v(TAG, "CollageActivity success");
        List<InstagramPhoto> mPhotos = new Select().from(InstagramPhoto.class).orderBy("likes DESC").execute();
        adapter = new InstagramPhotosAdapter(ChoosePhotosActivity.this, mPhotos);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setVisibility(View.VISIBLE);
        findViewById(R.id.progressBar).setVisibility(View.GONE);
        Log.v(TAG, mPhotos.size() + "");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Delete().from(InstagramPhoto.class).execute();

        setContentView(R.layout.choose_activity);

        initViews();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mMakeCollageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<InstagramPhoto> photos = adapter.getCheckedPhotos();
                ArrayList<String> ids = new ArrayList<>(photos.size());
                for (InstagramPhoto photo : photos) {
                    ids.add(photo.getId().toString());
                }
                Intent intent = new Intent(ChoosePhotosActivity.this, CollageActivity.class);
                intent.putStringArrayListExtra("ids", ids);
                startActivity(intent);
            }
        });

        new API(mHandler).getUserMedia(getIntent().getStringExtra(MainActivity.USER_ID), "");
    }

    private void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mMakeCollageButton = (Button) findViewById(R.id.make_button);
    }
}
