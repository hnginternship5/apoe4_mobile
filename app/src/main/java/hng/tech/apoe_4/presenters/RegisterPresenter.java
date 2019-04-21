package hng.tech.apoe_4.presenters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import hng.tech.apoe_4.RegisterActivity;
import hng.tech.apoe_4.activities.DOB_page;
import hng.tech.apoe_4.retrofit.responses.AuthResponse;
import hng.tech.apoe_4.utils.MainApplication;
import hng.tech.apoe_4.views.RegisterView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPresenter {
    Context context;
    RegisterView registerView;

    public RegisterPresenter(Context context, RegisterView registerView) {
        this.context = context;
        this.registerView = registerView;
    }

    public void beginRegistration(String firstName, String lastName, String email, String password){
        registerView.registrationEnd();
        MainApplication.getApiInterface().register(
                firstName,
                lastName,
                email,
                password)
                .enqueue(new Callback<AuthResponse>() {
                    @Override
                    public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                        if (response.isSuccessful()){
                            if (response.body() != null) {
                                registerView.registrationSuccessful();
                                Toast.makeText(context, "Registration Success",
                                        Toast.LENGTH_SHORT).show();

                                Prefs.putString("accessToken", response.body().getAccessToken());
                                Prefs.putString("firstName", firstName);
                                Prefs.putString("lastName", lastName);
                                registerView.registrationSuccessful();
                            }
                        }

                        else {
                            registerView.registrationFail();
                            registerView.toastError(response.message());
                        }
//                        Toast.makeText(RegisterActivity.this,"",
//                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<AuthResponse> call, Throwable t) {
                        registerView.registrationFail();
                        //Todo Logic to handle failure
                    }
                });
    }
}
