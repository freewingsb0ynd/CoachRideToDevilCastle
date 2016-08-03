package com.example.hoangelato.coachridetodevilcastle.Client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hoangelato.coachridetodevilcastle.Network.Client;
import com.example.hoangelato.coachridetodevilcastle.Network.EndPoint;
import com.example.hoangelato.coachridetodevilcastle.R;

public class ClientActivity extends AppCompatActivity {
    TextView response;
    EditText editTextAddress, editTextPort;
    Button buttonConnect, buttonClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        editTextAddress = (EditText) findViewById(R.id.addressEditText);
        editTextPort = (EditText) findViewById(R.id.portEditText);
        buttonConnect = (Button) findViewById(R.id.connectButton);
        buttonClear = (Button) findViewById(R.id.clearButton);
        response = (TextView) findViewById(R.id.responseTextView);

        buttonConnect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Client mClient = new Client(
                        editTextAddress.getText().toString()
                        , Integer.parseInt(editTextPort.getText().toString())
                );

                mClient.addDataSolver(new EndPoint.DataSolver() {
                    @Override
                    public void onDataReceived(byte[] data) {

                    }

                    @Override
                    public void onNewConnection(int count) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                response.setText("Connected to server");
                            }
                        });
                    }
                });

                Bundle bundle = new Bundle();
                bundle.putString("user ip", EndPoint.getCurrentIp());
                mClient.dataSender.send(0, bundle);
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                response.setText("");
            }
        });
    }
}