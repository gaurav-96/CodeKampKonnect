package com.wesuck.codekampkonnect;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wesuck.codekampkonnect.models.Contact;
import com.wesuck.codekampkonnect.models.ContactDetail;
import com.wesuck.codekampkonnect.models.Error;
import com.wesuck.codekampkonnect.models.ListResponse;
import com.wesuck.codekampkonnect.models.SingleAction;
import com.wesuck.codekampkonnect.services.codekamp.Callback;
import com.wesuck.codekampkonnect.services.codekamp.CodeKampServiceDecorator;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 13-05-2016.
 */
public class ContactDetailActivity extends CodeKampActivity {

    public static final String CONTACT_ID_CONSTANT = "contact_id_constant";
    private CodeKampServiceDecorator service = new CodeKampServiceDecorator(ListContactsActivity.mAccessToken);

    @BindView(R.id.first_name_detail)
    TextView mFirstName;
    @BindView(R.id.last_name_detail)
    TextView mLastName;
    @BindView(R.id.birthday_detail)
    TextView mBirthDay;
    @BindView(R.id.contact_action_list)
    ListView mActionListView;
    private ArrayAdapter mAdapter;
    private ArrayList<String> mActionArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        ButterKnife.bind(this);
        mActionArray = new ArrayList<String>();
        mAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mActionArray);
        mActionListView.setAdapter(mAdapter);

        getDetails(getIntent().getIntExtra(CONTACT_ID_CONSTANT, 0));

    }

    public static Intent getMyIntent(Context context) {
        return new Intent(context, ContactDetailActivity.class);
    }

    private void getDetails(final int contactID) {
        service.fetchContact(contactID, new Callback<ContactDetail>() {
            @Override
            public void onSuccess(ContactDetail response) {

                mFirstName.setText(response.getData().getFirstName());
                mLastName.setText(response.getData().getLastName());
                mBirthDay.setText(response.getData().getBirthday());

                getActions(contactID);

            }

            @Override
            public void onFailure(Error error) {
                Toast.makeText(ContactDetailActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getActions(int contactID) {
        service.fetchActions(1, contactID, new Callback<ListResponse<SingleAction>>() {
            @Override
            public void onSuccess(ListResponse<SingleAction> response) {
                for (int i = 0; i < response.getData().size(); i++) {

                    if (response.getData().get(i).getResponse().getActionableType().equals("Codekamp\\SmsAction")) {
                        mActionArray.add("SMS : " + response.getData().get(i).getSms().getData().getMessage());
                    } else if (response.getData().get(i).getResponse().getActionableType().equals("Codekamp\\CallAction")) {
                        mActionArray.add("CALL : " + Integer.toString(response.getData().get(i).getType().getDuration()));
                    } else if (response.getData().get(i).getEmail() != null) {
                        mActionArray.add("EMAIL : " + response.getData().get(i).getEmail().getData().getSubject());

                    }
                }

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Error error) {

            }
        });
    }
}
