package com.example.muproject.service;


import com.example.muproject.ui.home.bean.CoursePageInfoBean;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface MuService {
    @Headers({"Cache-Control:no-store"})
    @GET("xtk/v3/pricesAndAds.do")
    Single<CoursePageInfoBean> getCoursePageDataV3(
            @Query("productType") String productType,
            @Query("username") String username
    );

}
