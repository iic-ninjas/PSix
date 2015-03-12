package com.paypal.psix.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.paypal.psix.R;
import com.paypal.psix.activities.SetupEventActivity;
import com.paypal.psix.models.Event;
import com.paypal.psix.services.FacebookService;
import com.paypal.psix.utils.ClipboardUtil;
import com.paypal.psix.utils.HasEvent;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by shay on 3/3/15.
 */
public class ShareDialogFragment extends DialogFragment {

    @InjectView(R.id.share_url) EditText urlTextField;

    Event event;

    public static final String TAG = "Share";

    public static void show(FragmentActivity activity) {
        ShareDialogFragment newFragment = new ShareDialogFragment();
        newFragment.show(activity.getSupportFragmentManager(), ShareDialogFragment.TAG);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_share, null);
        event = ((HasEvent)getActivity()).getEvent();
        builder.setView(view)
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
                   onShare();
                }
            });
        ButterKnife.inject(this, view);

        urlTextField.setText(event.getShareURL());

        return builder.create();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick(R.id.share_copy_button)
    public void onCopy(Button button) {
        ClipboardUtil.copy(getActivity(), getString(R.string.psix_share_url_tag), urlTextField.getText().toString());
        Toast.makeText(getActivity(), getActivity().getString(R.string.share_copied), Toast.LENGTH_SHORT).show();
    }

    private void onShare() {
        FacebookService.postInEvent(event.fbEventId, event.getShareMessage(getActivity()), new Request.Callback() {
            @Override
            public void onCompleted(Response response) {
                finishShareFlow();
            }
        });
//        requestSharePermissions(new Session.StatusCallback() {
//            @Override
//            public void call(Session session, SessionState sessionState, Exception e) {
//
//            }
//        });
    }

    private void finishShareFlow() {
        dismiss();
        if (getActivity().getClass() == SetupEventActivity.class) {
            getActivity().finish();
        }
    }

    private void requestSharePermissions(Session.StatusCallback cb) {
        Session session = Session.getActiveSession();
        if (session != null) {
            Session.NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(
                    this, "publish_actions");
            newPermissionsRequest.setCallback(cb);
            session.addCallback(cb);
            session.requestNewPublishPermissions(newPermissionsRequest);
        }
    }
}