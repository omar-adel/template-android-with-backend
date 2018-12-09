package modules.general.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.WindowManager;

import com.example.template.R;


/**
 * Created by Net22 on 2/6/2018.
 */

public class ProgressShowHide {

    Context context ;
    ProgressDialog progressDialog ;
    public ProgressShowHide(Context context)
    {
        this.context=context;
    }

    public void showProgress(String message){
        try {
            if(progressDialog==null){
                progressDialog=new ProgressDialog(context);
            }

            if(!progressDialog.isShowing())
            {
                progressDialog.setMessage(message);
                progressDialog.show();
                progressDialog.setCancelable(false);
                //progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                //progressDialog.setContentView(R.layout.progress_dialog);
                progressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

            }
        }
        catch (Exception e)
        {
            Log.e("showProgress Exception ",e.toString());
        }
    }

    public void showProgress(){
        try {
            if(progressDialog==null){
                progressDialog=new ProgressDialog(context);
            }

            if(!progressDialog.isShowing())
           {
                progressDialog.show();
                progressDialog.setCancelable(false);
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                progressDialog.setContentView(R.layout.progress_dialog);
                progressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

          }
        }
        catch (Exception e)
        {
            Log.e("showProgress Exception ",e.toString());
        }
    }
     public void hideProgress(){
        try {
            if(progressDialog!=null){
                if(progressDialog.isShowing())
                {
                    progressDialog.dismiss();
                    progressDialog=null;
                }
                else
                {
                    progressDialog=null;
                }
            }
            else
            {
                progressDialog=null;
            }
        }
        catch (Exception e)
        {
            Log.e("hideProgress Exception ",e.toString());
        }


    }
}
