package hng.tech.apoe_4.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("data")
    @Expose
    private Data data;

    private String email;
    private String password;
    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
