package ru.egor_d.instagramcollage.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.egor_d.instagramcollage.Helper;
import ru.egor_d.instagramcollage.R;
import ru.egor_d.instagramcollage.model.InstagramPhoto;
import ru.egor_d.instagramcollage.view.CollageView;

/**
 * Created by Egor Danilin on 17.01.2015.
 */
public class CollageActivity extends Activity {
    Picasso mPicasso;
    TextView mUnableTextView;
    CollageView mCollageView;
    Button mSendButton;
    ProgressBar mProgressBar;
    ExecutorService mExecutorService;
    final ArrayList<Bitmap> mBitmaps = new ArrayList<>();
    List<String> mIds;

    Handler mHandler = new Handler() {
        int bitmapsLoaded = 0;

        @Override
        public void handleMessage(Message msg) {
            synchronized (mBitmaps) {
                if (msg.obj == null) {
                    mProgressBar.setVisibility(View.GONE);
                    mUnableTextView.setVisibility(View.VISIBLE);
                    mExecutorService.shutdown();
                } else {
                    mBitmaps.set(msg.arg1, (Bitmap) msg.obj);
                    ++bitmapsLoaded;
                }
            }
            if (bitmapsLoaded == mIds.size()) {
                mProgressBar.setVisibility(View.GONE);
                mCollageView.setVisibility(View.VISIBLE);
                mSendButton.setEnabled(true);
                photosLoaded(mBitmaps);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collage_activity);

        initViews();

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap collage = mCollageView.getCollage();
                new Helper(CollageActivity.this).sendBitmapToEmail(collage);
            }
        });

        mPicasso = Picasso.with(this);
        mIds = getIntent().getStringArrayListExtra("ids");
        mBitmaps.addAll(Collections.<Bitmap>nCopies(mIds.size(), null));
        mExecutorService = Executors.newFixedThreadPool(mIds.size());

        List<InstagramPhoto> allPhotos = new Select().from(InstagramPhoto.class).execute();

        final int[] index = {0};
        for (final InstagramPhoto photo : allPhotos) {
            if (mIds.contains(photo.getId().toString())) {
                mExecutorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap res;
                        try {
                            res = mPicasso.load(photo.low_resolution).get();
                        } catch (IOException e) {
                            e.printStackTrace();
                            res = null;
                        }
                        Message msg = new Message();
                        msg.obj = res;
                        msg.arg1 = index[0]++;
                        mHandler.sendMessage(msg);
                    }
                });
            }
        }
    }

    private void initViews() {
        mCollageView = (CollageView) findViewById(R.id.collageView);
        mSendButton = (Button) findViewById(R.id.sendButton);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mUnableTextView = (TextView) findViewById(R.id.unable_textView);
    }

    void photosLoaded(List<Bitmap> bitmaps) {
        mCollageView.setBitmaps(bitmaps);
    }
}
