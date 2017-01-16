package com.vigorous.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vigorous.testandroideverything.R;

public class UpFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_sp, null);
        //Glide.with(getActivity()).load(R.drawable.logo).into((ImageView) contentView.findViewById(R.id.logo));
        ImageView imageView = (ImageView) contentView.findViewById(R.id.logo);
        imageView.setImageResource(R.drawable.logo);
        return contentView;
    }

}
