package hng.tech.apoe_4.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
    public static final String BASE_URL = "https://api.apoe4.app";
    private static Retrofit mRetrofit;
    public static Retrofit getInstance(){
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            return mRetrofit;
    }
}
