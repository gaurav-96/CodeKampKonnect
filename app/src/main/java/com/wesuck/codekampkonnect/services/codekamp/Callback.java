package com.wesuck.codekampkonnect.services.codekamp;

import com.wesuck.codekampkonnect.models.Error;

/**
 * Created by User on 10-05-2016.
 */
public interface Callback<T> {
    void onSuccess(T response);


    void onFailure(Error error);
}
