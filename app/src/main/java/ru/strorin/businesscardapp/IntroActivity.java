package ru.strorin.businesscardapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import ru.strorin.businesscardapp.news.NewListActivity;

public class IntroActivity extends AppCompatActivity {
    private final String SHARED_PREF_NAME = "ru.strorin.businesscardapp.INTRO_SHARED_PREF";
    private static final String SHARED_PREF_KEY_INTRO = "INTRO";


    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();

        if (needToShowIntro()) {
            setContentView(R.layout.activity_intro);
            setShowIntroPref(false);
            Disposable disposable = Completable.complete()
                    .delay(3, TimeUnit.SECONDS)
                    .subscribe(this::startSecondActivity);
            compositeDisposable.add(disposable);
        } else {
            setShowIntroPref(true);
            startSecondActivity();
        }
    }

    private boolean needToShowIntro(){
        SharedPreferences sharedPref = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(SHARED_PREF_KEY_INTRO, true);
    }

    private void setShowIntroPref(boolean value){
    SharedPreferences sharedPref = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPref.edit();
       editor.putBoolean(SHARED_PREF_KEY_INTRO, value);
       editor.apply();
    }


    private void startSecondActivity() {
        NewListActivity.start(this);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        compositeDisposable.dispose();
    }

}
