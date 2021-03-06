package com.wesuck.codekampkonnect.services.codekamp;

import com.wesuck.codekampkonnect.models.AccessToken;
import com.wesuck.codekampkonnect.models.Contact;
import com.wesuck.codekampkonnect.models.ContactDetail;
import com.wesuck.codekampkonnect.models.ListResponse;
import com.wesuck.codekampkonnect.models.SingleAction;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by User on 10-05-2016.
 */
public interface CodeKampServiceInterface {

    @GET("contacts")
    Call<ListResponse<Contact>> listContacts(@Header("Authorization") String accessToken, @Query("page") int page);

    @GET("authenticate")
    Call<AccessToken> login(@Query("email") String email, @Query("password") String password);

    @GET("contacts/{id}")
    Call<ContactDetail> fetchContact(@Header("Authorization") String accessToken, @Path("id") int contactID);

    @GET("actions")
    Call<ListResponse<SingleAction>> fetchActions(@Header("Authorization") String accessToken, @Query("page")int pageNumber, @Query("contact_id")int contactID);

    //@FormUrlEncoded
    //@POST("contacts")
   // Call<Contact> addContact(@Header("Authorization")String accessToken,@Field("first_name") String firstName,@Field("last_name") String lastName,@Field("email") String email,@Field("first_name") String lastName);

}
