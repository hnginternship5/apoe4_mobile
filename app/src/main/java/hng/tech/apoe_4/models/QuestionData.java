package hng.tech.apoe_4.models;

public class QuestionData {

    private String qTitle;
    private String qAnswers;

    public QuestionData(String qTitle, String qAnswers) {
        this.qTitle = qTitle;
        this.qAnswers = qAnswers;
    }

    public String getqTitle() {
        return qTitle;
    }

    public String getqAnswers() {
        return qAnswers;
    }
}
