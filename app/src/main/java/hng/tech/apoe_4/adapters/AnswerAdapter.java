package hng.tech.apoe_4.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
    private List<AnswerData> answerDataList;
    private Context context;

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


        AnswerData answeData = answerDataList.get(position);

        holder.answerOPtion.setText(answeData.getAnswer());
        holder.answerOPtion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.answerOPtion.setBackgroundResource(R.drawable.buttons_clicked);
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
