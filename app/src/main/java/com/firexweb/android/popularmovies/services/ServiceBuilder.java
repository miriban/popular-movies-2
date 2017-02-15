package com.firexweb.android.popularmovies.services;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.firexweb.android.popularmovies.receivers.NetworkReceiver;

/**
 * Created by wello on 2/14/17.
 */

public class ServiceBuilder
{
    private Context context;
    private NetworkReceiver receiver;
    private Intent intent;

    public ServiceBuilder(Context context)
    {
        this.context = context;
    }

    public ServiceBuilder setIntent(Class<? extends NetworkService> c)
    {
        this.intent = new Intent(this.context,c);
        return this;
    }

    public ServiceBuilder setIntentExtra(String extraKeyIntent,NetworkReceiver receiver)
    {
        this.intent.putExtra(extraKeyIntent,receiver);
        this.receiver = receiver;
        return this;
    }

    public ServiceBuilder setIntentAction(String action)
    {
        this.intent.setAction(action);
        return this;
    }

    public ServiceBuilder setReceiverActivity(NetworkReceiver.Receiver receiver)
    {
        this.receiver.setReceiver(receiver);
        return this;
    }

    public void startService()
    {
        context.startService(intent);
    }


}
