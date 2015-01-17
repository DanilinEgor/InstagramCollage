package ru.egor_d.instagramcollage.activity;

import android.app.Activity;
import android.os.Bundle;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import ru.egor_d.instagramcollage.model.InstagramPhoto;

/**
 * Created by Egor Danilin on 17.01.2015.
 */
public class CollageActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<String> ids = getIntent().getStringArrayListExtra("ids");
        List<InstagramPhoto> allPhotos = new Select().from(InstagramPhoto.class).execute();
        List<InstagramPhoto> photos = new ArrayList<>(ids.size());
        for (InstagramPhoto photo : allPhotos) {
            if (photos.contains(photo.getId().toString())) {
                photos.add(photo);
            }
        }
    }
}
