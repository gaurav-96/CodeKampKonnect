package com.wesuck.codekampkonnect;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wesuck.codekampkonnect.models.Contact;
import com.wesuck.codekampkonnect.models.Error;
import com.wesuck.codekampkonnect.models.ListAllContact;
import com.wesuck.codekampkonnect.services.codekamp.Callback;
import com.wesuck.codekampkonnect.services.codekamp.CodeKampServiceDecorator;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 13-05-2016.
 */
public class ListContactsActivity extends CodeKampActivity {

    private String mAccessToken;
    private CustomAdapter mAdapter;
    private List<Contact> mContactsList;
    private int mPageNumberToFetch = 1;
    @BindView(R.id.list_contacts_recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contacts);
        ButterKnife.bind(this);

        mAccessToken = getIntent().getStringExtra(LoginActivity.ACCESS_TOKEN_CONSTANT);
//        mContactsList = new ArrayList<Contact>();



        fetchContactsList(mAccessToken,mPageNumberToFetch);
    }

    private void fetchContactsList(String accessToken,int pageNumber){
        CodeKampServiceDecorator service = new CodeKampServiceDecorator(accessToken);

        service.listAllContacts(pageNumber, new Callback<ListAllContact<Contact>>() {
            @Override
            public void onSuccess(ListAllContact<Contact> response) {
                Toast.makeText(ListContactsActivity.this,"Success!",Toast.LENGTH_SHORT).show();
                mContactsList = response.getData();
                mAdapter = new CustomAdapter(ListContactsActivity.this, mContactsList);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(ListContactsActivity.this));
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Error error) {
                Toast.makeText(ListContactsActivity.this,"Failure!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static Intent getMyIntent(Context context) {
        return new Intent(context, ListContactsActivity.class);
    }

    //View Holder Class
    public class CustomViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.view_holder_custom_text_view)
        TextView mTextView;

        public CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


    //Adapter Class
    public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {

        private List<Contact> dataList;
        private LayoutInflater inflater;

        public CustomAdapter(Context context, List<Contact> dataList) {
            inflater = LayoutInflater.from(context);
            this.dataList = dataList;
        }

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CustomViewHolder(inflater.inflate(R.layout.view_holder_custom, parent,false));
        }

        @Override
        public void onBindViewHolder(CustomViewHolder holder, int position) {
            holder.mTextView.setText(dataList.get(position).getFirstName() + " " + dataList.get(position).getLastName());
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }
    }
}
