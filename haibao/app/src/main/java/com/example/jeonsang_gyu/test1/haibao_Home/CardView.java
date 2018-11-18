package com.example.jeonsang_gyu.test1.haibao_Home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jeonsang_gyu.test1.Post.Poster;
import com.example.jeonsang_gyu.test1.R;

import java.util.ArrayList;

public class CardView extends Fragment {

    private RecyclerView mRecyclerView;


    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;









    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_card_view, null);
        TextView date_T=(TextView)view.findViewById(R.id.Date_T);



        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        Bundle bundle=getArguments();
        final ArrayList<? extends Poster> posters=  bundle.getParcelableArrayList("Object");

        String setDates=bundle.getString("Dates");
        date_T.setText(setDates);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(this.getActivity(),this.getContext(),(ArrayList<Poster>) posters);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }





}
