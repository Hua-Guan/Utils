package com.photovault.Utils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 这个工具类可以解密文件
 * @author guanhua
 */
public class DecipherUtils {


    /**
     * @param encryptFile 加密文件
     * @param decipherByte 解密字节数组
     * @throws IOException 异常
     */
    public static void decipherPhoto(File encryptFile, byte[] decipherByte) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(encryptFile,"rw");
        randomAccessFile.seek(0);
        randomAccessFile.write(decipherByte,0,decipherByte.length);
        randomAccessFile.close();
    }

}
