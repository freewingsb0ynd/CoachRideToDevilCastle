package com.example.hoangelato.coachridetodevilcastle.CustomView;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Vector;

/**
 * Created by bloe on 09/08/2016.
 */

public abstract class MyAdapter<T extends MyDataNode> extends RecyclerView.Adapter<MyAdapter<T>.MyViewHolder>{
    protected Vector<T> dataList = new Vector<>();
    protected Context mContext;
    protected OnItemClickListener onItemClickListener;

    public MyAdapter(Context mContext, Vector<T> dataList, OnItemClickListener onItemClickListener) {
        this.mContext = mContext;
        this.dataList = dataList;
        this.onItemClickListener = onItemClickListener;
    }

    public void add(T data, int pos) {
        dataList.add(pos, data);
        notifyItemInserted(pos);
    }

    public void delete(int pos) {
        dataList.remove(pos);
        notifyItemRemoved(pos);
    }

    public void delete(T data) {
        int pos = dataList.indexOf(data);
        dataList.remove(data);
        notifyItemRemoved(pos);
    }

    public T getDataAt(int pos) {
        return  dataList.get(pos);
    }

    public void setDataList(Vector<T> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bindData(dataList.get(position));
    }

    public abstract class MyViewHolder extends RecyclerView.ViewHolder{

        public T data;
        //MyViewHolder current = this;



        public MyViewHolder(View itemView) {
            super(itemView);
            initView(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(MyViewHolder.this);
                }
            });
        }

        public abstract void initView(View view);

        public void bindData(T data) {
            this.data = data;
            setData(data);
        }

        public abstract void setData(T data);
    }


}
