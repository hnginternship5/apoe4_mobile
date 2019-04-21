package hng.tech.apoe_4.presenters;

import android.content.Context;
import android.util.Log;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.pixplicity.easyprefs.library.Prefs;

import hng.tech.apoe_4.R;
import hng.tech.apoe_4.retrofit.responses.AnswerResponse;
import hng.tech.apoe_4.retrofit.responses.QuestionServed;
import hng.tech.apoe_4.utils.CONSTANTS;
import hng.tech.apoe_4.utils.MainApplication;
import hng.tech.apoe_4.views.TodayView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodayPresenter {
    private Context context;
    private TodayView todayView;

    public TodayPresenter(Context context, TodayView todayView) {
        this.context = context;
        this.todayView = todayView;
    }

    public void fetchQuestion(){
        todayView.beginQuestionFetch();
        MainApplication.getApiInterface().getQuestion(Prefs.getString("accessToken", ""),
                CONSTANTS.getTimeOfDay()).enqueue(new Callback<QuestionServed>() {
            @Override
            public void onResponse(Call<QuestionServed> call, Response<QuestionServed> response) {
                Log.e("Status", String.valueOf(response.code()));
                if (response.isSuccessful()){
                    assert  response.body() != null;


//                    questionsLayout.animate()
//                            .translationX(0)
//                            .alpha(1.0f).setListener(null);
                    QuestionServed questionServed = response.body();
                    if (questionServed.getStatus() == 0){
                        todayView.onFetchQuestion(questionServed.getQuestion());
                    }
                    else if (questionServed.getStatus() == 1){
                        todayView.noMoreQuestions(questionServed.getMsg());
                    }
                } else if (response.code() == 300){
                    todayView.noMoreQuestions("Seems You have answered all questions for now... ^_^");
                }
            }

            @Override
            public void onFailure(Call<QuestionServed> call, Throwable t) {
//                questionsLayout.animate()
//                        .translationX(0)
//                        .alpha(1.0f).setListener(null);
////                QuestionServed questionServed = response.body();
                todayView.questionFetchFailed();
            }
        });
    }

    public void sendAnswer(String questionId, String answer){
        MainApplication.getApiInterface().sendAnswer(Prefs.getString("accessToken", ""),questionId, answer).enqueue(new Callback<AnswerResponse>() {
            @Override
            public void onResponse(Call<AnswerResponse> call, Response<AnswerResponse> response) {
                if (response.isSuccessful());
                assert  response.body() != null;
                AnswerResponse answerResponse = response.body();


                String questionType = CONSTANTS.getTimeOfDay();
//                getQuestion(questionType);
                fetchQuestion();
            }

            @Override
            public void onFailure(Call<AnswerResponse> call, Throwable t) {

            }
        });
    }
}
