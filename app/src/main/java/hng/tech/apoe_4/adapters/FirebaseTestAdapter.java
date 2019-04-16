package hng.tech.apoe_4.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import hng.tech.apoe_4.R;
import hng.tech.apoe_4.activities.firebaseTest;

public class FirebaseTestAdapter extends RecyclerView.Adapter<FirebaseTestAdapter.ViewHolder> {

    private firebaseTest[] myfirebaseTest;

    // RecyclerView recyclerView;
    public FirebaseTestAdapter(firebaseTest[] firebaseTest) {
        this.myfirebaseTest = firebaseTest;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.firebase_test, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final firebaseTest firebaseTest = myfirebaseTest[position];
        holder.imageView.setImageResource(myfirebaseTest[position].getImgId());
        holder.textView1.setText(myfirebaseTest[position].getQuestionTitle());
        holder.textView2.setText(myfirebaseTest[position].getAnswerOptions());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Works",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return myfirebaseTest.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView1;
        public TextView textView2;
        public ConstraintLayout constraintLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.imageView6);
            this.textView1 = (TextView) itemView.findViewById(R.id.question_title);
            this.textView2= (TextView) itemView.findViewById(R.id.answer_option);
            constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.firebase_test);
        }
    }

}

