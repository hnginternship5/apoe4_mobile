package hng.tech.apoe_4.retrofit.responses;

import com.google.gson.annotations.SerializedName;

public class DailyResponse {

     private String status;
     private String message;
     private int code;
    @SerializedName("data")
     private DailyResponseData data;



    public DailyResponse(String status, String message, int code, DailyResponseData data) {
        this.status = status;
        this.message = message;
        this.code = code;
        this.data = data;
    }


    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public DailyResponseData getData() {
        return data;
    }


}
