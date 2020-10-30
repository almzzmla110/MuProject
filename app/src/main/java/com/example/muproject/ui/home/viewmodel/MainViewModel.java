package com.example.muproject.ui.home.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.example.muproject.ui.home.bean.Employee;
import com.example.muproject.ui.home.presenter.MainPresenter;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private MainPresenter mainPresenter;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mainPresenter = new MainPresenter();
    }

    public LiveData<List<Employee>> getAllEmployee() {
        return mainPresenter.getMutableLiveData();
    }
}
