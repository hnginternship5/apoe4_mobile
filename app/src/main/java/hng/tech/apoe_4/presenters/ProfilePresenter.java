package hng.tech.apoe_4.presenters;

import hng.tech.apoe_4.retrofit.responses.Data;
import hng.tech.apoe_4.retrofit.responses.User;
import hng.tech.apoe_4.utils.MainApplication;
import hng.tech.apoe_4.views.ProfileView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePresenter {

    ProfileView profileView;

    public ProfilePresenter(ProfileView profileView) {
        this.profileView = profileView;
    }

    public void fetchData(){
        profileView.beginDataFetch();
        profileView.beginDataFetch();
        MainApplication.getApiInterface().getUser().enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                profileView.finiishProcess();
                if (response.isSuccessful()){
                    profileView.onSuccess("success");
                    if (response.body() != null) {
                        Data user = response.body().getData();

                        profileView.updateUI(user);
                    }
                } else {
                    profileView.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                profileView.finiishProcess();
                profileView.onError(t.getMessage());
            }
        });
    }
}
