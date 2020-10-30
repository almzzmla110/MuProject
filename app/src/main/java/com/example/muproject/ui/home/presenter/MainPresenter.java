package com.example.muproject.ui.home.presenter;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.muproject.service.EmployeeDataService;
import com.example.muproject.service.RetrofitClient;
import com.example.muproject.ui.home.bean.Employee;
import com.example.muproject.ui.home.bean.EmployeeDBResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter {
    private static final String TAG = "EmployeeRepository";
    private ArrayList<Employee> employees = new ArrayList<>();
    private MutableLiveData<List<Employee>> mutableLiveData = new MutableLiveData<>();

    public MainPresenter() {
    }

    public MutableLiveData<List<Employee>> getMutableLiveData() {

        final EmployeeDataService userDataService = RetrofitClient.getService();

        Call<EmployeeDBResponse> call = userDataService.getEmployees();
        call.enqueue(new Callback<EmployeeDBResponse>() {
            @Override
            public void onResponse(Call<EmployeeDBResponse> call, Response<EmployeeDBResponse> response) {
                EmployeeDBResponse employeeDBResponse = response.body();
                if (employeeDBResponse != null && employeeDBResponse.getEmployee() != null) {
                    employees = (ArrayList<Employee>) employeeDBResponse.getEmployee();
                    Log.e("zw", employees.get(0).getFirstName());
                    mutableLiveData.setValue(employees);
                }
            }

            @Override
            public void onFailure(Call<EmployeeDBResponse> call, Throwable t) {
            }
        });

        return mutableLiveData;
    }
}
