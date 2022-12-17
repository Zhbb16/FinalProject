package com.example.finalproject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class DetailsFragmentActivity extends Fragment {

    private Bundle dataFromActivity;
    private long id;
    private AppCompatActivity parentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dataFromActivity = getArguments();

        // Inflate the layout for this fragment
        View result =  inflater.inflate(R.layout.fragment_details, container, false);

        //show the characters info:
        TextView nameView = (TextView)result.findViewById(R.id.title_content);
        nameView.setText(dataFromActivity.getString(MenuActivity.TITLE));

        TextView heightView = (TextView)result.findViewById(R.id.URL_content);
        heightView.setText(dataFromActivity.getString(MenuActivity.URL));

        TextView massView = (TextView)result.findViewById(R.id.section_content);
        massView.setText(dataFromActivity.getString(MenuActivity.SECTION));

        return result;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

//        When the fragment has been added to the Activity which has the FrameLayout.
        //context will either be Favorite for a tablet, or EmptyActivity for phone
        parentActivity = (AppCompatActivity)context;
    }
}
