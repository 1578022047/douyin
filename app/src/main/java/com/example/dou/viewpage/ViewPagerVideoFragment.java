package com.example.dou.viewpage;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dou.MainActivity;
import com.example.dou.R;

public class ViewPagerVideoFragment extends AddMethodFragment {

    private String title;
    private String content;
    private View view;

    private final static String TAG = "ViewPagerVideoFragment";


    public ViewPagerVideoFragment(final String title, final String content) {
        this.title = title;
        this.content = content;
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view_pager,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView textView = ((MainActivity)getActivity()).findViewById(R.id.text);
        if (textView==null){
            Log.e(TAG,"失败");
        }
        else {
            Log.e(TAG,textView.getText().toString());
        }
        textView.setText(content);
    }

    @Override
    public String getTitle() {
        return title;
    }
}