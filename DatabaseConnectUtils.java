package com.photovault.Utils;

import android.content.Context;

import androidx.room.Room;

import com.photovault.Model.database.DatabaseDao;

/**
 * 这个工具类用来连接数据库
 * @author guanhua
 */
public class DatabaseConnectUtils {

    public static DatabaseDao getDatabaseDao(Context context){

        //实例化数据库
        //要加入allowMainThreadQueries才能在主线程操作数据库
        DatabaseDao db = Room.databaseBuilder(context,
                DatabaseDao.class,"user_data")
                .allowMainThreadQueries()
                .build();

        return db;
    }


}
