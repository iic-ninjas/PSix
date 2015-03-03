package com.paypal.psix;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by benny on 3/3/15.
 */
public class SetupEventFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setup_event, container);
        addListeners(view);

        return view;
    }

    private void addListeners(View rootView) {
        TextWatcher toggleCreateLinkButtonTextWatcher = createToggleCreateLinkButtonTextWatcher();

        final EditText paymentSumText = (EditText) rootView.findViewById(R.id.editPaymentSum);
        paymentSumText.addTextChangedListener(toggleCreateLinkButtonTextWatcher);

        final EditText paymentReasonText = (EditText) rootView.findViewById(R.id.editPaymentReason);
        paymentReasonText.addTextChangedListener(toggleCreateLinkButtonTextWatcher);
    }

    private void toggleCreateLinkButton(){
        Button createPaymentLink = (Button) getActivity().findViewById(R.id.buttonCreatePaymentLink);
        createPaymentLink.setEnabled(isTextExist(R.id.editPaymentSum) && isTextExist(R.id.editPaymentReason));
    }

    private TextWatcher createToggleCreateLinkButtonTextWatcher(){
        return new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                toggleCreateLinkButton();
            }
        };
    }

    private boolean isTextExist(int id){
        final EditText paymentSum = (EditText) getActivity().findViewById(id);
        return paymentSum.getText().toString().length() > 0;
    }
}
