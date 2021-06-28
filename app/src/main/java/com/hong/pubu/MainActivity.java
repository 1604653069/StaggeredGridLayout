package com.hong.pubu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.hong.pubu.adapter.MyAdapter;
import com.hong.pubu.rxjava.RxJavaHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity implements OnLoadMoreListener {
    private RecyclerView recyclerview;
    private SmartRefreshLayout refresh;
    private int page = 1;
    private List<Food> foods = new ArrayList<>();
    private MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerview = this.findViewById(R.id.recyclerview);
        refresh = this.findViewById(R.id.refresh);
        refresh.setOnLoadMoreListener(this);

        adapter = new MyAdapter(this,foods);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(2,
                        StaggeredGridLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(staggeredGridLayoutManager);
        recyclerview.setAdapter(adapter);
        Map<String,Object> map = new HashMap<>();
        map.put("page",page);
        RetrofitManager.retrofit().create(APIService.class)
                .findFoods(map)
                .compose(RxJavaHelper.<BaseResponse<List<Food>>>observeOnMainThread())
                .subscribe(new Consumer<BaseResponse<List<Food>>>() {
                    @Override
                    public void accept(BaseResponse<List<Food>> listBaseResponse) throws Exception {
                        adapter.update(listBaseResponse.getData());
                    }
                });
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }
}