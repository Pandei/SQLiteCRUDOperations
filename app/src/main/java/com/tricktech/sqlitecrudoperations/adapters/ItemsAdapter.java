package com.tricktech.sqlitecrudoperations.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tricktech.sqlitecrudoperations.R;
import com.tricktech.sqlitecrudoperations.callback.ItemClickListener;
import com.tricktech.sqlitecrudoperations.models.Item;

import java.util.List;

/**
 * Created by am on 1/27/2017.
 */

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    public Context mContext;
    public List<Item> itemList;
    public ItemClickListener itemClickListener;
    public static final int EDIT = 0;
    public static final int DELETE = 1;

    public ItemsAdapter(Context mContext, List<Item> itemList, ItemClickListener itemClickListener) {
        this.mContext = mContext;
        this.itemList = itemList;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_item_row,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Item item = itemList.get(position);
        holder.txt_title.setText(item.title);

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView txt_title;
        public ImageView btnEdit,btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_title = (TextView) itemView.findViewById(R.id.txt_title);
            btnEdit = (ImageView) itemView.findViewById(R.id.btnEdit);
            btnDelete = (ImageView) itemView.findViewById(R.id.btnDelete);

            btnEdit.setOnClickListener(this);
            btnDelete.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (view == btnEdit){
                if (itemClickListener != null){
                    int id = itemList.get(getAdapterPosition()).id;
                    itemClickListener.onClick(id, EDIT, itemList.get(getAdapterPosition()).title);
                }
            }else if (view == btnDelete){
                if (itemClickListener != null){
                    int id = itemList.get(getAdapterPosition()).id;
                    itemClickListener.onClick(id, DELETE, null);
                }
            }
        }
    }
}
