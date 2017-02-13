package com.firexweb.android.popularmovies.services;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v4.content.LocalBroadcastManager;

import com.firexweb.android.popularmovies.data.MovieContract;
import com.firexweb.android.popularmovies.items.Movie;
import com.firexweb.android.popularmovies.receivers.NetworkReceiver;
import com.firexweb.android.popularmovies.utilities.NetworkUtility;
import com.firexweb.android.popularmovies.utilities.JSONUtility;

import java.io.IOException;
import java.net.URL;

/**
 * Created by wello on 2/12/17.
 */

public abstract class NetworkService extends IntentService
{
    public static final int CODE_NETWORK_FINISHED = 1;

    public static final String BUNDLE_URL_KEY = "network-url";

    public NetworkService(String type)
    {
        super(type);
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        String base_url = intent.getAction();
        URL url = NetworkUtility.buildUrl(base_url);
        String result = getDataFromInternet(url);
        insertData(base_url,result);
        notifyUI(intent,base_url);
    }

    private String getDataFromInternet(URL url)
    {
        String result = "";
        try
        {
            result = NetworkUtility.getResponseFromHTTPUrl(url);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        return result;
    }

    public abstract void insertData(String base_url,String result);

    private void notifyUI(Intent intent,String url)
    {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_URL_KEY,url);
        if(!intent.hasExtra(NetworkReceiver.KEY_NETWORK_RECEIVER))
            return;
        final ResultReceiver receiver = intent.getParcelableExtra(NetworkReceiver.KEY_NETWORK_RECEIVER);
        receiver.send(NetworkService.CODE_NETWORK_FINISHED,bundle);
        this.stopSelf();
    }
}
