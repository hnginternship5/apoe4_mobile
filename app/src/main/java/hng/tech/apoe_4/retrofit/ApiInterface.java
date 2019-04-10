package hng.tech.apoe_4.retrofit;

import hng.tech.apoe_4.retrofit.responses.User;
import hng.tech.apoe_4.utils.Config;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("users/2")
    Call<User> getUser();

    @POST(Config.REGISTRATION_ROUTE)
    @FormUrlEncoded
    Call<User> register(@Field("firstName") String firstName,
                        @Field("lastName") String lastName,
                        @Field("email") String email,
                        @Field("password") String password);
    @POST(Config.LOGIN_ROUTE)
    @FormUrlEncoded
    Call<User> login(@Field("email") String email,
                     @Field("password") String password);
}
