package com.webcreo.nirmal.mukthi.temp.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by prathik on 17/08/17.
 */

public class ManagerPermission {

    public static final int PERMISSION_ALL = 99;

    public static final int PERMISSION_READ_EXTERNAL_STORAGE = 100;
    public static final int PERMISSION_WRITE_EXTERNAL_STORAGE = 101;
    public static final int PERMISSION_CAMERA = 102;

    public static final int PERMISSION_LOCATION = 103;

    public static final int EXTERNAL_STORAGE_PERMISSION_CONSTANT = 0;
    public static final int REQUEST_PERMISSION_SETTING = 2;

    //public static final int PERMISSION_WRITE_EXTERNAL_STORAGE = 1;
    final public int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 0;

    private boolean sentToSettings = false;

    public boolean askedOnce = false;
    public Context context;
    //int PERMISSION_ALL = 1;
    Activity activity;

    public ManagerPermission(Context context) {
        this.context = context;
        this.activity = (Activity)context;
    }

    public void checkPermission(String... PERMISSIONS) {
        if (!hasPermissions(context, PERMISSIONS)) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS, PERMISSION_ALL);
        }
    }

    public void checkPermissionNew(int requestCode,String... PERMISSIONS) {
        if (!hasPermissions(context, PERMISSIONS)) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS, PERMISSION_ALL);
        }
    }

    public boolean isDontAskAgainChecked(String permission)
    {
        return ActivityCompat.shouldShowRequestPermissionRationale( activity, permission);
    }

    public void checkPermission(int requestCode, String permission) {

        if (!hasPermissions(context, permission)) {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
            askedOnce = true;
            return;
        }
        if(askedOnce){
            boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale( activity,permission);
            if (showRationale) {
                ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
            }
            else {
                goToSettings();
            }
        }
    }

    public void goToSettings()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.activity);
        builder.setTitle("Need Permission !");
        builder.setMessage("App needs permission to work this function. Go to settings and grant permission ?");
        builder.setPositiveButton("SETTINGS", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                activity.startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                dialog.cancel();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });
        builder.show();

    }

    public boolean hasPermissions(String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public void checkMultiplePermission(int requestCode,String... PERMISSIONS) {
        if (!hasPermissions(context, PERMISSIONS)) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS, PERMISSION_ALL);
        }
    }

    public boolean isAllPermissionsGranted(String... PERMISSIONS) {
        boolean isAllpermissionsGranted = true;

        requestPermission();
        if (!hasPermissions(context, PERMISSIONS)) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS, PERMISSION_ALL);
        }
        return isAllpermissionsGranted;
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean requestPermission()
    {
        if (ActivityCompat.checkSelfPermission(this.activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this.activity, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            {
                //Show Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(this.activity);
                builder.setTitle("Need Storage Permission");
                builder.setMessage("This app needs storage permission.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        }


        return true;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
            {

            }
            break;
            default:
        }
    }
}