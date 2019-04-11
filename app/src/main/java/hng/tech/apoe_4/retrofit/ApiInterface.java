package hng.tech.apoe_4.retrofit;

import hng.tech.apoe_4.retrofit.responses.AuthResponse;
import hng.tech.apoe_4.retrofit.responses.User;
import hng.tech.apoe_4.retrofit.responses.WeatherResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("users/2")
    Call<User> getUser();

    @GET("api/current")

    Call<WeatherResponse> getWeather(@Query("lon") Double lon, @Query("lat") Double lat);

    @POST("auth/register")
    @FormUrlEncoded
    Call<AuthResponse> register(@Field("firstName") String firstName,
                                @Field("lastName") String lastName,
                                @Field("email") String email,
                                @Field("password") String password);
    @POST("auth/login")
    Call<AuthResponse> login(@Body User user);
}
