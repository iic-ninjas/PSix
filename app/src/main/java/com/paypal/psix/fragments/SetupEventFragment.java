package com.paypal.psix.fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.paypal.psix.R;
import com.paypal.psix.activities.SetupEventActivity;
import com.paypal.psix.models.Event;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by benny on 3/3/15.
 */
public class SetupEventFragment extends Fragment {

    @InjectView(R.id.event_name_text_view) TextView eventTitle;
    @InjectView(R.id.edit_payment_sum) EditText paymentSumText;
    @InjectView(R.id.edit_payment_reason) EditText paymentReasonText;
    @InjectView(R.id.button_create_paymentLink) Button createPaymentLink;
    @InjectView(R.id.event_header_image) ImageView eventImageHeader;
    @InjectView(R.id.event_header_date) TextView eventDateHeader;

    Event event;
    ProgressDialog progress;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setup_event, container);
        ButterKnife.inject(this, view);

        event = ((SetupEventActivity)getActivity()).event;
        eventTitle.setText(event.name);
        eventDateHeader.setText(event.getFormattedDate());
        Picasso.with(getActivity()).load(event.imageURL).into(eventImageHeader);

        return view;
    }

    //region Listeners
    @OnClick(R.id.button_create_paymentLink)
    public void createPaymentLinkClicked(){
        new CreatePaymentLinkTask().execute((Void)null);
    }

    @OnTextChanged(R.id.edit_payment_reason)
    public void paymentChanged(){
        toggleCreateLinkButton();
    }

    @OnTextChanged(R.id.edit_payment_sum)
    public void paymentReasonChanged(){
        toggleCreateLinkButton();
    }
    //endregion

    //region PRIVATE
    private void toggleCreateLinkButton(){
        createPaymentLink.setEnabled(doesTextExist(paymentReasonText) && doesTextExist(paymentSumText));
    }

    private boolean doesTextExist(EditText editText){
        return editText.getText().toString().length() > 0;
    }
    //endregion

    class CreatePaymentLinkTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(getActivity(), getString(R.string.please_wait), getString(R.string.pluging_in));
            createPaymentLink.setFocusableInTouchMode(true);
            createPaymentLink.requestFocus();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Send request to server
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            progress.dismiss();
            createPaymentLink.setFocusableInTouchMode(false);
        }
    };
}
