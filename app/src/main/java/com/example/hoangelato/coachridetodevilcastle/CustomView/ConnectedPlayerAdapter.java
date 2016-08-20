package com.example.hoangelato.coachridetodevilcastle.CustomView;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bloe.rewrite.R;
import com.example.bloe.rewrite.databinding.ItemConnectedPlayerBinding;

import java.util.Vector;

/**
 * Created by bloe on 19/08/2016.
 */
public class ConnectedPlayerAdapter extends MyAdapter<PlayerInfo> {

    public ConnectedPlayerAdapter(Context mContext, Vector<PlayerInfo> dataList, OnItemClickListener onItemClickListener) {
        super(mContext, dataList, onItemClickListener);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_connected_player, parent, false);

        return new PlayerInfoViewHolder(view);
    }

    public class PlayerInfoViewHolder extends MyViewHolder {

        ItemConnectedPlayerBinding binding;

        public PlayerInfoViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void initView(View view) {
            binding = DataBindingUtil.bind(view);
        }

        @Override
        public void setData(PlayerInfo data) {
            binding.textViewPlayerIp.setText(data.playerIp);
            binding.textViewPlayerName.setText(data.playerName);
        }
    }
}
