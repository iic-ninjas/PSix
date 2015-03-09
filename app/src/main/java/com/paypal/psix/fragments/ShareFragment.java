package com.paypal.psix.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paypal.psix.R;

import butterknife.ButterKnife;

/**
 * Created by shay on 3/3/15.
 */
public class ShareFragment extends Fragment {

    public ShareFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_share, container, false);
        ButterKnife.inject(this, rootView);

        return rootView;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}