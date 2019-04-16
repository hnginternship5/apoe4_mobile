package hng.tech.apoe_4.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.FragmentTransaction;
import hng.tech.apoe_4.R;


public class ResultsFragment extends Fragment {

    public ResultsFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_results, container, false);

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
