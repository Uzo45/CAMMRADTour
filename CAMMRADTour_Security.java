package com.juxtopia.cammradtour;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

public class CAMMRADTour_Security extends Activity {

    private static final int CAMERA_PERM = 0;
    //public boolean perm = false;
    public Activity MainAct;
    public Context mainCon;
    //private CAMMRADTour_Camera camera;

    /*public boolean returnPerm(){
        return perm;
    }*/


    //this part is a method to ask the user for permission to use the camera
    public void getPermission()
    {
        if (ActivityCompat.checkSelfPermission(mainCon, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
        {
            //todo
        }
        else
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainAct, Manifest.permission.CAMERA))
            {
                ActivityCompat.requestPermissions(MainAct, new String[]{Manifest.permission.CAMERA}, CAMERA_PERM);
            } else
            {
                ActivityCompat.requestPermissions(MainAct, new String[]{Manifest.permission.CAMERA}, CAMERA_PERM);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if (requestCode == CAMERA_PERM)
        {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                //todo
            }
            else
            {
                //here it asks again
                if(ActivityCompat.shouldShowRequestPermissionRationale(MainAct,Manifest.permission.CAMERA))
                {

                    new AlertDialog.Builder(mainCon)
                            .setTitle("Permission")
                            .setMessage("Please give the app permission to use the camera for the features of this app")
                            .setPositiveButton("Proceed", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    ActivityCompat.requestPermissions(MainAct,new String[]{Manifest.permission.CAMERA},CAMERA_PERM);
                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i)
                        {
                            dialog.dismiss();
                            //finish();
                        }
                    }).create().show();
                }
                else
                {
                    //if denied, it will take you to the settings
                    new AlertDialog.Builder(mainCon)
                            .setTitle("Permission")
                            .setMessage("Some permissions were denied. Allow all permission at [Setting] > [Permission]")
                            .setPositiveButton("Proceed", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    dialog.dismiss();
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                            Uri.fromParts("package",getPackageName(), null));
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            }).setNegativeButton("No, Exit App", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i)
                        {
                            dialog.dismiss();
                            finish();
                        }
                    }).create().show();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void permissionStorage()
    {
        if(ActivityCompat.shouldShowRequestPermissionRationale(MainAct, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            Toast.makeText(MainAct,"Storage permission is requires,please allow from settings",Toast.LENGTH_SHORT).show();
        }else
            ActivityCompat.requestPermissions(MainAct,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},111);
    }

    public void authenticate(String UserName, String Password){
        String realUser = " ", realPass = " ";

        if(Password == realPass){
            //grant access
        }
    }

}
