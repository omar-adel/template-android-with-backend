package modules.general.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.template.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by shico on 8/4/2016.
 */


public class GeneralUtil {


    public Context context;


    public GeneralUtil(Context context) {
        this.context = context;

    }

//    @SuppressWarnings("unchecked")
//    public static <T extends X, X> ArrayList<T> convertToClazz(ArrayList<X> from, Class<X> inClazz, Class<T> outClazz) {
//        ArrayList<T> to = new ArrayList<T>();
//        for (X data : from) {
//            to.add((T) data);
//        }
//        return to;
//    }

    public static String convertArrToStr(ArrayList arrayList) {
        String arrayListStr = "";
        for (int i = 0; i < arrayList.size(); i++) {
            arrayListStr = arrayListStr + arrayList.get(i).toString();
        }
        return arrayListStr;

    }

    public static String convertArrToStr(List arrayList) {
        String arrayListStr = "";
        for (int i = 0; i < arrayList.size(); i++) {
            arrayListStr = arrayListStr + arrayList.get(i).toString();
        }
        return arrayListStr;

    }

    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static String convertDateLongToText(String timestampdate) {


        String dateText = "";
        try {
            SimpleDateFormat dateFormatReturn = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);

            Calendar cUtc = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            //  Calendar cLocale = Calendar.getInstance(GlobalTimeZoneClass.timeZoneDisplay);
            cUtc.setTimeInMillis(Long.valueOf(timestampdate) * 1000);
            dateText = dateFormatReturn.format(cUtc.getTime());

        } catch (Exception e) {

            Log.e("Exception ConvertDate: ", e.toString());
        }

        return dateText;
    }

//    public static void showFullDisplayText(Context context ,
//                                                  String text)
//    {
//        final Dialog dialog = new Dialog(context, R.style.CustomDialog);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
//        dialog.setContentView(R.layout.dialog_zoom_text);
//
//        Window window = dialog.getWindow();
//        if (window != null) {
//            WindowManager.LayoutParams wlp = window.getAttributes();
//            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
//            wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
//            window.setAttributes(wlp);
//        }
//
//        TextView txtvFull = (TextView) dialog.findViewById(R.id.txtvFull);
//        ImageView imgClose = (ImageView) dialog.findViewById(R.id.imgClose);
//
//        imgClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.cancel();
//            }
//        });
//
//        txtvFull.setText(text);
//
//        dialog.setCancelable(true);
//        dialog.show();
//
//
//    }


//    public static void showFullDisplayImageDialog(Context context ,
//                                                  String path ,  Bitmap bitmap)
//    {
//        final Dialog dialog = new Dialog(context, R.style.CustomDialog);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        dialog.setContentView(R.layout.dialog_zoom_image);
//
//        Window window = dialog.getWindow();
//        if (window != null) {
//            WindowManager.LayoutParams wlp = window.getAttributes();
//            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
//            wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
//            window.setAttributes(wlp);
//        }
//
//        SquareImageView imgvImage = (SquareImageView) dialog.findViewById(R.id.imgvImage);
//        ImageView imgClose = (ImageView) dialog.findViewById(R.id.imgClose);
//
//        imgClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.cancel();
//            }
//        });
//
//        if(!path.isEmpty())
//        {
//            PicassoHelper.loadImageFull(context,path,imgvImage);
//
//        }
//        if(bitmap!=null)
//        {
//            imgvImage.setImageBitmap(bitmap);
//
//        }
//
//        dialog.setCancelable(true);
//        dialog.show();
//
//
//    }


    public static String convertMapToStringAppended(Map<String, String> map) {
        String str = "?";
        int i = 0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (i == 0) {
                str = str + entry.getKey() + "=" + entry.getValue();
            } else {
                str = str + "&" + entry.getKey() + "=" + entry.getValue();
            }
            i++;
        }
        return str;
    }


//    public  void replaceFragment (Fragment fragment ){
//        String fragmentTag =  fragment.getClass().getName();
//
//        FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
//        FragmentTransaction ft = manager.beginTransaction();
//
//        ft.replace(R.id.mainframelayout, fragment, fragmentTag);
//        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        ft.commit();
//
//    }


//
//    public Fragment switchCustomerFragment (String  source ){
//        Fragment fragment = null ;
//
//        if(source.equals( Profile.class.getName()))
//
//        {
//            fragment = new Profile();
//        }
//        else
//        if(source.equals( Home.class.getName()))
//
//        {
//            fragment = new Home();
//        }
//
//
//        return fragment ;
//    }
//
//    public  String getBottomFragmentTitle (String  source ){
//
//        String fragmentToolbarTitle = "" ;
//
//        if(source.equals( Profile.class.getName()))
//
//        {
//            fragmentToolbarTitle = context.getString(R.string.profilefragmenttitle);
//
//        }
//        else    if(source.equals( Home.class.getName()))
//
//        {
//            fragmentToolbarTitle = context.getString(R.string.homefragmenttitle);
//        }
//
//        return fragmentToolbarTitle ;
//    }


    public static void setTaskBarColored(Activity context, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = context.getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //status bar height
            int statusBarHeight = getStatusBarHeight(context);

            View view = new View(context);
            view.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            view.getLayoutParams().height = statusBarHeight;
            ((ViewGroup) w.getDecorView()).addView(view);
            view.setBackgroundColor(context.getResources().getColor(color));
        }
    }

    public static int getStatusBarHeight(Activity context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    public File saveBitmapToFile(File file) {
        try {

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE = 75;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();


            // File fileCompressed=new File(file.getParentFile()+"/"+file.getName());

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            return file;
        } catch (Exception e) {

            Log.e("Exceptionsswe", e.toString());
            return null;
        }
    }

    public static boolean checkGpsState(Context  context) {
        boolean availableGps = true;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // test for connection
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                availableGps = false;

                Toast.makeText(context, context.getString(R.string.gpsIsDisabled), Toast.LENGTH_LONG).show();
            }
        } else {
            availableGps = false;

            Toast.makeText(context, context.getString(R.string.noInternetConnection), Toast.LENGTH_LONG).show();
        }


        return availableGps;
    }


}
