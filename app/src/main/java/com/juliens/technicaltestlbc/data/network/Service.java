package com.juliens.technicaltestlbc.data.network;

import com.juliens.technicaltestlbc.data.ListPhoto;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by juliens on 12/10/2017.
 */

public class Service {
    private static NetworkService networkService;
    private Service(){
        networkService = new Retrofit.Builder()
                .baseUrl("http://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(NetworkService.class);
    }

    //Singleton
    private static class ServiceHolder{
        private final static Service instance = new Service();
    }
    public static Service getInstance(){
        return ServiceHolder.instance;
    }

    public Observable<ListPhoto> getListPhoto(){
        return networkService.getListPhoto()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
