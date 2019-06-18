package com.example.ww;

import android.support.annotation.MainThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.RxThreadFactory;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Retrofit retrofit;
    TranslatedService translatedService;
    CompositeDisposable compositeDisposable;
    SwipeRefreshLayout swipeRefreshLayout;
    EditText editText;
    Translated translatedforMainThread;
    TextView output;

    private final static String BASE_URL = "https://translate.yandex.net/api/v1.5/tr.json/";
    private final static String BASE_URL1 = "https://yesno.wtf/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.input);
        swipeRefreshLayout = findViewById(R.id.swipe);
        output = findViewById(R.id.output);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                translatedService.getText("trnsl.1.1.20190609T144822Z.c9cb1523015a8ec2.d86184d1277e69937239b3456b9d00a7d8c60a3a",editText.getText().toString(),"en-ru")
                        .subscribeOn(Schedulers.io())
                        .subscribe(new SingleObserver<Translated>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onSuccess(Translated translated) {
                                translatedforMainThread = translated;
                                Log.e("hey",translated.getText().get(0).toString());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        output.setText(translatedforMainThread.getText().get(0).toString());
                                        swipeRefreshLayout.setRefreshing(false);
                                    }
                                });
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("hey","err");
                            }
                        });
            }
        });

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        compositeDisposable = new CompositeDisposable();

         translatedService = retrofit.create(TranslatedService.class);


    }
}





