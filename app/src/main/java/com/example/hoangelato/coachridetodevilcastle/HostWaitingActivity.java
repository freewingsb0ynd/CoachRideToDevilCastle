package com.example.hoangelato.coachridetodevilcastle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class HostWaitingActivity extends AppCompatActivity implements View.OnClickListener{
    ListView listConnectedPlayers;
    Button btnStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_waiting);
        initView();
    }

    private void initView() {
        listConnectedPlayers= (ListView) findViewById(R.id.list_Player);
        btnStart= (Button) findViewById(R.id.btnCreate);

    }


    @Override
    public void onClick(View view) {
        if ((view.getId()==R.id.btnCreate)){
            Intent intent = new Intent(HostWaitingActivity.this, GamePlayActivity.class);
            startActivity(intent);
        }
    }
}
