package ru.egor_d.instagramcollage.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Egor Danilin on 16.01.2015.
 */
@Table(name = "Photos")
public class InstagramPhoto extends Model {
    @Column(name = "photo_id")
    public String photo_id;

    @Column(name = "url")
    public String url;

    @Column(name = "likes")
    public int likes;
}
