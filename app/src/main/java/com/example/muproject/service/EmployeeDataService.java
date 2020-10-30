package com.example.muproject.service;


import com.example.muproject.ui.home.bean.EmployeeDBResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EmployeeDataService {
    @GET("users/?per_page=12&amp;page=1")
    Call<EmployeeDBResponse> getEmployees();
}
