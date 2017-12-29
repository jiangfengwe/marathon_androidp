package com.tdin360.zjw.marathon.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.adapter.RecyclerViewBaseAdapter;
import com.tdin360.zjw.marathon.model.HotelListModel;
import com.tdin360.zjw.marathon.ui.activity.HotelDetailsActivity;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.MarathonDataUtils;
import com.tdin360.zjw.marathon.utils.ToastUtils;
import com.tdin360.zjw.marathon.weight.ErrorView;
import com.tdin360.zjw.marathon.weight.SpaceItemDecoration;
import com.tdin360.zjw.marathon.weight.ZJSpaceItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 酒店预订列表
 * Created by admin on 17/7/26.
 */

public class HotelTabFragment extends BaseFragment {

    private String title;
    private RecyclerViewBaseAdapter adapter;

    private List<HotelListModel>list=new ArrayList<>();
    @ViewInject(R.id.mRecyclerView)
    private RecyclerView mRecyclerView;
    @ViewInject(R.id.errorView)
    private ErrorView mErrorView;
    public static HotelTabFragment newInstance(String title){

        HotelTabFragment fragment = new HotelTabFragment();

        fragment.title=title;

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_hotel_tab,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new ZJSpaceItem(10,1));
        adapter = new MyAdapter(getContext(),list,R.layout.hotel_list_item);
        mRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new RecyclerViewBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


                Intent intent = new Intent(getActivity(), HotelDetailsActivity.class);
                intent.putExtra("id",list.get(position).getId());
                intent.putExtra("title",list.get(position).getName());
                startActivity(intent);

            }
        });

        /**
         * 加载失败点击重试
         */
        mErrorView.setErrorListener(new ErrorView.ErrorOnClickListener() {
            @Override
            public void onErrorClick(ErrorView.ViewShowMode mode) {

                switch (mode){

                    case NOT_NETWORK:
                        httpRequest();
                        break;

                }
            }
        });


         httpRequest();

    }


    private void httpRequest(){
        final KProgressHUD hud = KProgressHUD.create(getContext());
        hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(1)
                .setDimAmount(0.5f)
                .show();

       /* RequestParams params = new RequestParams(HttpUrlUtils.HOTEL_LIST);
        params.addBodyParameter("type",title);
        params.addBodyParameter("eventId", MarathonDataUtils.init().getEventId());
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {


//                Log.d("------->", "onSuccess: "+result);

                try {

                    JSONObject obj = new JSONObject(result);

                    JSONArray array = obj.getJSONArray("EventHotelList");
                    Gson gson = new Gson();
                    for(int i=0;i<array.length();i++){

                        JSONObject object = array.getJSONObject(i);
                        HotelListModel model = gson.fromJson(object.toString(), HotelListModel.class);
                        list.add(model);


                    }
                    if(list.size()<=0){

                        mErrorView.show(mRecyclerView,"暂时还有没有数据",ErrorView.ViewShowMode.NOT_DATA);
                    }else {
                        mErrorView.hideErrorView(mRecyclerView);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                    mErrorView.show(mRecyclerView,"服务器数据异常", ErrorView.ViewShowMode.ERROR);
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                mErrorView.show(mRecyclerView,"加载失败,点击重试", ErrorView.ViewShowMode.NOT_NETWORK);
                ToastUtils.show(getActivity(),"网络不给力,连接服务器异常!");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                hud.dismiss();
                adapter.update(list);
            }
        });*/


    }

    /**
     * 数据适配器
     */
    class  MyAdapter extends RecyclerViewBaseAdapter<HotelListModel> {


        public MyAdapter(Context context, List<HotelListModel> list, int layoutId) {
            super(context, list, layoutId);
        }

        @Override
        protected void onBindNormalViewHolder(NormalViewHolder holder, HotelListModel model) {

            holder.setText(R.id.hotel_name,model.getName());
            holder.setText(R.id.hotel_price,"¥"+model.getLowestPrice()+"起");
            ImageView view = (ImageView) holder.getViewById(R.id.hotel_imageView);
            x.image().bind(view,model.getPictureUrl());
            switch (model.getType()){

                case "豪华":
                    holder.setImageResource(R.id.hotel_type,R.drawable.hotel_hh);
                    break;
                case "经济":
                    holder.setImageResource(R.id.hotel_type,R.drawable.hotel_jj);
                    break;
                case "舒适":
                    holder.setImageResource(R.id.hotel_type,R.drawable.hotel_ss);
            }


        }
    }

}

