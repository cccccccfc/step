package com.example.zhang.touchapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.zhang.touchapplication.R;
import com.example.zhang.touchapplication.activity.RecordActivity;
import com.example.zhang.touchapplication.recorddb.RecordEntity;
import java.util.List;

/**
 * Created by Administrator on 2017/10/31.
 */

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {
    private final List<RecordEntity> mRecordEntities;
    private Context mContext;

    public RecordAdapter(List<RecordEntity> recordEntities, RecordActivity context) {
        this.mRecordEntities = recordEntities;
        this.mContext= context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_text_hint, parent, false);
        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       // Log.i("qaz", "onBindViewHolder: "+mRecordEntities.get(position).year);
        holder.mTvyear.setText(mRecordEntities.get(position).year);
        holder.mTvtime.setText(mRecordEntities.get(position).starttime+" - "+mRecordEntities.get(position).stoptime);
        holder.mTvnum.setText(mRecordEntities.get(position).num);
    }
    @Override
    public int getItemCount() {
        return mRecordEntities == null ? 0 : mRecordEntities.size();

    }
    //自定义ViewHolder
    class ViewHolder  extends RecyclerView.ViewHolder {

        TextView mTvyear,mTvtime,mTvnum;

        public ViewHolder (View view) {
            super(view);
            mTvyear = (TextView) itemView.findViewById(R.id.tv_year);
            mTvtime = (TextView) itemView.findViewById(R.id.tv_time);
            mTvnum = (TextView) itemView.findViewById(R.id.tv_num);
        }
    }

}
