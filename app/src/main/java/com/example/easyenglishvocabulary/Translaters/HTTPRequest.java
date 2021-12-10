package com.example.easyenglishvocabulary.Translaters;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class HTTPRequest extends AsyncTask<String, String, String> {

    HttpUriRequest request;
    Callback pre;
    Callback post;

    public HTTPRequest(HttpUriRequest request, Callback pre, Callback post){
        this.request = request;
        this.pre = pre;
        this.post = post;
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            HttpClient httpClient = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();
            return httpClient.execute(request, res);
        } catch (Exception e){
            Log.e("HTTP ERROR", "sendRequest: " + e.getMessage(), e);
            return null;
        }
    }

    @Override
    protected void onPreExecute(){
        pre.handleResult(null);
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String res) {
        post.handleResult(res);
        super.onPostExecute(res);
    }

    public interface Callback{
        public void handleResult(String result);
    }
}

