package hng.tech.apoe_4.retrofit.responses;

import java.util.List;

public class QuestionsResponse {
    private String text;
    private List<String> answers;

    public QuestionsResponse(String text, List<String> answers) {
        this.text = text;
        this.answers = answers;
    }

    public QuestionsResponse() {
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }
}
