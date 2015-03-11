package com.paypal.psix.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;

/**
 * Created by shay on 3/11/15.
 */
public class ClipboardUtil {

    public static void copy(Activity activity, String tag, String text) {
        ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(activity.CLIPBOARD_SERVICE);
        ClipData clip = android.content.ClipData.newPlainText(tag, text);
        clipboard.setPrimaryClip(clip);
    }
}
