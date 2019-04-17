package hng.tech.apoe_4.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import hng.tech.apoe_4.R;
import hng.tech.apoe_4.models.AnswerData;
import hng.tech.apoe_4.models.QuestionData;
import hng.tech.apoe_4.utils.DataUtil;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private List<QuestionData> questionDataList;
    LayoutInflater layoutInflater;
    private Context context;
    private   List<List<AnswerData>> answerDataList;


    public QuestionAdapter(Context context ) {

        this.context = context;

    }

    @NonNull
    @Override
    public QuestionAdapter.QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.question,parent,false);
        return new QuestionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionAdapter.QuestionViewHolder holder, int position) {
        if(questionDataList != null){
            final QuestionData list = questionDataList.get(position);
            holder.question.setText(list.getqTitle());

           List<AnswerData> data = showAnswers(list.getqAnswers());
                //Log.d(TAG, "onBindViewHolder: answerDataList "+ q.toString());




                holder.answerAdapter.setDataList(data);







        }
    }

    //list of each answer
    private List<AnswerData> showAnswers(String qAnswer) {

        Log.d("TAG", "showAnswers: " + qAnswer);

        String arrayName = "{ qAnswers: " + qAnswer + "}";
        return DataUtil.loadAnswers(arrayName);


    }


    public void setQuestionDataList(List<QuestionData> dataList){

        this.questionDataList = dataList;
        notifyDataSetChanged();
    }

    public void setAnswerList(List<List<AnswerData>>  answerDataList){

        this.answerDataList = answerDataList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {

        if(questionDataList != null) {
            return questionDataList.size();
        }else{

            return 0;
        }
    }


        class QuestionViewHolder extends RecyclerView.ViewHolder {
                TextView question;
                RecyclerView answers;
                AnswerAdapter answerAdapter;
            @SuppressLint("WrongConstant")
            public QuestionViewHolder(@NonNull View itemView) {
                super(itemView);
                question = itemView.findViewById(R.id.question_title);
                answers = itemView.findViewById(R.id.answers_view);
                answers.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.
                        VERTICAL, false));
                answerAdapter = new AnswerAdapter(context);
                answers.setAdapter(answerAdapter);
            }
        }
}
