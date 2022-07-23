package com.example.tictactoe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String [] arr;


    public CustomAdapter(Context context,String [] arr){
        super(context,R.layout.custom,arr);
        this.context = context;
        this.arr = arr;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.custom,null,true);

        TextView fill = (TextView) rootView.findViewById(R.id.text);
        fill.setText(arr[position]);
        return rootView;
    }
}
