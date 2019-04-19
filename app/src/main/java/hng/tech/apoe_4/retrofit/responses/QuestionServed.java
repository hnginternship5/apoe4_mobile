package hng.tech.apoe_4.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import hng.tech.apoe_4.models.Question;

public class QuestionServed {

    @SerializedName("question")
    @Expose
    private Question question;
    @SerializedName("error")
    @Expose
    private Boolean error;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

}