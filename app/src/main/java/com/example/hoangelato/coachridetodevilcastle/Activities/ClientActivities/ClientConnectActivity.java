package com.example.hoangelato.coachridetodevilcastle.Activities.ClientActivities;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.hoangelato.coachridetodevilcastle.Activities.MainActivity;
import com.example.hoangelato.coachridetodevilcastle.Network.Client;
import com.example.hoangelato.coachridetodevilcastle.Network.NetworkTags;
import com.example.hoangelato.coachridetodevilcastle.Network.Server;
import com.example.hoangelato.coachridetodevilcastle.R;
import com.example.hoangelato.coachridetodevilcastle.databinding.ClientConnectActivityBinding;


/**
 * Created by bloe on 18/08/2016.
 */

public class ClientConnectActivity extends AppCompatActivity {
    public static Client mClient;
    public static Context mContext;
    public static int TIME_TO_WAIT_FOR_CONNECTION = 5000;

    ClientConnectActivityBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        binding = DataBindingUtil.setContentView(this, R.layout.client_connect_activity);

        initClient();
        initView();
    }

    private void initView() {

        binding.buttonConnectToServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "connecting", Toast.LENGTH_LONG).show();
                connectClient();
            }
        });
    }

    private void connectClient() {
        String connectResult = mClient.connectToHost(
                binding.editTextServerIpToConnect.getText().toString(),
                Server.SERVER_PORT,
                TIME_TO_WAIT_FOR_CONNECTION
        );

        Toast.makeText(mContext, connectResult, Toast.LENGTH_LONG).show();

        if (connectResult.equals(Client.CONNECT_SUCCESSFUL)) {
            moveToClientWaitingActivity();
        }
    }

    private void initClient() {
        mClient = new Client(this) {
            @Override
            public void customInitialData(Bundle data) {
                data.putString(NetworkTags.NAME_TAG, MainActivity.USERNAME);
            }
        };
    }

    private void moveToClientWaitingActivity() {
        Intent nextScreen = new Intent(ClientConnectActivity.this, ClientWaitingActivity.class);
        startActivity(nextScreen);
    }
}
