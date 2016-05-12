package com.wesuck.codekampkonnect.services.codekamp;

import android.util.Log;

import com.wesuck.codekampkonnect.models.Contact;
import com.wesuck.codekampkonnect.models.Error;
import com.wesuck.codekampkonnect.models.ListAllContact;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.wesuck.codekampkonnect.models.*;

import java.io.IOException;
import java.lang.annotation.Annotation;

/**
 * Created by User on 10-05-2016.
 */
public class CodeKampServiceDecorator {

    private final String BASE_URL = "http://api.codekamp.in/";
    private final String BEARER = "bearer";

    private String accessToken;
    private CodeKampServiceInterface codeKampServiceInterface;
    private Retrofit retrofit;

    public CodeKampServiceDecorator(){
        this(null);
    }

    public CodeKampServiceDecorator(String accessToken){
        this.accessToken = accessToken;

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        codeKampServiceInterface = retrofit.create(CodeKampServiceInterface.class);
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    private String getAccessTokenHeader() {
        if(this.accessToken == null) {
            throw new RuntimeException("Access token not set on CodeKampService");
        }
        return BEARER + " " + this.accessToken;
    }

    public void listAllContacts(int pageNumber,Callback<ListAllContact<Contact>> callback) {
        codeKampServiceInterface.listContacts(getAccessTokenHeader(),pageNumber).enqueue(new CallbackHandler<ListAllContact<Contact>>(retrofit,callback){

        });
    }

    public void login(Callback<AccessToken> callback){
        codeKampServiceInterface.login("piyush0mishra@gmail.com","secret").enqueue(new CallbackHandler<AccessToken>(retrofit,callback));
    }

    public void fetchContact(int contactID,Callback<Contact> callback){
        codeKampServiceInterface.fetchContact(getAccessTokenHeader(),contactID).enqueue(new CallbackHandler<Contact>(retrofit,callback));
    }






    private class CallbackHandler<T> implements retrofit2.Callback<T> {

        private Retrofit retrofit;
        private Callback callback;

        public CallbackHandler(Retrofit retrofit, Callback callback) {
            this.retrofit = retrofit;
            this.callback = callback;
        }

        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            if(response.isSuccessful()) {
                callback.onSuccess(response.body());
            } else {
                Log.d("codekamp", "response not isSuccessful");

                Converter<ResponseBody, Error> errorConverter =
                        retrofit.responseBodyConverter(Error.class, new Annotation[0]);
                try {
                    Error error = errorConverter.convert(response.errorBody());
                    callback.onFailure(error);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        public void onFailure(Call<T> call, Throwable t) {
            Log.d("codekamp", "response onFailure");

            Error error = new Error(t.getMessage(), Error.ERROR_CODE_NETWORK_ERROR);

            callback.onFailure(error);
        }
    }
}
