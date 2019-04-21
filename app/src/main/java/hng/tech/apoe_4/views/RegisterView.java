package hng.tech.apoe_4.views;

public interface RegisterView {
    void toastSuccess(String msg);
    void toastError(String msg);
    void beginRegistration();
    void registrationEnd();
    void registrationSuccessful();
    void registrationFail();
}
