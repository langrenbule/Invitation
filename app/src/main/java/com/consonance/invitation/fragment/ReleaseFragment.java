package com.consonance.invitation.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import com.consonance.invitation.R;
import com.consonance.invitation.RealseActivity;
import com.consonance.invitation.UserDetailActivity;
import com.consonance.invitation.adapter.ImgGridAdapter;

/**
 * 发布
 * Created by Deity on 2016/6/12.
 */
public class ReleaseFragment extends Fragment {
    private Button btn_ok;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agreement,container,false);
        btn_ok = (Button) view.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(BtnOnClickListener);
        return view;
    }

    private View.OnClickListener BtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_ok:
                    Intent realseIntent = new Intent(getActivity(),RealseActivity.class);
//                    Intent realseIntent = new Intent(getActivity(),UserDetailActivity.class);
                    startActivity(realseIntent);
                    break;
            }
        }
    };
}
