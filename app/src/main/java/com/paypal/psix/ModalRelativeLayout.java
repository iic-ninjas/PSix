package com.paypal.psix;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by benny on 3/4/15.
 */
public class ModalRelativeLayout extends RelativeLayout {

    public ModalRelativeLayout(Context context) {
        super(context);
        setupProgressBar();
    }
    public ModalRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupProgressBar();
    }
    public ModalRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupProgressBar();
    }

    private void setupProgressBar(){
        this.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev){
        return true;
    }
}
