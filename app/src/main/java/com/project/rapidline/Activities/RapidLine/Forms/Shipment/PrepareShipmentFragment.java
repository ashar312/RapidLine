package com.project.rapidline.Activities.RapidLine.Forms.Shipment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.project.rapidline.Adapters.ShipmentAdapter;
import com.project.rapidline.Models.RapidLine.Bilty;
import com.project.rapidline.Models.RapidLine.Shipment;
import com.project.rapidline.Models.SaeedSons.Bails;
import com.project.rapidline.R;
import com.project.rapidline.databinding.FragmentPrepareShipmentBinding;
import com.project.rapidline.viewmodel.RapidLine.RapidLineViewModel;
import com.project.rapidline.viewmodel.RapidLine.ShipmentViewModel;

import java.util.ArrayList;
import java.util.List;


public class PrepareShipmentFragment extends Fragment {

    private FragmentPrepareShipmentBinding prepareShipmentBinding;

    private NavController navController;
    private Shipment shipment;
    private ShipmentViewModel shipmentViewModel;
    private ShipmentAdapter shipmentAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        prepareShipmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_prepare_shipment, container, false);
        return prepareShipmentBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            shipment = getArguments().getParcelable("shipmentData");
        }

        shipmentViewModel = ViewModelProviders.of(this).get(ShipmentViewModel.class);

        shipmentViewModel.getListAllBiltyByCity(shipment.getStops()).observe(getViewLifecycleOwner(), bilties -> {
            if(bilties.isEmpty()){
                prepareShipmentBinding.shipmentHint.setVisibility(View.VISIBLE);
            }
            else {
                prepareShipmentBinding.shipmentHint.setVisibility(View.INVISIBLE);
                shipmentAdapter.setBiltyArrayList((ArrayList<Bilty>) bilties);
            }

        });

        shipmentAdapter = new ShipmentAdapter(getContext(), new ArrayList<>());
        prepareShipmentBinding.prepareShipmentRv.setLayoutManager(new LinearLayoutManager(getContext()));
        prepareShipmentBinding.prepareShipmentRv.setAdapter(shipmentAdapter);

        prepareShipmentBinding.prepareShipmentNextBtn.setOnClickListener(view1 -> {

            if (shipmentAdapter.getSelectedBiltyList().isEmpty()) {
                Toast.makeText(getContext(), "Select at least one bilty", Toast.LENGTH_SHORT).show();
                return;
            }

            //Separate bails and biltys
            List<String> bailsList = new ArrayList<>();
            List<String> biltyList = new ArrayList<>();
            double totalWeight = 0;
            for (Bilty currBilty : shipmentAdapter.getSelectedBiltyList()) {

                if (currBilty.getSupplierName().toLowerCase().equals("rapid line")) {
                    bailsList.add(currBilty.getBiltyNo());
                } else {
                    biltyList.add(currBilty.getBiltyNo());
                    totalWeight += currBilty.getWeight();
                }
            }

            shipment.setBails(bailsList);
            shipment.setBiltys(biltyList);
            shipment.setTotalBiltys(shipmentAdapter.getSelectedBiltyList().size());
            shipment.setTotalWeight(totalWeight);

            Bundle shipmentBundle = new Bundle();
            shipmentBundle.putParcelable("shipmentData", shipment);

            navController.navigate(R.id.action_prepareShipmentFragment_to_shipementSummaryFragment, shipmentBundle);
        });

    }
}