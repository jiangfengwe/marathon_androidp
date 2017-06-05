package com.tdin360.zjw.marathon.ui.activity;



import android.os.Bundle;
import android.widget.TextView;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.MarkModel;


/**
 *成绩详情
 * @author zhangzhijun
 */
public class MyAchievementDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolBarTitle("成绩单");
         showBackButton();

        initView();
    }

    private void initView() {


        MarkModel model = (MarkModel) getIntent().getSerializableExtra("model");

        if(model!=null){

            //展示成绩单

            showInfo(model);
        }

    }

    //显示成绩单数据
    private void showInfo(MarkModel model) {

        TextView ranking = (TextView) this.findViewById(R.id.ranking);
        ranking.setText("第 "+model.getCompetitorRank()+" 名");
        TextView eventName = (TextView) this.findViewById(R.id.eventName);
        eventName.setText(model.getEventName());
        TextView name = (TextView) this.findViewById(R.id.name);
        name.setText(model.getName());
        TextView gander = (TextView) this.findViewById(R.id.gander);
        gander.setText(model.getCompetitorSex()==true?"男":"女");
        TextView number = (TextView) this.findViewById(R.id.numBer);
        number.setText(model.getNumber());
        TextView project = (TextView) this.findViewById(R.id.projectName);
        project.setText(model.getCompetitorType());
        TextView mark = (TextView) this.findViewById(R.id.mark);
         mark.setText(model.getJing().equals("null")||model.getJing().equals("")? "00:00:00":model.getJing());
        TextView organizer = (TextView) this.findViewById(R.id.organizer);
          organizer.setText(model.getEventOrganizer().equals("null")?"":model.getEventOrganizer());
    }

    @Override
    public int getLayout() {
        return R.layout.activity_my_achievement_details ;
    }
}
