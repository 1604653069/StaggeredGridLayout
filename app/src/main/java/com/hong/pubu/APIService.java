package com.hong.pubu;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIService {
    @POST(Constants.food)
    Observable<BaseResponse<List<Food>>> findFoods(@Body Object loginData);

}
