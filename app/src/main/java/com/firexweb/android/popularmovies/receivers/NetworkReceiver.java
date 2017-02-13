package com.firexweb.android.popularmovies.receivers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

/**
 * Created by wello on 2/12/17.
 */

public class NetworkReceiver extends ResultReceiver
{
    public static final String KEY_NETWORK_RECEIVER = "key-network-receiver";
    private Receiver mReceiver;

    public NetworkReceiver(Handler handler)
    {
        super(handler);
    }

    public void setReceiver(Receiver receiver)
    {
        this.mReceiver = receiver;
    }

    public interface Receiver
    {
        public void onReceiveResult(int resultCode,Bundle resultData);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData)
    {
        if (mReceiver != null) {
            mReceiver.onReceiveResult(resultCode, resultData);
        }
    }
}
