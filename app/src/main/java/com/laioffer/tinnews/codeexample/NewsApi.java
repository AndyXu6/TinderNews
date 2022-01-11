package com.laioffer.tinnews.codeexample;

import android.os.Handler;
import android.os.Looper;

public class NewsApi {

    public Call getTopHeadlines() {
        return new Call();
    }

    public static class Call {
        interface Callback {
            void onSuccess();
        }

        public void enqueue(Callback callBack) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // network request;

                    // move the UI thread
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onSuccess();
                        }
                    });
                }
            }).start();

        }
    }
}
