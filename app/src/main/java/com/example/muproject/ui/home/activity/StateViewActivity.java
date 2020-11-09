package com.example.muproject.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.muproject.R;
import com.example.muproject.databinding.ActivityStateviewBinding;
import com.example.muproject.ui.home.view.StateFrameLayout;
import com.example.muproject.ui.home.viewmodel.StateViewModel;

/**
 * ****************************************
 * Created By LiXin  2020/11/9 10:17
 * ****************************************
 */
public class StateViewActivity extends AppCompatActivity {

    private ActivityStateviewBinding binding;

    public static void start(Context context){
        context.startActivity(new Intent(context,StateViewActivity.class));
    }
    int i = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_stateview);

        final StateViewModel viewModel = new ViewModelProvider(this).get(StateViewModel.class);
        viewModel.loadOtherError();
        viewModel.viewStateMutableLiveData.observe(this, new Observer<StateFrameLayout.ViewState>() {
            @Override
            public void onChanged(StateFrameLayout.ViewState viewState) {
                binding.stateView.showViewState(viewState);
            }
        });


        binding.stateView.setOnReloadListener(new StateFrameLayout.OnReloadListener() {
            @Override
            public void onReload(View view) {
                if (i == 0){
                    viewModel.loadNetError();
                }else if (i == 1){
                    viewModel.loadEmpty();
                }else if (i == 2){
                    viewModel.loadContent();
                }
                i++;
            }
        });
    }
}
