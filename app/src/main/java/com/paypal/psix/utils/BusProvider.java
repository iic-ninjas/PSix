package com.paypal.psix.utils;

import com.squareup.otto.Bus;

/**
 * Created by shay on 3/11/15.
 */
public final class BusProvider {
    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }

    private BusProvider() {
    }
}