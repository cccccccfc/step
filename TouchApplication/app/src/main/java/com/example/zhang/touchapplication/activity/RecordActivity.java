package com.example.zhang.touchapplication.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.zhang.touchapplication.R;
import com.example.zhang.touchapplication.adapter.RecordAdapter;
import com.example.zhang.touchapplication.recorddb.RecordDataBaseInfo;
import com.example.zhang.touchapplication.recorddb.RecordEntity;
import com.example.zhang.touchapplication.utils.BaseActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 历史数据
 */
public class RecordActivity extends BaseActivity {

  @BindView(R.id.rec_record) RecyclerView recRecord;
  @BindView(R.id.iv_left) ImageView ivLeft;
  @BindView(R.id.iv_dummy) ImageView ivDummy;
  private RecordAdapter recordAdapter;
  public List<RecordEntity> recordEntities;
  //数据库
  private RecordDataBaseInfo recordDataBaseInfo;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setTitle(true);//设置是否显示标题栏
    setState(true);//设置是否显示状态栏
    setContentView(R.layout.activity_record);
    ButterKnife.bind(this);
    recordDataBaseInfo = RecordDataBaseInfo.getInstance(this);
    ////初始化控件
    initView();
    ////设置数据
    initData();
  }

  private void initData() {
    recordEntities = new ArrayList<>();
    if (recordDataBaseInfo.queryEntit() != null && recordDataBaseInfo.queryEntit().size() > 0) {
      recRecord.setVisibility(View.VISIBLE);
      ivDummy.setVisibility(View.GONE);
      recordEntities.addAll(recordDataBaseInfo.queryEntit());
      Collections.reverse(recordEntities);
      recordAdapter = new RecordAdapter(recordEntities, this);
      recRecord.setLayoutManager(new LinearLayoutManager(this));
      recRecord.setAdapter(recordAdapter);
      recordAdapter.notifyDataSetChanged();
    }else {
      recRecord.setVisibility(View.GONE);
      ivDummy.setVisibility(View.VISIBLE);
    }
  }

  private void initView() {
    ivLeft.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
      finish();
      }
    });
  }
}
