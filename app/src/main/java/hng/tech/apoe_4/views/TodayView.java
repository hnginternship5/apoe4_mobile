package hng.tech.apoe_4.views;

import hng.tech.apoe_4.models.Question;

public interface TodayView {
    void beginQuestionFetch();
    void onFetchQuestion(Question question);
    void noMoreQuestions(String msg);
    void questionFetchFailed();
    void toastSuccess(String msg);
    void toastError(String msg);
}
