package com.paypal.psix.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;

import com.paypal.psix.R;
import com.paypal.psix.activities.SetupEventActivity;

/**
 * Created by shay on 3/3/15.
 */
public class ShareDialogFragment extends DialogFragment {

    public static final String TAG = "Share";

    public static void show(FragmentActivity activity) {
        ShareDialogFragment newFragment = new ShareDialogFragment();
        newFragment.show(activity.getSupportFragmentManager(), ShareDialogFragment.TAG);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(getActivity().getLayoutInflater().inflate(R.layout.fragment_share, null))
            .setTitle(R.string.share_payment_link_title)
            .setMessage(getString(R.string.share_instructions))
            .setIcon(android.R.drawable.ic_menu_share)
            .setPositiveButton(getString(R.string.exit_share), new DialogInterface.OnClickListener() {
                // this is actually the negative one - you can't order them :(
                public void onClick(DialogInterface dialog, int id) {
                    finishShareFlow();
                }
            })
            .setNegativeButton(getString(R.string.share_by_post), new DialogInterface.OnClickListener() {
                // this is the positive one
                public void onClick(DialogInterface dialog, int id) {
                    finishShareFlow();
                }
            });
        return builder.create();
    }

    private void finishShareFlow() {
        dismiss();
        if (getActivity().getClass() == SetupEventActivity.class) {
            getActivity().finish();
        }
    }
}