package modules.general.utils;

import android.content.Context;
import android.widget.ImageView;

import com.example.template.R;
import com.squareup.picasso.Picasso;


/**
 * Created by VisionsTech on 9/26/16.
 */
public class PicassoHelper {

    // SERVICES RETURN FULL PATH
    //.load(BackEndWrapper.imgURL.concat(imgPath))

    public static void loadImageFull(Context context, String imgPath, ImageView iv) {
        if (imgPath.equals(""))

            imgPath = "error";

        iv.setBackgroundResource(0);

        Picasso.get()
                .load(imgPath)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(iv);

    }


    public static void loadImage(Context context, String imgPath, ImageView iv) {
        if (imgPath.equals(""))

            imgPath = "error";

        iv.setBackgroundResource(0);

        Picasso.get()
                .load(imgPath)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .resize(150, 150)
                .into(iv);

        //Log.e("imgURL.concat(imgPath)",imgPath);

    }
}
