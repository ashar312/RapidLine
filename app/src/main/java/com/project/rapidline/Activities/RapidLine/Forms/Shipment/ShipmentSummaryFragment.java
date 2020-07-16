package com.project.rapidline.Activities.RapidLine.Forms.Shipment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.project.rapidline.Models.RapidLine.Shipment;
import com.project.rapidline.R;
import com.project.rapidline.databinding.FragmentShipementSummaryBinding;
import com.project.rapidline.viewmodel.RapidLine.ShipmentViewModel;


public class ShipmentSummaryFragment extends Fragment {

    private NavController navController;
    private Shipment shipment;
    private ShipmentViewModel shipmentViewModel;

    private FragmentShipementSummaryBinding shipmentSummaryBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        shipmentSummaryBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_shipement_summary, container, false);
        return shipmentSummaryBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController= Navigation.findNavController(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle=getArguments();
        if(bundle!=null){
            shipment=getArguments().getParcelable("shipmentData");
            Toast.makeText(getContext(),shipment.getDriverName(),Toast.LENGTH_SHORT).show();
        }

        shipmentViewModel = ViewModelProviders.of(this).get(ShipmentViewModel.class);

        //Set data
        //TODO set shipment number
        shipmentSummaryBinding.shipmentNoTxt.setText("000XYZ");
        shipmentSummaryBinding.biltyNoTxt.setText(String.valueOf(shipment.getTotalBiltys()) );
        shipmentSummaryBinding.shipmentWeightTxt.setText(String.valueOf(shipment.getTotalWeight()));
        shipmentSummaryBinding.shipmentDriverTxt.setText(shipment.getDriverName());
        shipmentSummaryBinding.shipmentSidekickTxt.setText(shipment.getSidekickName());
        shipmentSummaryBinding.shipmentExpiryTxt.setText(shipment.getShipmentExpiry());

        for(String stop:shipment.getStops()){
            Chip mChip= (Chip) getLayoutInflater().inflate(R.layout.item_chip_stops,null,false);
            mChip.setChipBackgroundColor(getResources().getColorStateList(R.color.rapidLine));
            mChip.setText(stop);
            shipmentSummaryBinding.stopsChipGroup.addView(mChip);
        }

        shipmentSummaryBinding.confirmShipmentBtn.setOnClickListener(view -> {
            shipmentViewModel.addShipment(shipment);
            Toast.makeText(getContext(),"Added sucessfully",Toast.LENGTH_SHORT).show();
            getActivity().finish();
        });

    }
}