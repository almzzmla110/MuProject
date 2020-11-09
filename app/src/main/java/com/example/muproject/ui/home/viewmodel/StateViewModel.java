package com.example.muproject.ui.home.viewmodel;

import android.os.Handler;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.muproject.ui.home.view.StateFrameLayout;

/**
 * ****************************************
 * Created By LiXin  2020/11/9 10:27
 * ****************************************
 */
public class StateViewModel extends ViewModel {

    public MutableLiveData<StateFrameLayout.ViewState> viewStateMutableLiveData = new MutableLiveData<>();


    public void loadOtherError(){
        viewStateMutableLiveData.setValue(StateFrameLayout.ViewState.LOADING);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                viewStateMutableLiveData.setValue(StateFrameLayout.ViewState.OTHER_ERROR);
            }
        },1000);
    }

    public void loadNetError(){
        viewStateMutableLiveData.setValue(StateFrameLayout.ViewState.LOADING);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                viewStateMutableLiveData.setValue(StateFrameLayout.ViewState.NET_ERROR);
            }
        },1000);
    }


    public void loadEmpty(){
        viewStateMutableLiveData.setValue(StateFrameLayout.ViewState.LOADING);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                viewStateMutableLiveData.setValue(StateFrameLayout.ViewState.EMPTY);
            }
        },1000);
    }


    public void loadContent(){
        viewStateMutableLiveData.setValue(StateFrameLayout.ViewState.LOADING);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                viewStateMutableLiveData.setValue(StateFrameLayout.ViewState.CONTENT);
            }
        },1000);
    }


}
