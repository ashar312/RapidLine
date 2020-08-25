package com.project.rapidline.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.project.rapidline.Models.SaeedSons.Bails;
import com.project.rapidline.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BailsAdapter extends RecyclerView.Adapter<BailsAdapter.EnterieViewHolder> implements Filterable {

    private ArrayList<Bails> bailsArrayList;
    private Context mCtx;
    private List<Bails> bailCopyList;
    private OnItemClickListener onItemClickListener;
    private OnNoteListener mOnNoteListener;


    public BailsAdapter(Context mCtx, ArrayList<Bails> bailsArrayList, OnItemClickListener clickListener, OnNoteListener onNoteListener) {
        this.bailsArrayList = bailsArrayList;
        this.mCtx = mCtx;
        this.onItemClickListener = clickListener;
        bailCopyList = new ArrayList<>(this.bailsArrayList);
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public EnterieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cell_register,
                parent, false);
        return new EnterieViewHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull EnterieViewHolder holder, int position) {
        holder.builty_no.setText(bailsArrayList.get(position).getBailNo());
        holder.sender_txt.setText(bailsArrayList.get(position).getSenderId());
        holder.receiver_txt.setText(bailsArrayList.get(position).getReceiverId());
        holder.name_txt.setText(bailsArrayList.get(position).getMadeBy());
        holder.date_txt.setText(formattedDate(bailsArrayList.get(position).getMadeDateTime()));
        holder.fromCity.setText(bailsArrayList.get(position).getFromCity());
        holder.toCity.setText(bailsArrayList.get(position).getToCity());

//        //Set click listeners for print
//        if (onItemClickListener != null) {
//            holder.print_btn.setOnClickListener(view -> onItemClickListener.onItemClick(bailsArrayList.get(position).getBiltyNo(), "print"));
//        }

    }

    private String formattedDate(Date mydate) {
        DateFormat dateFormat = DateFormat.getDateInstance();
        return dateFormat.format(mydate);
    }

    @Override
    public int getItemCount() {
        return bailsArrayList.size();
    }


    public class EnterieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView sender_txt, receiver_txt, date_txt, builty_no, name_txt,fromCity,toCity;
        Button print_btn;
        OnNoteListener onNoteListener;

        public EnterieViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            this.onNoteListener = onNoteListener;
            sender_txt = itemView.findViewById(R.id.sender_name);
            receiver_txt = itemView.findViewById(R.id.receiver_name);
            builty_no = itemView.findViewById(R.id.builty_no);
            date_txt = itemView.findViewById(R.id.date_txt);
            name_txt = itemView.findViewById(R.id.name_txt);
//            print_btn = itemView.findViewById(R.id.print_btn);
            fromCity=itemView.findViewById(R.id.senderCity);
            toCity=itemView.findViewById(R.id.receiverCity);
            itemView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onBailClick(bailsArrayList.get(getAdapterPosition()).getBailNo());
        }
    }

    public interface OnNoteListener {
        void onBailClick(String itemId);
    }

    public void setBailsArrayList(ArrayList<Bails> bailsArrayList) {
        this.bailsArrayList = bailsArrayList;
        bailCopyList = new ArrayList<>(this.bailsArrayList);
        notifyDataSetChanged();
    }

    public ArrayList<Bails> getBailsArrayList() {
        return bailsArrayList;
    }

    @Override
    public Filter getFilter() {
        return myFilter;
    }

    private Filter myFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<Bails> filtredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filtredList.addAll(bailCopyList);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (Bails bailList : bailCopyList) {
                    if (bailList.getBailNo().toLowerCase().contains(filterPattern)) {
                        filtredList.add(bailList);
                    }
                }

            }

            FilterResults results = new FilterResults();
            results.values = filtredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            bailsArrayList.clear();
            bailsArrayList.addAll((Collection<? extends Bails>) filterResults.values);
            notifyDataSetChanged();
        }
    };

}
