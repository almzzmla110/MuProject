package com.example.muproject.ui;

import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.muproject.R;
import com.example.muproject.base.BaseActivity;
import com.example.muproject.databinding.ActivityMainBinding;
import com.example.muproject.ui.home.adapter.RvAdapter;
import com.example.muproject.ui.home.bean.DataBean;
import com.example.muproject.ui.home.viewmodel.MainViewModel;

import java.util.List;

public class MainActivity extends BaseActivity {
    private MainViewModel viewModel;
    private RvAdapter adapter;
    private ActivityMainBinding binding;

    @Override
    protected void setDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override
    public void initData() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getXtkData();//执行接口
    }

    @Override
    public void initView() {
        adapter = new RvAdapter();
        binding.viewEmployees.setLayoutManager(new LinearLayoutManager(this));
        binding.viewEmployees.setHasFixedSize(true);
        binding.viewEmployees.setAdapter(adapter);
    }

    @Override
    public void setListener() {
        viewModel.mutableLiveData.observe(this, new Observer<List<DataBean>>() {
            @Override
            public void onChanged(List<DataBean> dataBeans) {
                Log.e(TAG, "onChanged: ------>"+dataBeans.size());
                adapter.setNewData(dataBeans);
            }
        });

        viewModel.showLoadingLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.progressbar.setVisibility(aBoolean == Boolean.TRUE ? View.VISIBLE : View.GONE);

            }
        });
    }

}
