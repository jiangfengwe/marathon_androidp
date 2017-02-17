package com.tdin360.zjw.marathon.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tdin360.zjw.marathon.R;

import me.gujun.android.taggroup.TagGroup;

/**
 *
 * 搜索
 */
public class SearchActivity extends AppCompatActivity {

    private Toolbar mToolBar;

    private EditText editText;
    private ImageView clearBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        this.editText = (EditText) this.findViewById(R.id.edit);
        this.clearBtn = (ImageView) this.findViewById(R.id.clear);
        this.mToolBar= (Toolbar) this.findViewById(R.id.mToolBar);
        this.setSupportActionBar(this.mToolBar);
        this.mToolBar.setNavigationIcon(R.drawable.nav_back);
        this.mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TagGroup mTagGroup = (TagGroup) findViewById(R.id.tag_group);
        mTagGroup.setTags(new String[]{"Tag1", "Tag2", "Tag3"});

        mTagGroup.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {

                Toast.makeText(SearchActivity.this,tag,Toast.LENGTH_SHORT).show();
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


    }

    //返回
    public void back(View view) {
        finish();
    }

//    搜索
    public void search(View view) {


    }

    //清空搜索关键词
    public void clear(View view) {

     this.editText.setText("");

    }
}
