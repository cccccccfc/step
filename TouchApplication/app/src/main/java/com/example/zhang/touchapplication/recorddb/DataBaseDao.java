package com.example.zhang.touchapplication.recorddb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Administrator on 2017/11/22.
 */

public class DataBaseDao {
    public SQLiteDatabase mReadSQL;
    public SQLiteDatabase mWriteSQL;
    private Context context;
    public DataBaseDao(Context context) {
        super();
        this.context = context;
        mReadSQL = RecordHelper.getReadDataBase(context);
        mWriteSQL = RecordHelper.getWriteDataBase(context);
    }
}
