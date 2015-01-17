package ru.egor_d.instagramcollage.activity;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.image.SmartImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.egor_d.instagramcollage.R;
import ru.egor_d.instagramcollage.model.InstagramPhoto;

/**
 * Created by Egor Danilin on 16.01.2015.
 */
public class InstagramPhotosAdapter extends RecyclerView.Adapter<InstagramPhotosAdapter.ViewHolder> {
    private List<InstagramPhoto> photos;
    private List<Boolean> checks;
    private List<InstagramPhoto> checkedPhotos = new ArrayList<>();
    private Context context;
    private Picasso picasso;

    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> photos) {
        checks = new ArrayList<>(Collections.nCopies(photos.size(), false));
        picasso = Picasso.with(context);
        this.context = context;
        this.photos = photos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        final InstagramPhoto photo = photos.get(i);
        viewHolder.likes.setText("♥ " + photo.likes);
        viewHolder.checkBox.setChecked(checks.get(i));
        picasso.load(photo.thumbnail).into(viewHolder.thumbnail);
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.checkBox.isChecked()) {
                    checks.set(i, false);
                    checkedPhotos.remove(photo);
                    viewHolder.checkBox.setChecked(false);
                } else {
                    if (checkedPhotos.size() < 9) {
                        checks.set(i, true);
                        viewHolder.checkBox.setChecked(true);
                        checkedPhotos.add(photo);
                    } else {
                        Toast.makeText(context, R.string.choose_less_than_9, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public List<InstagramPhoto> getCheckedPhotos() {
        return checkedPhotos;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView likes;
        private SmartImageView thumbnail;
        private CheckBox checkBox;
        private CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            likes = (TextView) itemView.findViewById(R.id.likes_textView);
            thumbnail = (SmartImageView) itemView.findViewById(R.id.thumbnail_smartImageView);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
        }
    }
}
