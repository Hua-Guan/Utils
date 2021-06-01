package com.photovault.Utils;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.annotation.RequiresApi;

import com.photovault.Model.Bean.ImageBean;
import com.photovault.Model.Bean.SystemAlbumBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 这个工具类用来查询系统中的照片
 * @author guanhua
 */
public class QueryUtils {


    /**
     * 查询手机内的所有系统相册
     * @param context 上下文对象
     * @return 返回保护系统相册的集合
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static List<SystemAlbumBean> getSystemAlbumBean(Context context){

        String[] projection = {MediaStore.Images.Media._ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                //注意" DESC"有空格
                MediaStore.Images.Media.DATE_ADDED + " DESC"
        );

        List<SystemAlbumBean> beanList = new ArrayList<>();

        List<String> albumNameList = new ArrayList<>();
        int albumNameIndex = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

        List<Uri> uriList = new ArrayList<>();
        int albumImageUriIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);

        List<String> imageCountList = new ArrayList<>();
        String[] albumName = new String[1];
        while (cursor.moveToNext()){
            String name = cursor.getString(albumNameIndex);
            if (!albumNameList.contains(name)){
                albumNameList.add(name);

                long id = cursor.getLong(albumImageUriIndex);
                uriList.add(ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id
                        ));

                albumName[0] = name;
                imageCountList.add(getAlbumImageCount(albumName,context));
            }
        }
        cursor.close();

        for(int i = 0 ; i < albumNameList.size(); i++){
            SystemAlbumBean bean = new SystemAlbumBean();
            bean.setAlbumName(albumNameList.get(i));
            bean.setImageUri(uriList.get(i));
            bean.setAlbumCount(String.valueOf(imageCountList.get(i)));
            beanList.add(bean);
        }
        return beanList;

    }

    /**
     * 查询某个系统相册的相片总数
     * @param albumName 相册名字
     * @param context 上下文对象
     * @return 返回相册总数的字符串
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private static String getAlbumImageCount(String[] albumName, Context context){

        @SuppressLint("Recycle") Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,
                //注意要加问好
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME + "=?",
                albumName,
                //注意" DESC"有空格
                MediaStore.Images.Media.DATE_ADDED + " DESC"
        );
        int count = 0;
        while (cursor.moveToNext()){
            count ++;
        }
        cursor.close();
        return String.valueOf(count);
    }

    /**
     * 查询某个系统相册的所有相片路径和相片的名字
     * @param albumName 相册名字
     * @param context 上下文对象
     * @return 返回相册总数的字符串
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static List<ImageBean> getAlbumImageUriAndName(String[] albumName, Context context){

        //查询照片的id和名字
        String[] projection = {MediaStore.Images.Media._ID,
        MediaStore.Images.Media.DISPLAY_NAME};

        //游标
        @SuppressLint("Recycle") Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                //注意要加问好
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME + "=?",
                albumName,
                //注意" DESC"有空格
                MediaStore.Images.Media.DATE_ADDED + " DESC"
        );
        int albumImageUriIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
        int albumImageNameIndex = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME);

        //装载的集合
        List<ImageBean> imageBeanList = new ArrayList<>();

        while (cursor.moveToNext()){
            ImageBean bean = new ImageBean();
            long id = cursor.getLong(albumImageUriIndex);
            bean.setUri(ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
            ));
            bean.setImageName(cursor.getString(albumImageNameIndex));
            imageBeanList.add(bean);
        }

        return imageBeanList;
    }

    /**
     * 查询手机内所有照片的URI
     * @param context 上下文对象
     * @return 返回存放URI的集合
     */
    public static List<ImageBean> getImageUriList(Context context){

        String[] projection = new String[]{
                MediaStore.Images.Media._ID,
        };

        List<ImageBean> list =  new ArrayList<>();

        @SuppressLint("Recycle") Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null
        );

        int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);

        cursor.moveToFirst();
        while (cursor.moveToNext()){
            long id = cursor.getLong(idColumn);
            Uri contentUri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
            ImageBean imageBean = new ImageBean();
            imageBean.setUri(contentUri);
            list.add(imageBean);
        }

        cursor.close();
        return list;
    }

}
