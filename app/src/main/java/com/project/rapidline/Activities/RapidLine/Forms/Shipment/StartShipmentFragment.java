package com.project.rapidline.Activities.RapidLine.Forms.Shipment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.project.rapidline.Activities.RapidLine.Forms.FitnessTest;
import com.project.rapidline.Adapters.DestinationStopsAdapter;
import com.project.rapidline.Models.RapidLine.Drivers;
import com.project.rapidline.Models.RapidLine.Shipment;
import com.project.rapidline.Models.RapidLine.SideKick;
import com.project.rapidline.Models.RapidLine.Vechile;
import com.project.rapidline.R;
import com.project.rapidline.databinding.FragmentStartShipmentBinding;
import com.project.rapidline.viewmodel.RapidLine.RapidLineViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class StartShipmentFragment extends Fragment implements DestinationStopsAdapter.OnDeliveryDateClickListener {

    private FragmentStartShipmentBinding fragmentStartShipmentBinding;
    private RapidLineViewModel rapidLineViewModel;

    private NavController navController;
    private List<String> cityList = new ArrayList<>();
    //Stops adapter
    private SimpleDateFormat mSimpleDateFormat;
    private Calendar mCalendar;
    private DestinationStopsAdapter destinationStopsAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentStartShipmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_start_shipment, container, false);
        return fragmentStartShipmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mSimpleDateFormat = new SimpleDateFormat("MM/dd/yyyy h:mm a", Locale.getDefault());
        rapidLineViewModel = ViewModelProviders.of(this).get(RapidLineViewModel.class);

        initializeSpinnerData();

        //Retake maintainance test button
        fragmentStartShipmentBinding.vehileMaintainanceTestBtn.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), FitnessTest.class));
        });


        //Move to next screen
        fragmentStartShipmentBinding.startShipmentNextBtn.setOnClickListener(view1 -> {
            if (!isFieldEmpty()) {
                Vechile selectedVechile= (Vechile) fragmentStartShipmentBinding.shipmentVechileSpinner.getSelectedItem();
                if(selectedVechile.getFitnessPercentage()<80){
                    Toast.makeText(getContext(),"Vechile not fit\n Please select another vechile",Toast.LENGTH_SHORT).show();
                    return;
                }

                //check for empty
                Shipment shipment = new Shipment();
                shipment.setVechileNo(fragmentStartShipmentBinding.shipmentVechileSpinner.getSelectedItem().toString());
                shipment.setDriverName(fragmentStartShipmentBinding.shipmentDriverSpinner.getSelectedItem().toString());
                shipment.setSidekickName(fragmentStartShipmentBinding.shipmentSideKickSpinner.getSelectedItem().toString());
                shipment.setShipmentExpiry(fragmentStartShipmentBinding.sessionExpiryDateTxt.getText().toString());
//                List<String> citylisttt=new ArrayList<>();
//                citylisttt.add("Lahores");
                shipment.setStops(destinationStopsAdapter.getStopArrayList());
//                shipment.setStops(citylisttt);
                shipment.setStopsArrivalDate(destinationStopsAdapter.getArrivalDateArrayList());

                Bundle bundle = new Bundle();
                bundle.putParcelable("shipmentData", shipment);

                navController.navigate(R.id.action_startShipmentFragment_to_prepareShipmentFragment, bundle);
            }

        });

//        ConstraintSet set = new ConstraintSet();
//        set.clone(fragmentStartShipmentBinding.stopsConstraintLayout);
//
//
//        //Button 1:
//        Button button = new Button(getContext());
//        button.setText("Gujranwala");
//        stopsViewIds.add(View.generateViewId());
//        button.setId(stopsViewIds.get(0));           // <-- Important
//        fragmentStartShipmentBinding.stopsConstraintLayout.addView(button);
//        set.connect(button.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
//        set.connect(button.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0);
//        set.constrainWidth(button.getId(), ConstraintSet.WRAP_CONTENT);
//        set.applyTo(fragmentStartShipmentBinding.stopsConstraintLayout);
//
//        EditText dateTxt = new EditText(getContext());
//        dateTxt.setHint("Delivery date and time");
//        dateViewIds.add(View.generateViewId());
//        dateTxt.setId(dateViewIds.get(0));
//        dateTxt.setInputType(InputType.TYPE_NULL);
//        fragmentStartShipmentBinding.stopsConstraintLayout.addView(dateTxt);
//        set.connect(dateTxt.getId(), ConstraintSet.TOP, button.getId(), ConstraintSet.TOP, 0);
//        set.connect(dateTxt.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 16);
//        set.constrainHeight(dateTxt.getId(), ConstraintSet.WRAP_CONTENT);
//        set.constrainWidth(dateTxt.getId(), ConstraintSet.WRAP_CONTENT);
//        set.applyTo(fragmentStartShipmentBinding.stopsConstraintLayout);

