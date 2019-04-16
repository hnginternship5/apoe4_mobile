package hng.tech.apoe_4.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import hng.tech.apoe_4.R;
import hng.tech.apoe_4.models.AnswerData;
import hng.tech.apoe_4.models.QuestionData;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder> {
    public String day;
    public String night;
    public String plannedActivities;
    public String reminders;
    public  String TITLE;
    public String tit;
    private List<AnswerData> answerDataList;
    private Context context;
    public List<String>answerList = new ArrayList<>();

    public AnswerAdapter( Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public AnswerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer,parent,false);
        return new AnswerAdapter.AnswerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerViewHolder holder, int position) {


        AnswerData answerData = answerDataList.get(position);
        TITLE = answerData.getAnswer();
        Log.d(TAG, "onBindViewHolder: " + TITLE);
        holder.answerOPtion.setText(answerData.getAnswer());
        holder.answerOPtion.setOnClickListener(v -> {
            holder.answerOPtion.setBackgroundResource(R.drawable.buttons_clicked);
            TITLE = holder.answerOPtion.getText().toString();
            Log.d(TAG, "onClick: " + TITLE);
            tit = answerData.getTitle();
            Log.d(TAG, "onClick: " + tit);
            if ("day".equals(tit)) {
                day = answerData.getAnswer();
                Log.d(TAG, "onBindViewHolder: " + "the answer is -> " + day);
                answerList.add(day);
                Prefs.putString("day_answer", day);

            } else if ("night".equals(tit)) {
                night = answerData.getAnswer();
                Log.d(TAG, "onBindViewHolder: " + "the answer is -> " + night);
                answerList.add(night);
                Prefs.putString("night_answer", night);

            } else if ("planned activities".equals(tit)) {
                plannedActivities = answerData.getAnswer();
                Log.d(TAG, "onBindViewHolder: " + "the answer is -> " + plannedActivities);
                answerList.add(plannedActivities);
                Prefs.putString("plannedActivity_answer", plannedActivities);

            } else if ("reminder".equals(tit)) {
                reminders = answerData.getAnswer();
                Log.d(TAG, "onBindViewHolder: " + "the answer is -> " + reminders);
                answerList.add(reminders);
                if (answerData.getAnswer() == "yes") {

                    Prefs.putBoolean("reminders_answer", true);
                } else {
                    Prefs.putBoolean("reminders_answer", false);
                }

            } else {
                Log.d(TAG, "onBindViewHolder: " + "hello world");

            }

        });


    }

    public void setDataList(List<AnswerData> answerDataList){

        this.answerDataList = answerDataList;
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {


        if (answerDataList != null) {
            Log.d(TAG, "getItemCount: size of answer "+answerDataList.size());
            return answerDataList.size();

        }else

        return 0;

    }

    public class AnswerViewHolder extends RecyclerView.ViewHolder {
        TextView answerOPtion;
        public AnswerViewHolder(@NonNull View itemView) {
            super(itemView);

            answerOPtion = itemView.findViewById(R.id.answer_option);
        }
    }
}
