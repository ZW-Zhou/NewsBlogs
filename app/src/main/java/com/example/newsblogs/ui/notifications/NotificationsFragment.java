package com.example.newsblogs.ui.notifications;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.newsblogs.MainActivity;
import com.example.newsblogs.R;
import com.example.newsblogs.db.UserDatabaseHelper;
import com.example.newsblogs.entity.User;

public class NotificationsFragment extends Fragment {

    TextView myname,mywords;
    User user;
    UserDatabaseHelper userDatabaseHelper;
    String result, words2,words3 ;

    private NotificationsViewModel notificationsViewModel;

//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        notificationsViewModel =
//                ViewModelProviders.of(this).get(NotificationsViewModel.class);
//        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
//        final TextView textView = root.findViewById(R.id.text_notifications);
//        notificationsViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
////        });
//        return root;
//    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LayoutInflater LF = getActivity().getLayoutInflater();

        View rootView = LF.inflate(R.layout.fragment_notifications, container,
                false);
        myname = (TextView) rootView.findViewById(R.id.name_tv);
        mywords= (TextView) rootView.findViewById(R.id.show_words_tv);
        Intent intent = new Intent(getActivity(), MainActivity.class);
        result = getActivity().getIntent().getStringExtra("name");
        myname.setText(result);
        //user = userDatabaseHelper.queryUserByName(result);
        words2 = getActivity().getIntent().getStringExtra("WORDS");
        mywords.setText(words2);
        if(words3!=null){
            mywords.setText(words3);
        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        //userDatabaseHelper  =new UserDatabaseHelper();
        //result = getActivity().getIntent().getStringExtra("name");
        //user = userDatabaseHelper.queryUserByName(result);
        //String words2 = getActivity().getIntent().getStringExtra("WORDS");
        //mywords.setText(words2);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        words3 = ((MainActivity)getActivity()).updateWordsHere();
        //Log.i("zzw",words3);
    }
}