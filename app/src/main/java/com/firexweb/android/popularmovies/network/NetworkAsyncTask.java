package com.firexweb.android.popularmovies.network;

import android.os.AsyncTask;
import android.util.Log;

import com.firexweb.android.popularmovies.network.networkobjects.NetworkObject;

import java.io.IOException;
import java.net.URL;

/**
 * Created by root on 1/17/17.
 */

public class NetworkAsyncTask extends AsyncTask<String,Void,String>
{
    private final String TAG = NetworkAsyncTask.class.getSimpleName();

    private NetworkObject networkObject;

    public NetworkAsyncTask(NetworkObject networkObject)
    {
        this.networkObject = networkObject;
    }


    @Override
    protected void onPreExecute()
    {
        this.networkObject.onTaskStarted();
    }

    @Override
    protected String doInBackground(String... params)
    {
        if(params.length == 0)
            return null;

        String base_url = params[0];
        URL movies_base_url = NetworkUtility.buildUrl(base_url);
        String result = "";

        Log.d(TAG,"Trying to connect to "+movies_base_url.toString());

        try
        {
            result = NetworkUtility.getResponseFromHTTPUrl(movies_base_url);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(String jsonData)
    {
        this.networkObject.populateResult(jsonData);
    }
}
