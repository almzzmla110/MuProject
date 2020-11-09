package com.example.muproject.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.muproject.R;
import com.example.muproject.databinding.ActivityMainBinding;
import com.example.muproject.ui.home.activity.StateViewActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        binding.btnStateview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StateViewActivity.start(MainActivity.this);
            }
        });
    }
}
