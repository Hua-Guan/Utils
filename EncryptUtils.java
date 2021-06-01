package com.photovault.Utils;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 这个工具类可以对照片进行加密，并保存到特定目录中
 * @author guanhua
 */
public class EncryptUtils {


    /**
     *
     * @param context 上下文对象
     * @param originFileUri 原来的文件uri
     * @param encryptFile 加密文件
     * @return 返回解密字节数组
     */
    public static byte[] encryptPhoto(Context context, Uri originFileUri, File encryptFile) {

        //解密字节数组
        byte[] decipherByte = new byte[4];

        ContentResolver resolver = context.getContentResolver();
        try (InputStream stream = resolver.openInputStream(originFileUri)) {

            //原来的文件字节数组缓冲流
            BufferedInputStream bufferedInputStream = new BufferedInputStream(stream);
            byte[] buffer = new byte[1024];

            //加密字节数组
            byte[] encryptByte = {0, 0, 0, 0};

            //获取新建的加密文件缓冲流
            FileOutputStream fileOutputStream = new FileOutputStream(encryptFile);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

            //保存解密字节数组
            int read = bufferedInputStream.read(decipherByte);

            //对文件进行加密
            int len = -1;
            bufferedOutputStream.write(encryptByte, 0, encryptByte.length);

            while ((len = bufferedInputStream.read(buffer)) != -1) {
                bufferedOutputStream.write(buffer, 0, len);
            }

            bufferedInputStream.close();
            bufferedOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return decipherByte;

    }
}
