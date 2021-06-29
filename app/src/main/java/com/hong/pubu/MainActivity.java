package com.hong.pubu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.hong.pubu.adapter.MyAdapter;
import com.hong.pubu.adapter.StaggeredDividerItemDecoration;
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
    private  StaggeredGridLayoutManager staggeredGridLayoutManager;
    private int spanCount =2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerview = this.findViewById(R.id.recyclerview);
        refresh = this.findViewById(R.id.refresh);
        refresh.setOnLoadMoreListener(this);
        adapter = new MyAdapter(this,foods);
        staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(spanCount,
                        StaggeredGridLayoutManager.VERTICAL);
        recyclerview.setHasFixedSize(true);
        recyclerview.setItemAnimator(null);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerview.setLayoutManager(staggeredGridLayoutManager);
        recyclerview.addItemDecoration(new StaggeredDividerItemDecoration(this,10,spanCount));
        recyclerview.setAdapter(adapter);
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int[] first = new int[spanCount];
                staggeredGridLayoutManager.findFirstCompletelyVisibleItemPositions(first);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && (first[0] == 1 || first[1] == 1)) {
                    staggeredGridLayoutManager.invalidateSpanAssignments();
                }
            }
        });
        getImageData();

    }
    public void getImageData(){
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
        page++;
        getImageData();
        refreshLayout.finishLoadMore(true);
    }
}