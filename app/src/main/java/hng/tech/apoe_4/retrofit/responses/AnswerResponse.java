package hng.tech.apoe_4.retrofit.responses;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AnswerResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("data")
    @Expose
    private Data data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    class Data {

        @SerializedName("created")
        @Expose
        private String created;
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("question")
        @Expose
        private String question;
        @SerializedName("text")
        @Expose
        private String text;
        @SerializedName("owner")
        @Expose
        private String owner;
        @SerializedName("__v")
        @Expose
        private Integer v;

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public Integer getV() {
            return v;
        }

        public void setV(Integer v) {
            this.v = v;
        }

    }
}


