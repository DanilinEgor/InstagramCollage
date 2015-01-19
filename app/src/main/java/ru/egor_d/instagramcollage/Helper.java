package ru.egor_d.instagramcollage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by Egor Danilin on 19.01.2015.
 */
public class Helper {
    Context c;

    public Helper(Context context) {
        c = context;
    }

    public void sendBitmapToEmail(Bitmap bmp) {
        File file = saveBitmap(bmp);
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, c.getString(R.string.email_subject));
        emailIntent.putExtra(Intent.EXTRA_TEXT, c.getString(R.string.email_text));
        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        emailIntent.setType("image/png");
        c.startActivity(Intent.createChooser(emailIntent, c.getString(R.string.send_using)));
    }

    private File saveBitmap(Bitmap bmp) {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        OutputStream outStream = null;
        File file = new File(extStorageDirectory, "collage.png");
        if (file.exists()) {
            file.delete();
            file = new File(extStorageDirectory, "collage.png");
        }

        try {
            outStream = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }
}
