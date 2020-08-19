package com.project.rapidline.Activities.RapidLine.Forms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Toast;

import com.project.rapidline.Models.RapidLine.Vechile;
import com.project.rapidline.R;
import com.project.rapidline.databinding.ActivityFitnessTestBinding;
import com.project.rapidline.viewmodel.RapidLine.RapidLineViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FitnessTest extends AppCompatActivity {

    private ActivityFitnessTestBinding fitnessTestBinding;
    private RapidLineViewModel rapidLineViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fitnessTestBinding = DataBindingUtil.setContentView(this, R.layout.activity_fitness_test);
        rapidLineViewModel = ViewModelProviders.of(this).get(RapidLineViewModel.class);


        rapidLineViewModel.getAllVechiles().observe(this, vechiles -> {
            List<Vechile> vechileList = new ArrayList<>(vechiles);
            vechileList.add(0, new Vechile("Select a vechile"));
            ArrayAdapter<Vechile> arrayAdapter = new ArrayAdapter<>(this,
                    R.layout.spinner_item, vechileList);
            fitnessTestBinding.vechileSpinner.setAdapter(arrayAdapter);
        });

        fitnessTestBinding.backBtn.setOnClickListener(view -> {
            finish();
        });


        fitnessTestBinding.testBtn.setOnClickListener(view -> {
            if (fitnessTestBinding.vechileSpinner.getSelectedItem().toString().equals("Select a vechile")) {
                Toast.makeText(this, "Please select a vechile", Toast.LENGTH_SHORT).show();
                return;
            }

            double fitness = calculateFitness();

            double percentage = (fitness / 9) * 100;

            fitnessTestBinding.progressBarFitness.setProgress((int) percentage);
            fitnessTestBinding.progressPercent.setText((int) percentage + " %");

            //Scroll to top
            fitnessTestBinding.scrollVechView.post(() -> fitnessTestBinding.scrollVechView.fullScroll(fitnessTestBinding.scrollVechView.FOCUS_UP));

            rapidLineViewModel.addVechileFitness(
                    fitnessTestBinding.vechileSpinner.getSelectedItem().toString(),
                    (int) percentage,
                    Calendar.getInstance().getTime()
            );


            if (percentage < 80) {
                Toast.makeText(this, "Vechile not fit\nMaintainaince required", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Vechile fitness is good", Toast.LENGTH_SHORT).show();
            }


        });

    }

    private int calculateFitness() {
        int fitness = 0;

        CheckBox[] chechBoxGroup = {fitnessTestBinding.gearOilChk, fitnessTestBinding.engineOilChk,
                fitnessTestBinding.breakOilChk, fitnessTestBinding.breakShoeChk, fitnessTestBinding.breakPedalChk,
                fitnessTestBinding.gearBoxChk, fitnessTestBinding.headLightChk, fitnessTestBinding.breakLightChk,
                fitnessTestBinding.indicatorChk};

        for (CheckBox box : chechBoxGroup) {
            if (box.isChecked())
                fitness++;
        }

        return fitness;
    }

}
