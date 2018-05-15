package com.example.zhang.touchapplication.recorddb;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/22.
 */

public class RecordEntity implements Serializable {
    public String year;
    public String starttime;
    public String stoptime;
    public String num;

    @Override
    public String toString() {
        return "RecordEntity{" +
                "year=" + year +
                ", starttime='" + starttime + '\'' +
                ", stoptime='" + stoptime + '\'' +
                ", num='" + num + '\'' +
                '}';
    }
}
