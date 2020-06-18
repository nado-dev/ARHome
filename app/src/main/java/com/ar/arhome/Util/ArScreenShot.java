package com.ar.arhome.Util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.view.PixelCopy;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.ux.ArFragment;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ArScreenShot {
    private Activity activity;
    private ArFragment arFragment;
    public ArScreenShot(Activity activity, ArFragment arFragment){
        this.activity = activity;
        this.arFragment = arFragment;
    }

     private String generateFilename() {

        //根据当前时间创建文件名
        String date =
                new SimpleDateFormat("yyyyMMddHHmmss", java.util.Locale.getDefault()).format(new Date());
        return Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES) + File.separator + "ARHome/" + date + "_screenshot";
    }


    private  void saveBitmapToLocal(Bitmap bitmap,String fileName) {
        try {
            String FILE_PATH = activity.getApplicationContext().getFilesDir().getAbsolutePath() + "/photos/";

            File file = new File(FILE_PATH, fileName + ".png");
            // file是图片，它的父级File是文件夹，判断一下文件夹是否存在，如果不存在，创建文件夹
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
            e.printStackTrace();
        }

    }

    public void takePhoto() {
        //使用PixelCopy将相机屏幕和对象创建为位图
        final String filename = generateFilename();
        ArSceneView view = arFragment.getArSceneView();

        final Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
                Bitmap.Config.ARGB_8888);

        final HandlerThread handlerThread = new HandlerThread("PixelCopier");
        handlerThread.start();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            PixelCopy.request(view, bitmap, (copyResult) -> {
                if (copyResult == PixelCopy.SUCCESS) {
                    try {
                        saveBitmapToLocal(bitmap, filename);

                        //媒体扫描
                        Uri uri = Uri.parse("file://" + filename);
                        Intent i = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        i.setData(uri);
                        activity.sendBroadcast(i);

                    } catch (Exception e) {
                        Toast toast = Toast.makeText(activity, e.toString(),
                                Toast.LENGTH_LONG);
                        toast.show();
                        return;
                    }
                    Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content),
                            "屏幕截图已保存。", Snackbar.LENGTH_LONG);

                    snackbar.show();
                } else {
                    Toast toast = Toast.makeText(activity,
                            "无法保存屏幕截图！： " + copyResult, Toast.LENGTH_LONG);
                    toast.show();
                }
                handlerThread.quitSafely();
            }, new Handler(handlerThread.getLooper()));
        }
    }
}
