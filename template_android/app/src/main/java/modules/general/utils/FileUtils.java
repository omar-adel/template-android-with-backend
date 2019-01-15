package modules.general.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import androidx.core.content.FileProvider;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.example.template.BuildConfig;
import com.example.template.R;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

import static modules.general.model.db.Constants.DatabaseName;

/**
 * Created by Net22 on 11/28/2017.
 */

public class FileUtils {

    public static final String TAG="FileUtils";
    public static final String DATABASES_ANDROID_FOLDER_NAME ="databases";

    public static  void useNewDownloadedSqliteFile(Context context , String downloadedSqlitePath ) {
        try {

            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            if (sd.canWrite()) {

                downloadedSqlitePath=context.getString(R.string.sqlite_folder_app_name_english)
                        + "/"+
                        DatabaseName
                        +".db";

                String app_data_path = getAppDatabaseFolderPATH(context)
                        +DatabaseName+".db";
                //Destination

                File downloadedSqliteFile = new File(sd, downloadedSqlitePath);  //SOURCE
                File appDBPathFile = new File(data, app_data_path);  //Destination
                FlowManager.close();
                copyFile(new FileInputStream(downloadedSqliteFile),new FileOutputStream(appDBPathFile));
                reInitDbflow(context);

                Log.e("useNewDownSqliteSuccess", "useNewDownSqliteSuccess");

            }
        } catch (Exception e) {
            Log.e("useNewDownloadedSqliteFileerror", e.toString());
        }
    }

    public static  void useNewSqliteFileFromAnyPath(Context context , String path ) {
        try {

            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            if (sd.canWrite()) {



                String app_data_path = getAppDatabaseFolderPATH(context)
                        +DatabaseName+".db";
                //Destination

                File newSqliteFile = new File(sd, path);  //SOURCE
                File appDBPathFile = new File(data, app_data_path);  //Destination
                FlowManager.close();
                copyFile(new FileInputStream(newSqliteFile),new FileOutputStream(appDBPathFile));
                reInitDbflow(context);

                Log.e("useNewSqliteFileFromAnyPath", "useNewSqliteFileFromAnyPath");

            }
        } catch (Exception e) {
            Log.e("useNewSqliteFileFromAnyPatherror", e.toString());
        }
    }

    public static void copyFromAssetsToDataPath(Context context  )  {
//        InputStream is = context.getAssets().open("path/to/shipped/db/file");
        InputStream is = null;
        try {
            is = context.getAssets().open(DatabaseName+".db");
            File data = Environment.getDataDirectory();
            String app_data_path = getAppDatabaseFolderPATH(context)
                    +DatabaseName+".db";

            //Destination
            OutputStream os = null;
            try {
                File appDBPathFile = new File(data, app_data_path);  //Destination
                os = new FileOutputStream(appDBPathFile);
                try {
                    FlowManager.close();
                    copyFile(is,os);
                    reInitDbflow(context);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }




    public static void copyFromAssetsToAnyPath(Context context , File dbFileFullPath) {
        InputStream is = null;
        try {
            is = context.getAssets().open(DatabaseName+".db");
            OutputStream os = null;
            try {
                os = new FileOutputStream(dbFileFullPath);
                try {
                    copyFile(is,os);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void copyFile(InputStream is, OutputStream os) throws IOException {
        byte[] buffer = new byte[1024];
        while (is.read(buffer) > 0) {
            os.write(buffer);
        }

        os.flush();
        os.close();
        is.close();
    }

    public static void copyFile(FileInputStream fromFile, FileOutputStream toFile) throws IOException {
        FileChannel fromChannel = null;
        FileChannel toChannel = null;
        try {
            fromChannel = fromFile.getChannel();
            toChannel = toFile.getChannel();
            fromChannel.transferTo(0, fromChannel.size(), toChannel);
            //or
            // toChannel.transferFrom(fromChannel, 0, fromChannel.size());
        } finally {
            try {
                if (fromChannel != null) {
                    fromChannel.close();
                }
            } finally {
                if (toChannel != null) {
                    toChannel.close();
                }
            }
        }
    }


    public static void reInitDbflow(Context context) {


        FlowManager.init(FlowConfig.builder(context.getApplicationContext())
                .openDatabasesOnInit(true)
                .build());
    }

    public static Uri getFileUri(Context context ,File file)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            return FileProvider.getUriForFile(context,
                    BuildConfig.APPLICATION_ID + ".provider",file);

        }
        else
        {
            return   Uri.fromFile(file); // create

        }
    }

    public static String getMimeType(Context context ,Uri uri) {
        String mimeType = null;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = context.getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return mimeType;
    }

    public static String getAppDatabaseFolderPATH(Context context) {
        String app_data_path="";
        if(Build.VERSION.SDK_INT >= 17) {
            app_data_path = context.getApplicationContext()
                    .getApplicationInfo().dataDir  + "/" +DATABASES_ANDROID_FOLDER_NAME+"/";
        } else {
            app_data_path = "/data/data/" + context.getApplicationContext()
                    .getPackageName()  + "/" +DATABASES_ANDROID_FOLDER_NAME+"/";
        }
        return app_data_path ;
    }

}


