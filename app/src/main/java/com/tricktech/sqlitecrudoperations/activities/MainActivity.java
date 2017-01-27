package com.tricktech.sqlitecrudoperations.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tricktech.sqlitecrudoperations.R;
import com.tricktech.sqlitecrudoperations.adapters.ItemsAdapter;
import com.tricktech.sqlitecrudoperations.callback.ItemClickListener;
import com.tricktech.sqlitecrudoperations.dbhelper.DBManager;
import com.tricktech.sqlitecrudoperations.models.Item;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ItemClickListener {

    public EditText edtSay;
    public Button btnAdd, btnEdit;
    public RecyclerView mRecyclerView;
    public List<Item> itemList;
    public ItemsAdapter mAdapter;
    public DBManager mDatabase;
    public static final int EDIT = 0;
    public static final int DELETE = 1;
    int edit_id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtSay = (EditText) findViewById(R.id.edtSay);
        btnAdd = (Button) findViewById(R.id.btnSubmit);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        itemList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mDatabase = new DBManager(this);
        mDatabase.open();
        itemList = mDatabase.getAllItem();
        if (itemList.size() > 0) {
            mAdapter = new ItemsAdapter(this, itemList, this);
            mRecyclerView.setAdapter(mAdapter);
        }
        mDatabase.close();

        btnAdd.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (btnAdd == view) {
            String title = edtSay.getText().toString().trim();
            if (!title.isEmpty()) {
                Item item = new Item();
                item.title = title;
                mDatabase.open();
                mDatabase.insertItem(item);
                mDatabase.close();

                mDatabase.open();
                itemList.clear();
                itemList = mDatabase.getAllItem();
                mDatabase.close();

                if (itemList.size() > 0) {
                    mAdapter = new ItemsAdapter(this, itemList, this);
                    mRecyclerView.setAdapter(mAdapter);
                }
                edtSay.setText("");

            } else {
                Toast.makeText(MainActivity.this, "Please write somthing", Toast.LENGTH_LONG).show();
            }
        } else if (view == btnEdit) {
            String title = edtSay.getText().toString().trim();
            if (!title.isEmpty()) {
                Item item = new Item();
                item.title = title;
                mDatabase.open();
                mDatabase.update(item, edit_id);
                itemList.clear();
                itemList = mDatabase.getAllItem();
                mDatabase.close();


                if (itemList.size() > 0) {
                    mAdapter = new ItemsAdapter(this, itemList, this);
                    mRecyclerView.setAdapter(mAdapter);
                }
                mAdapter.notifyDataSetChanged();
                edtSay.setText("");
            } else {
                Toast.makeText(MainActivity.this, "Please write somthing", Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public void onClick(int id, int TAG, String title) {
        if (TAG == EDIT) {
            edit_id = id;
            edtSay.setText(title);
        }

        if (TAG == DELETE) {
            mDatabase.open();
            mDatabase.remove(id);
            itemList.clear();
            itemList = mDatabase.getAllItem();
            mDatabase.close();

            if (itemList.size() > 0) {
                mAdapter = new ItemsAdapter(this, itemList, this);
                mRecyclerView.setAdapter(mAdapter);
            }
            mAdapter.notifyDataSetChanged();
        }
    }
}
