package com.example.muproject.ui.home.bean;

import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.BindingMethod;
import androidx.databinding.BindingMethods;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.muproject.R;

/**
 * ****************************************
 * Created By LiXin  2020/10/30 13:47
 * ****************************************
 */
public class DataBean {
    /**
     * id : 1
     * email : george.bluth@reqres.in
     * first_name : George
     * last_name : Bluth
     * avatar : https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg
     */

    public int id;
    public String email;
    public String first_name;
    public String last_name;
    public String avatar;

    public DataBean(String email, String first_name, String last_name, String avatar) {
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.avatar = avatar;
    }

}