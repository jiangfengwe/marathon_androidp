package com.tdin360.zjw.marathon.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.adapter.MarathonListViewAdapter;
import com.tdin360.zjw.marathon.model.EventModel;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.MarathonDataUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import me.gujun.android.taggroup.TagGroup;

/**
 *
 * 搜索
 */
public class SearchActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    private EditText editText;
    private ImageView clearBtn;
    private ListView listView;
    private TextView resultCount;
    private MyAdapter adapter;
    private List<EventModel> list = new ArrayList<>();
    private TagGroup mTagGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        this.editText = (EditText) this.findViewById(R.id.edit);
        this.resultCount = (TextView) this.findViewById(R.id.resultCount);
        this.clearBtn = (ImageView) this.findViewById(R.id.clear);
        this.mToolBar= (Toolbar) this.findViewById(R.id.mToolBar);
        this.listView = (ListView) this.findViewById(R.id.listView);
        this.setSupportActionBar(this.mToolBar);
        this.mToolBar.setNavigationIcon(R.drawable.nav_back);
        this.mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        this.mTagGroup = (TagGroup) findViewById(R.id.tag_group);
        this.mTagGroup.setTags(new String[]{"Tag1", "Tag2", "Tag3"});

        this.mTagGroup.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {

                Toast.makeText(SearchActivity.this,tag,Toast.LENGTH_SHORT).show();

                editText.setText(tag);
                httpRequest(tag);
            }
        });


        /**
         * 监听搜索框值的变化
         */
        this.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.length()>0){
                    clearBtn.setVisibility(View.VISIBLE);
                }else {
                    clearBtn.setVisibility(View.INVISIBLE);
                }
            }
        });

        this.adapter = new MyAdapter();
        this.listView.setAdapter(adapter);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                EventModel eventInfo = list.get(position);
                //为单例成员赋值
                MarathonDataUtils.init().setEventId(eventInfo.getId() + "");
                MarathonDataUtils.init().setEventName(eventInfo.getName());
                MarathonDataUtils.init().setStatus(eventInfo.getStatus());
                MarathonDataUtils.init().setEventImageUrl(eventInfo.getPicUrl());
                MarathonDataUtils.init().setShareUrl(eventInfo.getShardUrl());
                MarathonDataUtils.init().setRegister(eventInfo.isRegister());
                Intent intent = new Intent(SearchActivity.this, MarathonDetailsActivity.class);

                startActivity(intent);
            }
        });

    }

    //返回
    public void back(View view) {
        finish();
    }

//    搜索
    public void search(View view) {

        list.clear();
        //   1.获取输入的搜索关键词

      String key = editText.getText().toString().trim();

        if(key.equals("")){

            Toast.makeText(this,"请输入关键字",Toast.LENGTH_SHORT).show();
            return;
        }

        httpRequest(key);

    }

    //清空搜索关键词
    public void clear(View view) {

     this.editText.setText("");

    }

    // 向服务器发起搜索
    private void httpRequest(String key){

        //显示提示框
        final KProgressHUD hud = KProgressHUD.create(this);
        hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(1)
                .setDimAmount(0.5f)
                .show();
        RequestParams params = new RequestParams(HttpUrlUtils.SEARCH);
        params.addQueryStringParameter("key",key);
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        x.http().get(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {


                try {
                    JSONObject obj = new JSONObject(result);

//                    Log.d("------->>>", "onSuccess: "+obj);

                    JSONArray array = obj.getJSONArray("EventSystemMessageList");

                    for(int i=0;i<array.length();i++){

                        JSONObject object = array.getJSONObject(i);

                        int id = object.getInt("Id");
                        String eventName = object.getString("EventName");
                        String status = object.getString("Status");
                        String eventStartTime = object.getString("EventStartTimeStr");
                        String shareUrl = object.getString("EventSiteUrl");
                         String pictureUrl = object.getString("PictureUrl");
                         boolean isRegister = object.getBoolean("IsRegister");
                        EventModel eventModel = new EventModel(id, eventName, status, pictureUrl,"",eventStartTime,shareUrl,isRegister);
                        list.add(eventModel);


                    }


                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                adapter.notifyDataSetChanged();
                hud.dismiss();
                resultCount.setVisibility(View.VISIBLE);
                resultCount.setText("共搜到"+list.size()+"条结果");
            }

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });
    }


    /**
     * 数据适配器
     */
    private class MyAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            return list==null?0:list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder=null;

            if(convertView==null){

               viewHolder = new ViewHolder();

                convertView = LayoutInflater.from(SearchActivity.this).inflate(R.layout.search_list_item,null);
                viewHolder.status = (TextView) convertView.findViewById(R.id.status);
                viewHolder.title = (TextView) convertView.findViewById(R.id.title);

                convertView.setTag(viewHolder);
            }else {

                viewHolder= (ViewHolder) convertView.getTag();
            }

            EventModel model = list.get(position);

            viewHolder.title.setText(model.getName());
            viewHolder.status.setText(model.getStatus());
            if(model.getStatus().equals("已结束")){

                viewHolder.status.setEnabled(false);
            }

            return convertView;
        }


        class ViewHolder{

            TextView title;
            TextView status;
        }
    }

}
