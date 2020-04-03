package com.project.rapidline.viewmodel;

import android.app.Application;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.project.rapidline.AdminRights;
import com.project.rapidline.Database.entity.Admins;
import com.project.rapidline.repository.RapidLineRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class AdminViewModel extends AndroidViewModel {

    private RapidLineRepository rapidLineRepository;
    private String adminName;

    public AdminViewModel(@NonNull Application application) {
        super(application);
        rapidLineRepository = new RapidLineRepository(application);
    }

    public LiveData<String> checkAdmin(String username,String password){
        MutableLiveData<String> response=new MutableLiveData<>();
        rapidLineRepository.getAdmins().whereEqualTo("username",username)
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        if(task.getResult().isEmpty()){
                            response.setValue("Either password or username is incorrect");
                        }
                        else {
                            for(QueryDocumentSnapshot users:task.getResult()){
                                //Check pass
                                if(password.equals(users.getString("pass"))){
                                    response.setValue("Login Sucessfull");
                                    adminName=users.getString("adminName");
                                    return;
                                }
                            }
                            response.setValue("Password is incorrect");
                        }
                    }
                });

        return response;
    }

    public LiveData<String> addAdmin(final Admins admin){
        return rapidLineRepository.addAdmin(admin);
    }

    public String getAdminName() {
        return adminName;
    }
}
