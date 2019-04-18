package hng.tech.apoe_4.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;

import androidx.fragment.app.FragmentTransaction;
import hng.tech.apoe_4.R;


public class ResultsFragment extends Fragment {

    private BarChart chart;

    public ResultsFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_results, container, false);

        //here we set the datas for the bar chart
         chart = v.findViewById(R.id.chart);
        ArrayList score = new ArrayList();

        score.add(new BarEntry(10f, 0));
        score.add(new BarEntry(4f, 1));
        score.add(new BarEntry(6f, 2));
        score.add(new BarEntry(8f, 3));
        score.add(new BarEntry(10f, 4));
        score.add(new BarEntry(2f, 5));
        score.add(new BarEntry(10f, 6));


        ArrayList dayAxis = new ArrayList();

        dayAxis.add("SUN");
        dayAxis.add("MON");
        dayAxis.add("TUE");
        dayAxis.add("WED");
        dayAxis.add("THUR");
        dayAxis.add("FRI");
        dayAxis.add("SAT");


        BarDataSet bardataset = new BarDataSet(score, "Score");
        chart.animateY(5000);
        BarData data = new BarData(dayAxis, bardataset);
        bardataset.setColors(R.color.colorAccent);
        chart.setData(data);





        Button button=v.findViewById(R.id.backbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(TodayFragment.newInstance(), "result");
            }
        });
        return v;
    }
    private void openFragment(Fragment fragment, String tag){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment, tag);
        transaction.addToBackStack(fragment.getTag());
        Log.d("TAG","fragment tag: "+fragment.getTag());
        transaction.commit();
    }




    public static ResultsFragment newInstance(){
        return new ResultsFragment();
    }
}
