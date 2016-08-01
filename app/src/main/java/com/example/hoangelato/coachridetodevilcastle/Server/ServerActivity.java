package com.example.hoangelato.coachridetodevilcastle.Server;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.hoangelato.coachridetodevilcastle.R;

public class ServerActivity extends AppCompatActivity {

    Server server;
    TextView infoip, msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        infoip = (TextView) findViewById(R.id.infoip);
        msg = (TextView) findViewById(R.id.msg);
        server = new Server(this);
        infoip.setText(server.getIpAddress() + ":" + server.getPort());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        server.onDestroy();
    }
}
