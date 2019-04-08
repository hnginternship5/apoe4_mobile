package hng.tech.apoe_4.retrofit;

import hng.tech.apoe_4.retrofit.responses.User;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("users/2")
    Call<User> getUser();
}