//
//        dateTxt.setOnClickListener(view -> {
//            mCalendar = Calendar.getInstance();
//
//            TimePickerDialog.OnTimeSetListener timeSetListener = (timePicker, hourOfDay, minute) -> {
//                mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
//                mCalendar.set(Calendar.MINUTE, minute);
//                dateTxt.setText(mSimpleDateFormat.format(mCalendar.getTime()));
//            };
//
//
//            DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, dayOfMonth) -> {
//                mCalendar.set(Calendar.YEAR, year);
//                mCalendar.set(Calendar.MONTH, month);
//                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                new TimePickerDialog(getContext(), timeSetListener,
//                        mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), false).show();
//            };
//
//            new DatePickerDialog(getContext(), dateSetListener,
//                    mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH))
//                    .show();
//
//        });


//        Button newButton = new Button(getContext());
//        newButton.setText("second");
//        newButton.setId(View.generateViewId());
//        fragmentStartShipmentBinding.stopsConstraintLayout.addView(newButton);
//        set.connect(newButton.getId(), ConstraintSet.TOP, button.getId(), ConstraintSet.BOTTOM, 0);
//        set.connect(newButton.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0);
//        set.constrainHeight(newButton.getId(), ConstraintSet.WRAP_CONTENT);
//        set.applyTo(fragmentStartShipmentBinding.stopsConstraintLayout);


//        ConstraintSet set = new ConstraintSet();
//        set.clone(fragmentStartShipmentBinding.stopsConstraintLayout);
//
//
//        View child = getLayoutInflater().inflate(R.layout.stops_view, null);
//        child.setId(id);
//
//
//
//
//        fragmentStartShipmentBinding.stopsConstraintLayout.addView(child);
//

