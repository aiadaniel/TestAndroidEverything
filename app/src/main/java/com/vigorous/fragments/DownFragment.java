package com.vigorous.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vigorous.testandroideverything.R;

public class DownFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_de, null);
        //Glide.with(getActivity()).load(R.drawable.one).into((ImageView) contentView.findViewById(R.id.detail));
        ImageView imageView = (ImageView) contentView.findViewById(R.id.detail);

        imageView.setImageResource(R.drawable.one);//big image,OOM!!! use largeheap ,but not the final resolution
        return contentView;
    }
}
