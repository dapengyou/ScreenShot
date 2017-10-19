package com.test.screenshot;

/**
 * Created by lady_zhou on 2017/10/18.
 */

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 进行截屏工具类
 */
public class ScreenShotUtils {
    /**
     * 进行截取屏幕
     *
     * @param activity
     * @return bitmap
     */
    public static Bitmap takeScreenShot(Activity activity) {
        Bitmap bitmap = null;
        int statusHeight = -1;
        View view = activity.getWindow().getDecorView();
        // 设置是否可以进行绘图缓存
        view.setDrawingCacheEnabled(true);
        // 如果绘图缓存无法，强制构建绘图缓存
        view.buildDrawingCache();
        // 返回这个缓存视图
        bitmap = view.getDrawingCache();
        Log.d("123", "bitmap.getHeight():" + bitmap.getHeight());

//        // 获取状态栏高度，状态栏高度方法一
//        Rect frame = new Rect();
//        // 测量屏幕宽和高
//        view.getWindowVisibleDisplayFrame(frame);
//        int statusHeight = frame.top;
//        Log.d("jiangqq", "状态栏的高度为:" + statusHeight);
        //状态栏的高度方法二
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {

            statusHeight = activity.getResources().getDimensionPixelSize(resourceId);

        }
//        Log.d("jiangqq", "状态栏的高度1为:" + activity.getResources().getDimensionPixelSize(resourceId));


//        int statusHeight = -1;
//        //屏幕
//        DisplayMetrics dm = new DisplayMetrics();
//
//        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
//
//        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
//
//        if (resourceId > 0) {
//
//            statusHeight = activity.getResources().getDimensionPixelSize(resourceId);
//
//        }
//        //View绘制区域，//viewtop方法一
//        Rect outRect2 = new Rect();
//        activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(outRect2);
//        int viewTop = dm.heightPixels - statusHeight - outRect2.height();
        //viewTop方法二
        int viewTop = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();

        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();
        // 根据坐标点和需要的宽和高创建bitmap
        bitmap = Bitmap.createBitmap(bitmap, 0, statusHeight + viewTop, width, height - statusHeight - viewTop);
        return bitmap;
    }


    /**
     * 保存图片到sdcard中
     *
     * @param pBitmap
     */
    private static boolean savePic(Bitmap pBitmap, File strName) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(strName);
            if (null != fos) {
                pBitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
                fos.flush();
                fos.close();
                return true;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 截图
     *
     * @param activity
     * @return 截图并且保存sdcard成功返回true，否则返回false
     */
    public static boolean shotBitmap(Activity activity) {
        String sdCardDir = Environment.getExternalStorageDirectory() + "/wurenji/";
        File dirFile = new File(sdCardDir);  //目录转化成文件夹
        if (!dirFile.exists()) {              //如果不存在，那就建立这个文件夹
            dirFile.mkdirs();
        }
        //指定目录
        File file = new File(sdCardDir, System.currentTimeMillis() + ".png");
        return ScreenShotUtils.savePic(takeScreenShot(activity), file);
    }


}

