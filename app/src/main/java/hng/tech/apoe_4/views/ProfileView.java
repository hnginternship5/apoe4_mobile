package hng.tech.apoe_4.views;

import hng.tech.apoe_4.retrofit.responses.Data;

public interface ProfileView {
    void beginDataFetch();
    void onSuccess(String msg);
    void onError(String msg);
    void finiishProcess();
    void updateUI(Data user);
}
