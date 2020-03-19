package com.project.rapidline.HomeScreens.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.rapidline.Models.ListItems;
import com.project.rapidline.R;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    ArrayList<ListItems> listItems;
    private Context context;


    public ListAdapter(Context context,ArrayList<ListItems> listItems){
        this.context = context;
        this.listItems = listItems;
    }
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list_view,
                parent, false);
        return new ListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
//        holder.Count.setText(listItems.get(position).getCount());
//        holder.StrItem.setText(listItems.get(position).getItem());
        holder.count.setText(position+1);
        holder.strItem.setText(listItems.get(position).getmItemName());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder{
        TextView count, strItem;
        ImageView imageEdit, imageDelete;

        public ListViewHolder(View view) {
            super(view);
            count = view.findViewById(R.id.Count);
            strItem = view.findViewById(R.id.itemstr);
            imageEdit = view.findViewById(R.id.editimage);
            imageDelete = view.findViewById(R.id.deleteimage);
            imageEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show();
                }
            });
            imageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void setListItems(ArrayList<ListItems> listItems) {
        this.listItems = listItems;
        notifyDataSetChanged();
    }

}
