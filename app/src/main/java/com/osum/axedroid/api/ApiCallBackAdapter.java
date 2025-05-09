package com.osum.axedroid.api;



import static com.osum.axedroid.api.ServiceGenerator.getApiError;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiCallBackAdapter<T> implements Callback<T> {

    private final ApiCallBack<T> callback;

    public ApiCallBackAdapter(ApiCallBack<T> callback) {
        this.callback = callback;
    }

    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            try {
                callback.onResponse(response.body());
            } catch(NullPointerException ex)
            {
                ex.printStackTrace();
            }
        } else {
            if (response.code() == 504) {
                // HTTP 504 return code is used when the API successfully sent the message but not get a response within the timeout period.
                // It is important to NOT treat this as a failure; the execution status is UNKNOWN and could have been a success.
                return;
            }
            try {
                ApiError apiError = getApiError(response);
                onFailure(call, new ApiException(apiError));
            } catch (IOException e) {
                onFailure(call, new ApiException(e));
            }
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable throwable) {
        if (throwable instanceof ApiException) {
            callback.onFailure(throwable);
        } else {
            try {
                callback.onFailure(new ApiException(throwable));
            }catch (NullPointerException ex)
            {
                ex.printStackTrace();
            }
        }
    }
}
