package modules.general.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.template.BuildConfig;
import com.example.template.R;
import com.fastaccess.permission.base.PermissionFragmentHelper;
import com.fastaccess.permission.base.PermissionHelper;
import com.fastaccess.permission.base.callback.OnPermissionCallback;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;

/**
 * Created by VisionsTech on 9/29/16.
 */
public class UploadMediaHeplerPermissions implements OnPermissionCallback {

    private AppCompatActivity mActivity;
    private Fragment mFrgment;


    AlertDialog.Builder builder;


    public static int REQUEST_CAMERA_CAPTURE_CODE = 10;
    public static int REQUEST_CAMERA_CHOOSE_CODE = 15;
    public int REQUEST_VIDEO_CAPTURE_CODE = 17;
    public int REQUEST_VIDEO_CHOOSE_CODE = 19;

    private PermissionHelper permissionHelper;
    private PermissionFragmentHelper permissionFragmentHelper;

    private final static String SINGLE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private final static String[] MULTI_PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
    };

    private boolean isSingle;
    private AlertDialog alertBuilder;
    private String[] neededPermission;


    private MediaPathHelper mediaPathHelper;
    private Uri picVidUri;

    public static String CAMERA_TXT = "camera";
    public static String PHOTO_TXT = "photo";
    public static String VIDEO_TXT = "video";

    private String action_case = "";


    public UploadMediaHeplerPermissions(AppCompatActivity activity, Fragment fr) {
        mActivity = activity;
        mFrgment = fr;
        mediaPathHelper = new MediaPathHelper(activity);

        if (mFrgment != null) {
            permissionFragmentHelper = PermissionFragmentHelper.getInstance(mFrgment);
        } else if (mActivity != null) {
            permissionHelper = PermissionHelper.getInstance(mActivity);
        }

    }


    public void Select_img() {
        final CharSequence[] options = {mActivity.getString(R.string.camera), mActivity.getString(R.string.gallery),
                mActivity.getString(R.string.cancel)};

        builder = new AlertDialog.Builder(mActivity);


        builder.setTitle(mActivity.getString(R.string.pick));
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals(mActivity.getString(R.string.camera))) {
                    checkCameraCapturePermission();
                } else if (options[item].equals(mActivity.getString(R.string.gallery))) {
                    checkcCameraChoosePermission();
                } else if (options[item].equals(mActivity.getString(R.string.cancel))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void checkCameraCapturePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (mFrgment != null) {

                if (
                        (PermissionFragmentHelper.isPermissionGranted(mFrgment, Manifest.permission.CAMERA))
                                &&
                                (PermissionFragmentHelper.isPermissionGranted(mFrgment, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                        ) {
                    camera();

                } else if (
                        !(PermissionFragmentHelper.isPermissionGranted(mFrgment, Manifest.permission.CAMERA))
                                &&
                                !(PermissionFragmentHelper.isPermissionGranted(mFrgment, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                        ) {
                    action_case = CAMERA_TXT;

                    isSingle = false;
                    permissionFragmentHelper
                            .setForceAccepting(false) // default is false. its here so you know that it exists.
                            .request(MULTI_PERMISSIONS);
                } else if (
                        !(PermissionFragmentHelper.isPermissionGranted(mFrgment, Manifest.permission.CAMERA))
                        ) {

                    action_case = CAMERA_TXT;

                    isSingle = true;
                    permissionFragmentHelper
                            .setForceAccepting(false) // default is false. its here so you know that it exists.
                            .request(Manifest.permission.CAMERA);
                } else if (
                        !(PermissionFragmentHelper.isPermissionGranted(mFrgment, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                        ) {

                    action_case = CAMERA_TXT;

                    isSingle = true;
                    permissionFragmentHelper
                            .setForceAccepting(false) // default is false. its here so you know that it exists.
                            .request(Manifest.permission.WRITE_EXTERNAL_STORAGE);

                }

            } else if (mActivity != null) {

                if (
                        (PermissionHelper.isPermissionGranted(mActivity, Manifest.permission.CAMERA))
                                &&
                                (PermissionHelper.isPermissionGranted(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                        ) {
                    camera();

                } else if (
                        !(PermissionHelper.isPermissionGranted(mActivity, Manifest.permission.CAMERA))
                                &&
                                !(PermissionHelper.isPermissionGranted(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                        ) {
                    action_case = CAMERA_TXT;

                    isSingle = false;
                    permissionHelper
                            .setForceAccepting(false) // default is false. its here so you know that it exists.
                            .request(MULTI_PERMISSIONS);
                } else if (
                        !(PermissionHelper.isPermissionGranted(mActivity, Manifest.permission.CAMERA))
                        ) {

                    action_case = CAMERA_TXT;

                    isSingle = true;
                    permissionHelper
                            .setForceAccepting(false) // default is false. its here so you know that it exists.
                            .request(Manifest.permission.CAMERA);
                } else if (
                        !(PermissionHelper.isPermissionGranted(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                        ) {

                    action_case = CAMERA_TXT;

                    isSingle = true;
                    permissionHelper
                            .setForceAccepting(false) // default is false. its here so you know that it exists.
                            .request(Manifest.permission.WRITE_EXTERNAL_STORAGE);

                }


            }

        } else {
            camera();
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void checkcCameraChoosePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (mFrgment != null) {


                if (
                        !(PermissionFragmentHelper.isPermissionGranted(mFrgment, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                        ) {

                    action_case = PHOTO_TXT;

                    isSingle = true;
                    permissionFragmentHelper
                            .setForceAccepting(false) // default is false. its here so you know that it exists.
                            .request(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                } else {

                    choose_photo();

                }


            } else if (mActivity != null) {
                if (
                        !(PermissionHelper.isPermissionGranted(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                        ) {
                    action_case = PHOTO_TXT;
                    isSingle = true;
                    permissionHelper
                            .setForceAccepting(false) // default is false. its here so you know that it exists.
                            .request(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                } else {
                    choose_photo();
                }

            }

        } else {
            choose_photo();
        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkVideoChoosePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (mFrgment != null) {

                if (
                        !(PermissionFragmentHelper.isPermissionGranted(mFrgment, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                        ) {
                    action_case = VIDEO_TXT;
                    isSingle = true;
                    permissionFragmentHelper
                            .setForceAccepting(false) // default is false. its here so you know that it exists.
                            .request(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                } else {
                    choose_video();
                }


            } else if (mActivity != null) {
                if (
                        !(PermissionHelper.isPermissionGranted(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                        ) {
                    action_case = VIDEO_TXT;
                    isSingle = true;
                    permissionHelper
                            .setForceAccepting(false) // default is false. its here so you know that it exists.
                            .request(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                } else {
                    choose_video();
                }


            }

        } else {
            choose_video();
        }

    }


    private void camera() {
        try {
            Intent intent = new Intent((MediaStore.ACTION_IMAGE_CAPTURE));
            File f = mediaPathHelper.getOutputMediaFile(1);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                picVidUri = FileProvider.getUriForFile(mActivity,
                        BuildConfig.APPLICATION_ID + ".provider", f);

            } else {
                picVidUri = Uri.fromFile(f); // create

            }

            intent.putExtra(MediaStore.EXTRA_OUTPUT, picVidUri);// set the image file

            if (mFrgment != null) {
                mFrgment.startActivityForResult(intent, REQUEST_CAMERA_CAPTURE_CODE);

            } else if (mActivity != null) {
                mActivity.startActivityForResult(intent, REQUEST_CAMERA_CAPTURE_CODE);

            }


//            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//                startActivityForResult(takePictureIntent, REQUEST_CAMERA_CAPTURE_CODE);
//            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(mActivity, mActivity.getString(R.string.errorincamera), Toast.LENGTH_SHORT).show();
        }
    }


    private void choose_photo() {

//        }
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");

        if (mFrgment != null) {
            mFrgment.startActivityForResult(intent, REQUEST_CAMERA_CHOOSE_CODE);
        } else if (mActivity != null) {
            mActivity.startActivityForResult(intent, REQUEST_CAMERA_CHOOSE_CODE);
        }


    }


    public void choose_video() {

        Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        intent.setType("video/*");
        mFrgment.startActivityForResult(intent, REQUEST_VIDEO_CHOOSE_CODE);
    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        try {
            if (mFrgment != null) {
                permissionFragmentHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
            } else if (mActivity != null) {
                permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }

        } catch (Exception e) {
            Log.e("Exception onRequestPermissionsResult", e.toString());
        }

    }

    public String onActivityResult(int requestCode, int resultCode, Intent data) {
        String mFilePath, returnedImagePath = "null";


        if (requestCode == REQUEST_CAMERA_CAPTURE_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                mFilePath = mediaPathHelper.getPath(picVidUri, "image");
                String type = mediaPathHelper.getMimeType(picVidUri);
                if (type == null) {
                    Toast.makeText(mActivity, mActivity.getString(R.string.tryanothertype), Toast.LENGTH_SHORT).show();
                } else if (type.equals("image/jpeg") || type.equals("image/jpg") || type.equals("image/png")) {
                    Log.e("---- file type -----", "typed " + type);

                    Bitmap returnedBimap = saveBitmapToFile(new File(mFilePath));
                    if (returnedBimap.getWidth() > returnedBimap.getHeight()) {
                        returnedBimap = rotateBitmap(returnedBimap, -90);
                    }
                    saveBitmapToFile(returnedBimap, new File(mFilePath));

                    returnedImagePath = mFilePath;
                }
            } else {

            }


        } else if (requestCode == REQUEST_CAMERA_CHOOSE_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                picVidUri = data.getData();
                mFilePath = mediaPathHelper.getPath(picVidUri, "image");
                String type = mediaPathHelper.getMimeType(picVidUri);
                if (type == null) {
                    Toast.makeText(mActivity, mActivity.getString(R.string.tryanothertype), Toast.LENGTH_SHORT).show();
                } else if (type.equals("image/jpeg") || type.equals("image/jpg") || type.equals("image/png")) {
                    Log.e("---- file type -----", "type " + type);
                    returnedImagePath = mFilePath;
                }
            } else {

            }


        } else if (requestCode == REQUEST_VIDEO_CHOOSE_CODE)

        {
            if (resultCode == Activity.RESULT_OK) {
                picVidUri = data.getData();

                mFilePath = mediaPathHelper.getPath(picVidUri, "video");
                String type = mediaPathHelper.getMimeType(picVidUri);
                Log.e("---- file type -----", "type " + type);
                if (type == null) {
                    Toast.makeText(mActivity, mActivity.getString(R.string.tryanothertype), Toast.LENGTH_SHORT).show();
                } else {
                    returnedImagePath = mFilePath;
                }
            } else {

            }


        } else if (requestCode == REQUEST_VIDEO_CAPTURE_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                returnedImagePath = data.getData().getPath();
                // final File file = new File(model.getData().getPath());
            } else {

            }


        } else {
            if (mFrgment != null) {
                permissionFragmentHelper.onActivityForResult(requestCode);
            } else if (mActivity != null) {
                permissionHelper.onActivityForResult(requestCode);
            }

        }


        Log.e("onActivityResult", returnedImagePath);
        return returnedImagePath;
    }

    public static Bitmap rotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public Bitmap saveBitmapToFile(Bitmap bitmap, File file) {
        try {
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            return bitmap;
        } catch (Exception e) {

            Log.e("Exception saveBitmapToFile ", e.toString());
            return null;
        }
    }

    public Bitmap saveBitmapToFile(File file) {
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

            return selectedBitmap;
        } catch (Exception e) {

            Log.e("Exception saveBitmapToFile", e.toString());
            return null;
        }
    }

    @Override
    public void onPermissionGranted(@NonNull String[] permissionName) {

        Log.e("onPermissionGranted", "Permission(s) " + Arrays.toString(permissionName) + " Granted");

        switch (action_case) {
            case "camera":

                camera();
                break;

            case "photo":

                choose_photo();
                break;
            case "video":
                choose_video();
                break;


        }


    }

    @Override
    public void onPermissionDeclined(@NonNull String[] permissionName) {
        Log.e("onPermissionDeclined", "Permission(s) " + Arrays.toString(permissionName) + " Declined");

    }

    @Override
    public void onPermissionPreGranted(@NonNull String permissionsName) {
        Log.i("onPermissionPreGranted", "Permission( " + permissionsName + " ) preGranted");

    }

    @Override
    public void onPermissionNeedExplanation(@NonNull String permissionName) {

        Log.e("NeedExplanation", "Permission( " + permissionName + " ) needs Explanation");
        if (!isSingle) {
            if (mFrgment != null) {
                neededPermission = PermissionFragmentHelper.declinedPermissions(mFrgment, MULTI_PERMISSIONS);
            } else if (mActivity != null) {
                neededPermission = PermissionHelper.declinedPermissions(mActivity, MULTI_PERMISSIONS);
            }

            StringBuilder builder = new StringBuilder(neededPermission.length);
            if (neededPermission.length > 0) {
                for (String permission : neededPermission) {
                    builder.append(permission).append("\n");
                }
            }
            //result.setText("Permission( " + builder.toString() + " ) needs Explanation");
            AlertDialog alert = getAlertDialog(neededPermission, builder.toString());
            if (!alert.isShowing()) {
                alert.show();
            }
        } else {
            //result.setText("Permission( " + permissionName + " ) needs Explanation");
            getAlertDialog(permissionName).show();
        }


    }

    @Override
    public void onPermissionReallyDeclined(@NonNull String permissionName) {
        Log.e("ReallyDeclined", "Permission " + permissionName + " can only be granted from settingsScreen");

        if (mFrgment != null) {
            /** you can call  {@link PermissionFragmentHelper#openSettingsScreen(Context)} to open the settings screen */
        } else if (mActivity != null) {
            /** you can call  {@link PermissionHelper#openSettingsScreen(Context)} to open the settings screen */
        }

    }

    @Override
    public void onNoPermissionNeeded() {
        Log.e("onNoPermissionNeeded", "Permission(s) not needed");

    }

    public AlertDialog getAlertDialog(final String[] permissions, final String permissionName) {
        if (alertBuilder == null) {
            alertBuilder = new AlertDialog.Builder(mActivity)
                    .setTitle("Permission Needs Explanation")
                    .create();
        }
        alertBuilder.setButton(DialogInterface.BUTTON_POSITIVE, "Request", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (mFrgment != null) {
                    permissionFragmentHelper.requestAfterExplanation(permissions);
                } else if (mActivity != null) {
                    permissionHelper.requestAfterExplanation(permissions);
                }

            }
        });
        alertBuilder.setMessage("Permissions need explanation (" + permissionName + ")");
        return alertBuilder;
    }


    public AlertDialog getAlertDialog(final String permission) {
        if (alertBuilder == null) {
            alertBuilder = new AlertDialog.Builder(mActivity)
                    .setTitle("Permission Needs Explanation")
                    .create();
        }
        alertBuilder.setButton(DialogInterface.BUTTON_POSITIVE, "Request", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mFrgment != null) {
                    permissionFragmentHelper.requestAfterExplanation(permission);
                } else if (mActivity != null) {
                    permissionHelper.requestAfterExplanation(permission);
                }

            }
        });
        alertBuilder.setMessage("Permission need explanation (" + permission + ")");
        return alertBuilder;
    }


}