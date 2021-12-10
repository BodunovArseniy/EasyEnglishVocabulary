package com.example.easyenglishvocabulary.Translaters;

public interface Translator {

    public String translate(String text);
    public void setPreRequestCallback(HTTPRequest.Callback preRequestCallback);
    public void setPostRequestCallback(HTTPRequest.Callback preRequestCallback);
}
