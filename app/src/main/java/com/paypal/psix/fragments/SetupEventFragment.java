package com.paypal.psix.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.Toast;

import com.paypal.psix.R;
import com.paypal.psix.activities.SetupEventActivity;
import com.paypal.psix.activities.ShareActivity;
import com.paypal.psix.models.Event;
import com.paypal.psix.utils.BusProvider;
import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by benny on 3/3/15.
 */
public class SetupEventFragment extends Fragment {

    public class SuccessNotification {
    }

    @InjectView(R.id.event_name_text_view) TextView eventTitle;
    @InjectView(R.id.edit_payment_sum) EditText paymentSumText;
    @InjectView(R.id.edit_payment_reason) EditText paymentReasonText;
    @InjectView(R.id.button_create_paymentLink) Button createPaymentLink;
    @InjectView(R.id.event_header_image) ImageView eventImageHeader;
    @InjectView(R.id.event_header_date) TextView eventDateHeader;
    @InjectView(R.id.event_invitees) TextView eventInviteesDesc;

    Bus bus = BusProvider.getInstance();

    Event event;
    ProgressDialog progress;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setup_event, container);
        ButterKnife.inject(this, view);

        event = ((SetupEventActivity)getActivity()).event;
        eventTitle.setText(event.name);
        eventDateHeader.setText(event.getFormattedDate());

        int count = event.getNumberOfInvitees();
        String quantityString = getResources().getQuantityString(R.plurals.you_invited, count, count);
        eventInviteesDesc.setText(quantityString);
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

    class CreatePaymentLinkTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(getActivity(), getString(R.string.please_wait), getString(R.string.pluging_in));
            createPaymentLink.setFocusableInTouchMode(true);
            createPaymentLink.requestFocus();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // Send request to server
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            progress.dismiss();
            if (result) {
                event.setup();
                bus.post(new SuccessNotification());
                Toast.makeText(getActivity(), getActivity().getString(R.string.paymentes_added), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), ShareActivity.class);
                startActivity(intent);
                getActivity().finish();
            } else {
                Toast.makeText(getActivity(), getActivity().getString(R.string.paymentes_adding_failed), Toast.LENGTH_LONG).show();
            }

        }
    };
}
