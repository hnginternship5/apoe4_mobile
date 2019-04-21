package hng.tech.apoe_4.retrofit;

import hng.tech.apoe_4.retrofit.responses.AnswerResponse;
import hng.tech.apoe_4.retrofit.responses.AuthResponse;
import hng.tech.apoe_4.retrofit.responses.DailyResponse;
import hng.tech.apoe_4.retrofit.responses.QuestionServed;
import hng.tech.apoe_4.retrofit.responses.User;
import hng.tech.apoe_4.retrofit.responses.WeatherResponse;
import hng.tech.apoe_4.retrofit.responses.dailyQuestions;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
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

    @POST("questions/daily-question")
    Call<DailyResponse> dailyQ(@Body dailyQuestions questions);

    @POST("questions/getQuestion")
    @FormUrlEncoded
    Call<QuestionServed> getQuestion(@Header ("Authorization") String header,@Field("type") String type);

    @POST("answers/")
    @FormUrlEncoded
    Call<AnswerResponse> sendAnswer(@Header ("Authorization") String header, @Field("question") String questionId, @Field("text") String answerSelected);
}
