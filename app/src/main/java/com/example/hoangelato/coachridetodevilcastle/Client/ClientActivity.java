package com.example.hoangelato.coachridetodevilcastle.Client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoangelato.coachridetodevilcastle.Network.Client;
import com.example.hoangelato.coachridetodevilcastle.Network.EndPoint;
import com.example.hoangelato.coachridetodevilcastle.R;

import java.util.ArrayList;
import java.util.Vector;

public class ClientActivity extends AppCompatActivity {

    Client mClient;
    Button connectBtn;
    Button findBtn;
    ListView availServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        initView();
    }

    private void initView() {
        connectBtn = (Button) findViewById(R.id.btn_connect_server);
        findBtn = (Button) findViewById(R.id.btn_find_servers);
        availServer = (ListView) findViewById(R.id.server_list);
        mClient = new Client(this);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, R.layout.avail_server, R.id.server, new Vector<String>()
        );

        availServer.setAdapter(arrayAdapter);


        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayAdapter.clear();
                Log.d("client", "start finding ip");

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mClient.findServersIp(new EndPoint.ProgressListener() {
                            @Override
                            public void onUpdateProgress(final double percentage) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.d("client", "percent " + String.valueOf(percentage));
                                    }
                                });
                            }

                            @Override
                            public void onDone(final Object result) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        arrayAdapter.addAll(((Vector<String>) result));
                                    }
                                });
                                Log.d("client", "done scan for ip");
                            }
                        });
                    }
                }).start();
            }
        });

        availServer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }
}