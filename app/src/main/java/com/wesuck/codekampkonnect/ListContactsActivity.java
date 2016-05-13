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
import com.wesuck.codekampkonnect.models.ListResponse;
import com.wesuck.codekampkonnect.services.codekamp.Callback;
import com.wesuck.codekampkonnect.services.codekamp.CodeKampServiceDecorator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 13-05-2016.
 */
public class ListContactsActivity extends CodeKampActivity {

    public static String mAccessToken;
    private CustomAdapter mAdapter;
    private List<Contact> mContactsList;
    private int mPageNumberToFetch = 1;

    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    private int visibleThreshold = 1; // The minimum amount of items to have below your current scroll position before loading more.
    int firstVisibleItem, visibleItemCount, totalItemCount;

    private LinearLayoutManager mLayoutManager;
    @BindView(R.id.list_contacts_recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contacts);
        ButterKnife.bind(this);

        mAccessToken = getIntent().getStringExtra(LoginActivity.ACCESS_TOKEN_CONSTANT);
        mContactsList = new ArrayList<Contact>();
        mLayoutManager = new LinearLayoutManager(ListContactsActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CustomAdapter(ListContactsActivity.this, mContactsList);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    // End has been reached

                    // Do something
                    mPageNumberToFetch++;
                    fetchContactsList(mAccessToken, mPageNumberToFetch);
                    loading = true;
                }
            }
        });

        fetchContactsList(mAccessToken, mPageNumberToFetch);

    }

    private void fetchContactsList(String accessToken, int pageNumber) {
        CodeKampServiceDecorator service = new CodeKampServiceDecorator(accessToken);

        service.listAllContacts(pageNumber, new Callback<ListResponse<Contact>>() {
            @Override
            public void onSuccess(ListResponse<Contact> response) {
                mContactsList.addAll(response.getData());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Error error) {
                Toast.makeText(ListContactsActivity.this, "Failure!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static Intent getMyIntent(Context context) {
        return new Intent(context, ListContactsActivity.class);
    }

    //View Holder Class
    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.view_holder_custom_text_view)
        TextView mTextView;

        public int contactID;

        public CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Intent i = ContactDetailActivity.getMyIntent(ListContactsActivity.this);
            i.putExtra(ContactDetailActivity.CONTACT_ID_CONSTANT, contactID);
            startActivity(i);
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
            return new CustomViewHolder(inflater.inflate(R.layout.view_holder_custom, parent, false));
        }

        @Override
        public void onBindViewHolder(CustomViewHolder holder, int position) {
            holder.mTextView.setText(dataList.get(position).getFirstName() + " " + dataList.get(position).getLastName());
            holder.contactID = dataList.get(position).getId();
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }
    }
}
