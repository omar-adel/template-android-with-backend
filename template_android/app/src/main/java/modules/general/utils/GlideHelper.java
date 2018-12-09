package modules.general.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by omar on 22/05/2017.
 */

public class GlideHelper {

    // SERVICES RETURN FULL PATH
    //.load(BackEndWrapper.imgURL.concat(imgPath))

    public static void loadImageFull(Context context, String imgPath, ImageView iv) {
        if (imgPath.equals(""))

            imgPath = "error";

        iv.setBackgroundResource(0);

        Glide.with(context)
                .load(imgPath)
                //.placeholder(R.mipmap.ic_launcher)
                //.error(R.mipmap.ic_launcher)
                .into(iv);

    }


    public static void loadImage(Context context, String imgPath, ImageView iv) {
        if (imgPath.equals(""))

            imgPath = "error";

        //iv.setBackgroundResource(0);

        Glide.with(context)
                .load(imgPath)
                //.placeholder(R.mipmap.ic_launcher)
                //.error(R.mipmap.ic_launcher)
                .into(iv);

        //  Log.e(  "loadImage: ","cd "+imgPath );


    }

}
