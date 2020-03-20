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

import com.project.rapidline.HomeScreens.Adapter.Listeners.OnItemClickListener;
import com.project.rapidline.Models.ListItems;
import com.project.rapidline.R;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    ArrayList<ListItems> listItems;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public ListAdapter( Context context,ArrayList<ListItems> listItems, OnItemClickListener clickListener) {
        this.listItems = listItems;
        this.context = context;
        this.onItemClickListener = clickListener;
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
        int count=position+1;
        holder.count.setText(""+count);
        holder.strItem.setText(listItems.get(position).getmItemName());

        //To set the click listener to edit and delete
        if(onItemClickListener!=null){
            holder.imageEdit.setOnClickListener(view -> onItemClickListener.onItemClick(listItems.get(position).getmItemId(),"edit"));
            holder.imageDelete.setOnClickListener(view -> onItemClickListener.onItemClick(listItems.get(position).getmItemId(),"delete"));
        }

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
//            imageEdit.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show();
//                }
//            });
//            imageDelete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
//                }
//            });


        }
    }

    public void setListItems(ArrayList<ListItems> listItems) {
        this.listItems = listItems;
        notifyDataSetChanged();
    }


}
