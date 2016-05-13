package com.wesuck.codekampkonnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.wesuck.codekampkonnect.models.AccessToken;
import com.wesuck.codekampkonnect.models.Error;
import com.wesuck.codekampkonnect.services.codekamp.Callback;
import com.wesuck.codekampkonnect.services.codekamp.CodeKampServiceDecorator;

public class LoginActivity extends CodeKampActivity {

    public static final String ACCESS_TOKEN_CONSTANT = "access_token_constant";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void loginButtonPressed(View view){

        CodeKampServiceDecorator service = new CodeKampServiceDecorator();
        service.login(new Callback<AccessToken>() {
            @Override
            public void onSuccess(AccessToken response) {

                Intent i = ListContactsActivity.getMyIntent(getApplicationContext());
                i.putExtra(ACCESS_TOKEN_CONSTANT,response.getToken());
                startActivity(i);
            }

            @Override
            public void onFailure(Error error) {
                Toast.makeText(getApplicationContext(),"Login Failed !",Toast.LENGTH_SHORT).show();
            }
        });

    }

}
