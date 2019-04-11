package hng.tech.apoe_4.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hng.tech.apoe_4.R;


public class ResultsFragment extends Fragment {

    public ResultsFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_results, container, false);
    }

    public static ResultsFragment newInstance(){
        return new ResultsFragment();
    }
}
