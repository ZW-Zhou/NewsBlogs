package com.example.newsblogs.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.newsblogs.MainActivity;
import com.example.newsblogs.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DashboardFragment extends Fragment {

    TextView timeTv;
    private EditText title,content;
    private String strTitle,strContent;
    private DashboardViewModel dashboardViewModel;

//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        dashboardViewModel =
//                ViewModelProviders.of(this).get(DashboardViewModel.class);
//        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
//        final TextView textView = root.findViewById(R.id.text_dashboard);
//        dashboardViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//        return root;
//    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

//        LayoutInflater lf = getActivity().getLayoutInflater();
//        View view =  lf.inflate(R.layout.fragment_dashboard, container, false); //pass the correct layout name for the fragment
//        String d = new Date().toLocaleString()+";";//得到的结果格式：年-月-日 上/下午 时：分：秒
//        //Log.i("zzw",d);
//        timeTv = (TextView)view.findViewById(R.id.create_time_tv);
//        //TextView timeTv = getFragmentManager().findFragmentById(nav_dashboard).getView().findViewById(create_time_tv);
//        timeTv.setText(d);




        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

}