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

import com.paypal.psix.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by benny on 3/3/15.
 */
public class SetupEventFragment extends Fragment {

    @InjectView(R.id.edit_payment_sum) EditText paymentSumText;
    @InjectView(R.id.edit_payment_reason) EditText paymentReasonText;
    @InjectView(R.id.button_create_paymentLink) Button createPaymentLink;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setup_event, container);
        ButterKnife.inject(this, view);

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
        private ProgressDialog progressDialog;

        private void showProgressDialog(){
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Processing...");
            progressDialog.setMessage("Please wait.");
            progressDialog.setCancelable(true);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }


        @Override
        protected void onPreExecute() {
            showProgressDialog();

        }

        @Override
        protected Void doInBackground(Void... params) {
            // Send request to server
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (progressDialog!=null) {
                progressDialog.dismiss();
            }
        }
    };
}
