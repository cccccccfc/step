package com.example.zhang.touchapplication.recorddb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/22.
 */

public class RecordDataBaseInfo extends DataBaseDao {
    private static final String DATABASE_NAME = "record_data";
    private static final String sql = "select * from "+DATABASE_NAME;
    private static RecordDataBaseInfo mInfo;
    public RecordDataBaseInfo(Context context) {
        super(context);
    }

    /**
     * 实例化
     */
    public static synchronized RecordDataBaseInfo getInstance(Context context){
        if(null == mInfo){
            mInfo = new RecordDataBaseInfo(context);
        }
        return mInfo;
    }
    /**
     * 增加数据
     */
    public void addHistoryName(String year,String starttime,String stoptime,String num){
        ContentValues values = new ContentValues();
        values.put("year", year);
        values.put("starttime", starttime);
        values.put("stoptime", stoptime);
        values.put("num", num);
        mWriteSQL.insert(DATABASE_NAME, null, values);
    }
    /**
     * 通过position删除对应的数据
     */
    public void dele2Position(int position){
        mWriteSQL.delete(DATABASE_NAME, "position=?", new String[]{ String.valueOf(position)});
    }
    /**
     * 通过输入文字删除已存在的重复数据
     */
    public void deleRepetition(String s){
        mWriteSQL.delete(DATABASE_NAME, "content=?", new String[]{s});

    }
    /**
     * 查询数据
     */
    public List<RecordEntity> queryEntit(){
        List<RecordEntity> list = new ArrayList<RecordEntity>();
        Cursor c = mReadSQL.rawQuery(sql, null);
        while (c.moveToNext()) {
            RecordEntity entity = new RecordEntity();
            int cYear = c.getColumnIndex("year");
            int cStarttime = c.getColumnIndex("starttime");
            int cStoptime = c.getColumnIndex("stoptime");
            int cNum = c.getColumnIndex("num");
            entity.year = c.getString(cYear);
            entity.starttime = c.getString(cStarttime);
            entity.stoptime = c.getString(cStoptime);
            entity.num = c.getString(cNum);
            list.add(entity);
        }
        return list;
    }

    /**
     * 清空数据库
     */
    public void clearDataBase(){
        mWriteSQL.delete(DATABASE_NAME, null, null);
    }
}
