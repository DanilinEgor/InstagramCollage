package ru.egor_d.instagramcollage.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.activeandroid.query.Select;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.egor_d.instagramcollage.Helper;
import ru.egor_d.instagramcollage.R;
import ru.egor_d.instagramcollage.model.InstagramPhoto;
import ru.egor_d.instagramcollage.view.CollageView;

/**
 * Created by Egor Danilin on 17.01.2015.
 */
public class CollageActivity extends Activity {
    Picasso picasso;
    CollageView mCollageView;
    Button mSendButton;

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

        picasso = Picasso.with(this);

        List<String> ids = getIntent().getStringArrayListExtra("ids");
        List<InstagramPhoto> allPhotos = new Select().from(InstagramPhoto.class).execute();
        List<String> photos = new ArrayList<>(ids.size());
        for (InstagramPhoto photo : allPhotos) {
            if (ids.contains(photo.getId().toString())) {
                photos.add(photo.low_resolution);
            }
        }

        new LoadImagesAsyncTask().execute(photos.toArray(new String[photos.size()]));
    }

    private void initViews() {
        mCollageView = (CollageView) findViewById(R.id.collageView);
        mSendButton = (Button) findViewById(R.id.sendButton);
    }

    void photosLoaded(List<Bitmap> bitmaps) {
        mCollageView.setBitmaps(bitmaps);
    }

    private class LoadImagesAsyncTask extends AsyncTask<String, Void, List<Bitmap>> {
        @Override
        protected List<Bitmap> doInBackground(String... params) {
            List<Bitmap> res = new ArrayList<>();
            for (String url : params) {
                try {
                    res.add(picasso.load(url).get());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return res;
        }

        @Override
        protected void onPostExecute(List<Bitmap> bitmaps) {
            photosLoaded(bitmaps);
        }
    }
}
