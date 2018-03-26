package com.example.pollyplyim.androidfinalproject;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by pollyplyim on 2018-03-04.
 */

public class MCListFragment extends ListFragment {
    private ArrayList<MC> mcs;
    private MCAdapter mcAdapter;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.mc_title);
        mcs=CentralMC.getInstance(getActivity()).getMcs();
        mcAdapter =new MCAdapter(getContext());

    }

    private class MCAdapter extends ArrayAdapter<String>{
        public MCAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount(){
            return mcs.size()*2;
        }

        public String getItem(int position){
            return mcs.get(position).getQuestion();
        }

        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = (LayoutInflater)
                    getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = null ;

            if(position%2==0) {
                v = inflater.inflate(R.layout.question_layout, null);
            }
            else{
                if(mcs.get(position-1).getCorrectChoice()!=null)
                    v = inflater.inflate(R.layout.choice_layout, null);
                else if(mcs.get(position-1).getCorrectTF()!=null)
                    v = inflater.inflate(R.layout.true_false_layout, null);
                else if(mcs.get(position-1).getCorrectTextInput()!=null)
                    v = inflater.inflate(R.layout.text_input_layout,null);
                else if (mcs.get(position-1).getCorrectNumInput()!=null)
                    v= inflater.inflate(R.layout.float_input_layout,null);
            }

            return v;
        }

        public long getId(int position){
            return position;
        }

        }
}
