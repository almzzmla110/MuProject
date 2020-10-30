package com.example.muproject.ui.home.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.muproject.service.RetrofitClient;
import com.example.muproject.ui.home.bean.CoursePageInfoBean;
import com.example.muproject.ui.home.bean.DataBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel {

    public final MutableLiveData<List<DataBean>> mutableLiveData = new MutableLiveData<>();
    public final MutableLiveData<Boolean> showLoadingLiveData = new MutableLiveData<>();
    private Disposable disposable;


    public void getXtkData() {
        showLoadingLiveData.setValue(true);
        disposable = RetrofitClient.getXtkApiService().getCoursePageDataV3("ZHIYEYISHI", "17600172723")
                .map(new Function<CoursePageInfoBean, List<DataBean>>() {
                    @Override
                    public List<DataBean> apply(CoursePageInfoBean CoursePageInfoBean) throws Exception {
                        List<DataBean> list = new ArrayList<>();
                        List<CoursePageInfoBean.TeachersBean> teachers = CoursePageInfoBean.teachers;
                        for (CoursePageInfoBean.TeachersBean teachersBean : CoursePageInfoBean.teachers) {
                            list.add(new DataBean(teachersBean.name, teachersBean.realname, teachersBean.zhuanye, "http://www.xinghengedu.com" + teachersBean.img));
                        }
                        return list;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<DataBean>>() {
                    @Override
                    public void accept(List<DataBean> dataBeans) throws Exception {
                        showLoadingLiveData.setValue(false);

                        mutableLiveData.setValue(dataBeans);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showLoadingLiveData.setValue(false);

                    }
                });

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
