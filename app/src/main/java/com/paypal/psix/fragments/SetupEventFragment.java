package com.paypal.psix.fragments;

import android.app.ProgressDialog;
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
import com.paypal.psix.models.Event;
import com.paypal.psix.services.EventSyncService;
import com.paypal.psix.services.ParseInterface;
import com.paypal.psix.utils.BusProvider;
import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

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

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick(R.id.button_create_paymentLink)
    public void createPaymentLinkClicked() {
        event.amountPerUser = Integer.parseInt(paymentSumText.getText().toString());
        event.paymentDescription = paymentReasonText.getText().toString();

        progress = ProgressDialog.show(getActivity(), getString(R.string.please_wait), getString(R.string.pluging_in));
        createPaymentLink.setFocusableInTouchMode(true);
        createPaymentLink.requestFocus();

        EventSyncService.pluginEvent(event, new Callback<ParseInterface.ParseEvent>() {
            @Override
            public void success(ParseInterface.ParseEvent parseEvent, Response response) {
                progress.dismiss();
                event.setup();
                bus.post(new SuccessNotification());
                ShareDialogFragment.show(getActivity());
            }

            @Override
            public void failure(RetrofitError error) {
                progress.dismiss();
                Toast.makeText(getActivity(), getActivity().getString(R.string.paymentes_adding_failed), Toast.LENGTH_LONG).show();
            }
        });
    }

    @OnTextChanged(R.id.edit_payment_reason)
    public void paymentChanged(){
        toggleCreateLinkButton();
    }

    @OnTextChanged(R.id.edit_payment_sum)
    public void paymentReasonChanged(){
        toggleCreateLinkButton();
    }

    private void toggleCreateLinkButton(){
        createPaymentLink.setEnabled(doesTextExist(paymentReasonText) && doesTextExist(paymentSumText));
    }

    private boolean doesTextExist(EditText editText){
        return editText.getText().toString().length() > 0;
    }
}
