/**
 * 截取屏幕截图工具类ScreenShot
 * @Author:房文宇
 */
package com.ar.arhome.Util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

public class ScreenShot {

    private Activity activity;
    public ScreenShot(Activity activity){
        this.activity = activity;
    }

    public void activityShot() {
        View view = activity.getWindow().getDecorView();

        //允许当前窗口保存缓存信息
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();

        //获取状态栏高度
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        int statusBarHeight = rect.top;

        WindowManager windowManager = activity.getWindowManager();

        //获取屏幕宽和高
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;

        //去掉状态栏
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache(), 0, statusBarHeight, width,
                height - statusBarHeight);

        //销毁缓存信息
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(false);
        // 获取时间戳作为文件名
        Date date = new Date();
        long currentTime = date.getTime();
        Toast.makeText(activity, "图片捕获",Toast.LENGTH_SHORT).show();
        saveBitmapToLocal(String.valueOf(currentTime), bitmap);

    }


    private  void saveBitmapToLocal(String fileName, Bitmap bitmap) {
        try {
            String FILE_PATH = activity.getApplicationContext().getFilesDir().getAbsolutePath() + "/photos/";

            File file = new File(FILE_PATH, fileName + ".png");
            // file其实是图片，它的父级File是文件夹，判断一下文件夹是否存在，如果不存在，创建文件夹
            File fileParent = file.getParentFile();
            if (!fileParent.exists()) { // 文件夹不存在
                fileParent.mkdirs();// 创建文件夹
            } // 将图片保存到本地
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
            Toast.makeText(activity,"图片保存在"+FILE_PATH+fileName+".png", Toast.LENGTH_SHORT).show();

            // 其次把文件插入到系统图库
            try {
                MediaStore.Images.Media.insertImage(activity.getContentResolver(),
                        file.getAbsolutePath(), fileName, null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // 最后通知图库更新
            activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + FILE_PATH+fileName+".png")));
        } catch (Exception e) {
            Toast.makeText(activity, "图片保存失败",Toast.LENGTH_SHORT).show();
            Log.d("exception_save",e.getMessage());
            e.printStackTrace();
        }

    }
}



