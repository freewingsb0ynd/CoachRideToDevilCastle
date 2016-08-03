package com.example.hoangelato.coachridetodevilcastle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hoangelato.coachridetodevilcastle.Network.DataHelper;
import com.example.hoangelato.coachridetodevilcastle.Network.EndPoint;
import com.example.hoangelato.coachridetodevilcastle.Network.Server;

import java.util.ArrayList;

public class HostWaitingActivity extends AppCompatActivity implements View.OnClickListener{
    ListView listConnectedPlayers;
    Button btnStart;
    Server mServer;
    TextView curIp;
    final MyAdapter myAdapter = new MyAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_waiting);
        mServer = new Server();
        initView();
    }

    private void initView() {
        curIp = (TextView) findViewById(R.id.cur_ip);
        curIp.setText(EndPoint.getCurrentIp());
        listConnectedPlayers= (ListView) findViewById(R.id.list_Player);
        listConnectedPlayers.setAdapter(myAdapter);
        btnStart= (Button) findViewById(R.id.btn_start_game);

        mServer.addDataSolver(new EndPoint.DataSolver() {
            @Override
            public void onDataReceived(byte[] bytesReceived) {
                final Bundle receivedData = DataHelper.toObject(bytesReceived, Bundle.CREATOR);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myAdapter.add(receivedData.getString("user ip"));
                    }
                });
            }

            @Override
            public void onNewConnection(final int count) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        curIp.setText(String.valueOf(count));
                    }
                });
            }

            @Override
            public void onConnectFail(String reason) {

            }
        });
    }


    private class MyAdapter extends BaseAdapter {

        ArrayList<String> playersIp = new ArrayList<>();

        public void add(String newPlayerIp) {
            playersIp.add(newPlayerIp);
            notifyDataSetChanged();
        }
        @Override
        public int getCount() {
            return playersIp.size();
        }

        @Override
        public Object getItem(int i) {
            return playersIp.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v = getLayoutInflater().inflate(R.layout.connected_player, viewGroup);
            TextView playerIp = (TextView) v.findViewById(R.id.player_ip);
            playerIp.setText(playersIp.get(i));
            return v;
        }
    }

    @Override
    public void onClick(View view) {
        if ((view.getId()==R.id.btn_start_game)){
            Intent intent = new Intent(HostWaitingActivity.this, GamePlayActivity.class);
            startActivity(intent);
        }
    }
}
