package com.example.muproject.ui.home.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muproject.R;
import com.example.muproject.databinding.EmployeeRowLayoutBinding;
import com.example.muproject.ui.home.bean.Employee;

import java.util.ArrayList;

public class EmployeeDataAdapter extends RecyclerView.Adapter<EmployeeDataAdapter.EmployeeViewHolder> {

    private ArrayList<Employee> employees;

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EmployeeRowLayoutBinding employeeRowLayoutBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.employee_row_layout, parent, false);
        return new EmployeeViewHolder(employeeRowLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee currentEmp = employees.get(position);
        holder.employeeRowLayoutBinding.setEmployee(currentEmp);
    }

    @Override
    public int getItemCount() {
        if (employees != null) {
            return employees.size();
        } else {
            return 0;
        }
    }

    public void setEmployeeList(ArrayList<Employee> employees) {
        this.employees = employees;
        notifyDataSetChanged();
    }

    class EmployeeViewHolder extends RecyclerView.ViewHolder {

        private EmployeeRowLayoutBinding employeeRowLayoutBinding;

        public EmployeeViewHolder(@NonNull EmployeeRowLayoutBinding employeeRowLayoutBinding) {
            super(employeeRowLayoutBinding.getRoot());

            this.employeeRowLayoutBinding = employeeRowLayoutBinding;
        }
    }
}
