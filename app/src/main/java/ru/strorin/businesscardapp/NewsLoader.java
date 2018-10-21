package ru.strorin.businesscardapp;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import ru.strorin.businesscardapp.data.DataUtils;

public class NewsLoader {
    private static final String TAG = NewsLoader.class.getCanonicalName();

    private static final NewsLoader newsInstance = new NewsLoader();
    private Observable observer;

    static NewsLoader getInstance() {
        return newsInstance;
    }

    private NewsLoader() {
        Observable<? extends Long> delay = Observable.interval(2, TimeUnit.SECONDS);
        observer = Observable.zip(
                delay,
                Observable.fromIterable(DataUtils.generateNews()),
                (d, newsItem) -> newsItem)
//                .filter(x -> !adapter.contains(x))
                .doOnNext(item -> Log.d(TAG, Thread.currentThread().toString()))
                .subscribeOn(Schedulers.io());
    }

    private Observable getNews(){
        return observer;
    }
}
