package hng.tech.apoe_4.utils;

import hng.tech.apoe_4.retrofit.ApiInterface;

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "https://api.apoe4.app/api/";

    public static ApiInterface getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(ApiInterface.class);
    }
}
