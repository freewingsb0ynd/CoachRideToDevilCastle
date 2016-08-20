package com.example.hoangelato.coachridetodevilcastle.Network;

import android.os.Bundle;

/**
 * Created by bloe on 07/08/2016.
 */

public abstract class EventListener {
    public boolean listen = true;
    public abstract void onDataReceived(final Bundle data, final Connection connection);
    public abstract void onNewConnection(final Connection connection);
    public abstract void onConnectFail(final String reason, final String destinationIp);
    public abstract void onInitialDataReceived(final Bundle data, final Connection connection);
    public abstract void onDisconnected(final Bundle message, final Connection connection);
    public abstract void onLosingConnection(final Connection connection);
    public void removeListener() {
        listen = false;
    }
}
