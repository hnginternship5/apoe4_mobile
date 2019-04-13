package hng.tech.apoe_4.models;

public class AnswerData {

    private String title;
    private String answer;

    public AnswerData(String title, String answer) {
        this.title = title;
        this.answer = answer;
    }

    public String getTitle() {
        return title;
    }

    public String getAnswer() {
        return answer;
    }
}
