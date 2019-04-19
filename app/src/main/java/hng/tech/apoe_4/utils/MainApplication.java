package hng.tech.apoe_4.utils;

import android.content.ContextWrapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.concurrent.TimeUnit;

import androidx.multidex.MultiDexApplication;
import hng.tech.apoe_4.retrofit.ApiInterface;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainApplication extends MultiDexApplication {

    private static MainApplication instance;
    private static ApiInterface apiInterface;

    public static String BASE_URL = "https://api.apoe4.app/api/v1/";


    Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    static Retrofit mRetrofit;
    OkHttpClient okHttpClient;

    public static ApiInterface getApiInterface() {
        if (apiInterface == null)
            apiInterface = mRetrofit.create(ApiInterface.class);
        return apiInterface;
    }



    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();


        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static MainApplication getInstance() {
        return instance;
    }
}