//
//
//
//        set.connect(child.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
//        set.connect(child.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0);
//        set.constrainHeight(child.getId(), 200);
//        set.applyTo(fragmentStartShipmentBinding.stopsConstraintLayout);
//


    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("pk.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void initializeSpinnerData() {

        //Initialize spinner data

        rapidLineViewModel.getAllVechiles().observe(getViewLifecycleOwner(), vechiles -> {
            if(!vechiles.isEmpty()) {
                ArrayAdapter<Vechile> vechileAdapter = new ArrayAdapter<>(getContext(),
                        R.layout.spinner_item, vechiles);
                fragmentStartShipmentBinding.shipmentVechileSpinner.setAdapter(vechileAdapter);

                //Set the UI initially
                Vechile initialVechile = vechiles.get(0);
                fragmentStartShipmentBinding.vechileMessageTxt.setVisibility(View.VISIBLE);
                fragmentStartShipmentBinding.vehileMaintainanceTestBtn.setVisibility(View.VISIBLE);

                if (initialVechile.getFitnessPercentage()< 80) {
                    fragmentStartShipmentBinding.vechileMessageTxt.setText("Vechile is not fit");

                } else {
                    fragmentStartShipmentBinding.vechileMessageTxt.setText("Vechile fitness is good");
                }

                fragmentStartShipmentBinding.shipmentVechileSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Vechile vechile = (Vechile) fragmentStartShipmentBinding.shipmentVechileSpinner.getSelectedItem();
                        if (vechile.getFitnessPercentage()< 80) {
                            fragmentStartShipmentBinding.vechileMessageTxt.setText("Vechile is not fit");

                        } else {
                            fragmentStartShipmentBinding.vechileMessageTxt.setText("Vechile fitness is good");
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }
        });

        rapidLineViewModel.getAllDrivers().observe(getViewLifecycleOwner(), drivers -> {
            ArrayAdapter<Drivers> driverAdapter = new ArrayAdapter<>(getContext(),
                    R.layout.spinner_item, drivers);
            fragmentStartShipmentBinding.shipmentDriverSpinner.setAdapter(driverAdapter);
        });

        rapidLineViewModel.getAllSideKicks().observe(getViewLifecycleOwner(), sideKicks -> {
            ArrayAdapter<SideKick> sideKickAdapter = new ArrayAdapter<>(getContext(),
                    R.layout.spinner_item, sideKicks);
            fragmentStartShipmentBinding.shipmentSideKickSpinner.setAdapter(sideKickAdapter);
        });


        //Load cities
        try {
            JSONArray jsonArray = new JSONArray(loadJSONFromAsset());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String currCity = jsonObject.getString("city");
                cityList.add(currCity);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Setup stops Recycler View
        destinationStopsAdapter = new DestinationStopsAdapter(getContext(), new ArrayList<>(), this);
        fragmentStartShipmentBinding.stopsRv.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentStartShipmentBinding.stopsRv.setAdapter(destinationStopsAdapter);

        //Add a stop in recycler view
        fragmentStartShipmentBinding.addStopBtn.setOnClickListener(view -> {
            showAddStopPopup();
        });

        //Select session expiry date
        fragmentStartShipmentBinding.sessionExpiryDateTxt.setInputType(InputType.TYPE_NULL);
        fragmentStartShipmentBinding.sessionExpiryDateTxt.setOnClickListener(view -> {
            mCalendar = Calendar.getInstance();

            TimePickerDialog.OnTimeSetListener timeSetListener = (timePicker, hourOfDay, minute) -> {
                mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                mCalendar.set(Calendar.MINUTE, minute);
                fragmentStartShipmentBinding.sessionExpiryDateTxt.setText(
                        mSimpleDateFormat.format(mCalendar.getTime())
                );
            };


            DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, dayOfMonth) -> {
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, month);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                new TimePickerDialog(getContext(), timeSetListener,
                        mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), false).show();
            };

            new DatePickerDialog(getContext(), dateSetListener,
                    mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH))
                    .show();
        });


    }

    private void showAddStopPopup() {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getContext());
        View view = layoutInflaterAndroid.inflate(R.layout.popup_add_city, null);
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
        alertBuilder.setView(view);

        TextView heading = view.findViewById(R.id.popup_heading);
        Spinner citySpinner = view.findViewById(R.id.popup_city_text);
        Button addBtn = view.findViewById(R.id.popup_btn);

        heading.setText("Choose City");
        addBtn.setText("Add stop");

        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(getContext(),
                R.layout.spinner_item, cityList);
        citySpinner.setAdapter(cityAdapter);

        AlertDialog dialog = alertBuilder.create();
        dialog.show();

        addBtn.setOnClickListener(view1 -> {
            destinationStopsAdapter.addStop(citySpinner.getSelectedItem().toString());
            dialog.dismiss();
        });

    }

    //To select delivery date
    @Override
    public void onDateClick(int position) {

        mCalendar = Calendar.getInstance();

        TimePickerDialog.OnTimeSetListener timeSetListener = (timePicker, hourOfDay, minute) -> {
            mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            mCalendar.set(Calendar.MINUTE, minute);
            destinationStopsAdapter.addDate(mSimpleDateFormat.format(mCalendar.getTime()), position);
        };


        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, dayOfMonth) -> {
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, month);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            new TimePickerDialog(getContext(), timeSetListener,
                    mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), false).show();
        };

        new DatePickerDialog(getContext(), dateSetListener,
                mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH))
                .show();

    }

    private boolean isFieldEmpty() {
        if (TextUtils.isEmpty(fragmentStartShipmentBinding.sessionExpiryDateTxt.getText())) {
            Toast.makeText(getContext(), "Enter expiry date", Toast.LENGTH_SHORT).show();
            return true;
        }

        //Now check for arrival date of stops
        for (String arrivalDate : destinationStopsAdapter.getArrivalDateArrayList()) {
            if (TextUtils.isEmpty(arrivalDate)) {
                Toast.makeText(getContext(), "Enter expected arrival date for stop", Toast.LENGTH_SHORT).show();
                return true;
            }
        }


        return false;
    }
}
