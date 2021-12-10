package com.example.easyenglishvocabulary.Translaters;

import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class MicrosoftTranslator implements Translator{

    static final String language = "ru";

    private HTTPRequest.Callback preRequestCallback;
    private HTTPRequest.Callback postRequestCallback;

    @Override
    public void setPreRequestCallback(HTTPRequest.Callback preRequestCallback) {
        this.preRequestCallback = preRequestCallback;
    }

    @Override
    public void setPostRequestCallback(HTTPRequest.Callback postRequestCallback) {
        this.postRequestCallback = postRequestCallback;
    }

    @Override
    public String translate(String text) {
        return getTranslate(text, language);
    }

    private String getTranslate(String text, String to) {
        HttpPost request = new HttpPost(URI.create("https://microsoft-translator-text.p.rapidapi.com/translate?to=" + to + "&api-version=3.0&profanityAction=NoAction&textType=plain"));
        String response = null;
         try {
             request.setHeader("content-type", "application/json");
             request.setHeader("x-rapidapi-host", "microsoft-translator-text.p.rapidapi.com");
             request.setHeader("x-rapidapi-key", yourkey);
             request.setEntity(new StringEntity("[\r{\r\"Text\": \"" + text + "\"\r}\r]"));

             new HTTPRequest(request, preRequestCallback,
                     new HTTPRequest.Callback() {
                         @Override
                         public void handleResult(String result) {
                             String text = parseResponse(result);
                             postRequestCallback.handleResult(text);
                         }
                     }).execute();

         } catch (Exception e){
             Log.e("HTTP ERROR", "getTranslate: " + e.getMessage(), e);
         }

        return response == null ? "Ошибка перевода: запрос не отправлен" : parseResponse(response);
    }

    private String parseResponse(String response){
        try {
            JsonObject jsonObject = (new JsonParser()).parse(response).getAsJsonArray().get(0).getAsJsonObject();
            return jsonObject.get("translations").getAsJsonArray().get(0).getAsJsonObject().get("text").getAsString();
        } catch (Exception e){
            return "Ошибка перевода: ответ не получен";
        }
    }
}
