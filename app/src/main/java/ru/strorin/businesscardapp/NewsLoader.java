package ru.strorin.businesscardapp;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.strorin.businesscardapp.data.network.ApiKeyInterceptor;
import ru.strorin.businesscardapp.data.network.endpoints.NewsEndpoint;

public class NewsLoader {
    private static final String TAG = NewsLoader.class.getCanonicalName();

    private static final String URL = "https://api.nytimes.com/";
    private static final String API_KEY = "f81b8c5e62b04c7fbafe07f33d782c5c";

    private static final int TIMEOUT_IN_SECONDS = 2;
    private static NewsLoader sNewsLoader;

    private final NewsEndpoint newsEndpoint;

    public static synchronized NewsLoader getInstance() {
        if (sNewsLoader == null) {
            sNewsLoader = new NewsLoader();
        }
        return sNewsLoader;
    }

    private NewsLoader() {
        final OkHttpClient httpClient = buildOkHttpClient();
        final Retrofit retrofit = buildRetrofitClient(httpClient);

        //init endpoints here. It's can be more then one endpoint
        newsEndpoint = retrofit.create(NewsEndpoint.class);
    }

    @NonNull
    private Retrofit buildRetrofitClient(@NonNull OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @NonNull
    private OkHttpClient buildOkHttpClient() {
        final HttpLoggingInterceptor networkLogInterceptor = new HttpLoggingInterceptor();
        networkLogInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);


        return new OkHttpClient.Builder()
                .addInterceptor(ApiKeyInterceptor.create(API_KEY))
                .addInterceptor(networkLogInterceptor)
                .connectTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .build();
    }

    public NewsEndpoint news() {
        return newsEndpoint;
    }
}
