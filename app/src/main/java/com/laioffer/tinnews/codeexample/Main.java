package com.laioffer.tinnews.codeexample;

public class Main {

    public void main() {
        NewsApi newsApi = new NewsApi();
        newsApi.getTopHeadlines().enqueue(new NewsApi.Call.Callback() {
            @Override
            public void onSuccess() {

            }
        });
    }
}
