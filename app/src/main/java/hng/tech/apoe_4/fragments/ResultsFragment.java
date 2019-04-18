package hng.tech.apoe_4.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.FragmentTransaction;
import hng.tech.apoe_4.R;


public class ResultsFragment extends Fragment {



    public ResultsFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_results, container, false);

        //here we set the datas for the bar chart

        BarChart barChart = v.findViewById(R.id.chart);

        // the labels that should be drawn on the XAxis
        final String[] quarters = new String[] { "SUN", "MON", "TUE", "WED", "THUR", "FRI", "SAT" };
        IAxisValueFormatter formatter = (value, axis) -> quarters[(int) value];

        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);
        BarData barData = new BarData(getDataSet());
        barChart.setData(barData);
        barChart.animateXY(2000, 2000);
        barChart.invalidate();

        Button button=v.findViewById(R.id.backbutton);
        button.setOnClickListener(v1 -> openFragment(TodayFragment.newInstance(), "result"));
        return v;
    }
    private void openFragment(Fragment fragment, String tag){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment, tag);
        transaction.addToBackStack(fragment.getTag());
        Log.d("TAG","fragment tag: "+fragment.getTag());
        transaction.commit();
    }

    private ArrayList<IBarDataSet> getDataSet() {
        ArrayList<IBarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(0, 10f); // sun
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(1, 10f); // mon
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(2, 4f); // tue
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(3, 4f); // wed
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(4, 2f); // thur
        valueSet1.add(v1e5);
        BarEntry v1e6 = new BarEntry(5, 8f); // fri
        valueSet1.add(v1e6);
        BarEntry v1e7 = new BarEntry(6, 10f); // sat
        valueSet1.add(v1e7);


        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "APO SCORE");
        barDataSet1.setColor(ContextCompat.getColor(getContext(),R.color.figmaBlue));


        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        return dataSets;
    }



    public static ResultsFragment newInstance(){
        return new ResultsFragment();
    }
}
