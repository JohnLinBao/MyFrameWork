package com.alfred.framework.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by AlfredZhou on 2017/9/6.
 */

public class PermissionUtils {
    public static int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0x10;
    /**
     * 判断应用是否具备所需权限
     * @param context Act
     * @param permission mainfest权限
     * @param MY_PERMISSIONS_REQUEST_READ_CONTACTS 回调返回值
     */
    public static void checkAndRequestPermission(Activity context, String permission, int MY_PERMISSIONS_REQUEST_READ_CONTACTS){

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(context,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(context,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }
}
