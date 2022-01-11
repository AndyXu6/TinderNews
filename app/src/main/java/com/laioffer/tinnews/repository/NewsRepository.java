package com.laioffer.tinnews.repository;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.laioffer.tinnews.TinNewsApplication;
import com.laioffer.tinnews.database.TinNewsDatabase;
import com.laioffer.tinnews.model.Article;
import com.laioffer.tinnews.model.NewsResponse;
import com.laioffer.tinnews.network.NewsApi;
import com.laioffer.tinnews.network.RetrofitClient;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("deprecation")
public class NewsRepository {
    private final NewsApi newsApi;
    private final TinNewsDatabase database;

    public NewsRepository() {
        newsApi = RetrofitClient.newInstance().create(NewsApi.class);
        database = TinNewsApplication.getDatabase();
    }

    public LiveData<NewsResponse> getTopHeadlines(String country) {
        MutableLiveData<NewsResponse> topHeadlinesLiveData = new MutableLiveData<>();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<NewsResponse> response = newsApi.getTopHeadlines(country).execute();
                    topHeadlinesLiveData.postValue(response.body());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Response<NewsResponse> response = newsApi.getTopHeadlines(country).execute();
//                    topHeadlinesLiveData.postValue(response.body());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//        newsApi.getTopHeadlines(country).enqueue(new Callback<NewsResponse>() {
//            @Override
//            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
//                if (response.isSuccessful()) {
//                    topHeadlinesLiveData.setValue(response.body());
//                } else {
//                    topHeadlinesLiveData.setValue(null);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<NewsResponse> call, Throwable t) {
//                topHeadlinesLiveData.setValue(null);
//            }
//        });
        return topHeadlinesLiveData;
    }

    public LiveData<NewsResponse> searchNews(String query) {
        MutableLiveData<NewsResponse> everythingLiveData = new MutableLiveData<>();
        newsApi.getEverything(query, 40).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful()) {
                    everythingLiveData.setValue(response.body());
                } else {
                    everythingLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                everythingLiveData.setValue(null);
            }
        });
        return everythingLiveData;
    }

    public LiveData<Boolean> favoriteArticle(Article article) {
        MutableLiveData<Boolean> success = new MutableLiveData<>();
        new FavoriteAsyncTask(database, success).execute(article);
        return success;
    }

    public LiveData<List<Article>> getAllSavedArticles() {
        return database.articleDao().getAllArticles();
    }

    public void deleteSavedArticle(Article article) {
        AsyncTask.execute(() -> database.articleDao().deleteArticle(article));
    }

    private static class FavoriteAsyncTask extends AsyncTask<Article, Void, Boolean> {

        private final TinNewsDatabase database;
        private final MutableLiveData<Boolean> liveData;

        private FavoriteAsyncTask(TinNewsDatabase database, MutableLiveData<Boolean> liveData) {
            this.database = database;
            this.liveData = liveData;
            Log.d("Test", Thread.currentThread().getName() + "0");
        }

        @Override
        protected void onPreExecute() {
            Log.d("Test", Thread.currentThread().getName() + "1");
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Article... articles) {
            Article article = articles[0];
            try {
                Log.d("Test", Thread.currentThread().getName() + "2");
                database.articleDao().saveArticle(article);
            } catch (Exception e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            Log.d("Test", Thread.currentThread().getName() + "3");
            liveData.setValue(success);
        }
    }
}
