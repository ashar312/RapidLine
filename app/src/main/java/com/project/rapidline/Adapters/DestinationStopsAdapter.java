package com.project.rapidline.Adapters;

import android.content.Context;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.rapidline.R;

import java.util.ArrayList;

public class DestinationStopsAdapter extends RecyclerView.Adapter<DestinationStopsAdapter.DestinationViewHolder> {

    private Context mCtx;
    private ArrayList<String> stopArrayList;
    private ArrayList<String> arrivalDateArrayList;
    private OnDeliveryDateClickListener mDeliveryDateClickListener;

    public DestinationStopsAdapter(Context mCtx, ArrayList<String> stopArrayList, OnDeliveryDateClickListener mDeliveryDateClickListener) {
        this.mCtx = mCtx;
        this.stopArrayList = stopArrayList;
        this.mDeliveryDateClickListener = mDeliveryDateClickListener;
        arrivalDateArrayList=new ArrayList<>();
    }

    @NonNull
    @Override
    public DestinationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stops_view,
                parent, false);
        return new DestinationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DestinationViewHolder holder, int position) {
        holder.cityName.setText(stopArrayList.get(position));
        holder.arrivaldateTxt.setText(arrivalDateArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return stopArrayList.size();
    }

    class DestinationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Button cityName;
        EditText arrivaldateTxt;
        ImageView removeImg;
        public DestinationViewHolder(@NonNull View itemView) {
            super(itemView);
            cityName=itemView.findViewById(R.id.stop_city_name);
            arrivaldateTxt=itemView.findViewById(R.id.stops_date_txt);
            removeImg=itemView.findViewById(R.id.stop_remove_btn);

            arrivaldateTxt.setInputType(InputType.TYPE_NULL);

            removeImg.setOnClickListener(view -> {
                removeStop(getAdapterPosition());
            });

            arrivaldateTxt.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            mDeliveryDateClickListener.onDateClick(getAdapterPosition());
        }
    }

    private void removeStop(int pos){
        stopArrayList.remove(pos);
        arrivalDateArrayList.remove(pos);
        notifyDataSetChanged();
    }

    public void addStop(String stopName){
        stopArrayList.add(stopName);
        arrivalDateArrayList.add("");
        notifyDataSetChanged();
    }

    public void addDate(String dateTxt,int pos){
        arrivalDateArrayList.set(pos,dateTxt);
        notifyItemChanged(pos);
    }

    public interface OnDeliveryDateClickListener{
        void onDateClick(int position);
    }

    public ArrayList<String> getStopArrayList() {
        return stopArrayList;
    }

    public ArrayList<String> getArrivalDateArrayList() {
        return arrivalDateArrayList;
    }
}
