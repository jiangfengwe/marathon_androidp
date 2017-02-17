package com.tdin360.zjw.marathon.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.tdin360.zjw.marathon.ui.fragment.Marathon_Fragment_News;
import com.tdin360.zjw.marathon.ui.fragment.Marathon_Fragment_Notice;
import com.tdin360.zjw.marathon.ui.fragment.Marathon_Fragment_page_ShowHtml;
import com.tdin360.zjw.marathon.ui.fragment.Marathon_Fragment_Home;
import com.tdin360.zjw.marathon.utils.MarathonDataUtils;


/**
 * Created by Administrator on 2016/6/17.
 */
public class MarathonDetailPageTopTabAdapter extends FragmentPagerAdapter {

    private String titles[]={"首页","赛事简介","赛事新闻","赛事公告","参赛路线","领物指南"};


    public MarathonDetailPageTopTabAdapter(FragmentManager fm) {
        super(fm);

    }
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0://首页
                return Marathon_Fragment_Home.newInstance();
            case 1://赛事简介
                return Marathon_Fragment_page_ShowHtml.newInstance("http://byydtk.oicp.net:26211/EventInfo/EventInfoDetail?eventId="+ MarathonDataUtils.init().getEventId()+"&categoryName="+titles[1]);
            case 2://赛事新闻
                return Marathon_Fragment_News.newInstance(titles[2]);
            case 3://赛事公告
                return Marathon_Fragment_Notice.newInstance (titles[3]);
            case 4://参赛路线
                return Marathon_Fragment_page_ShowHtml.newInstance("http://www.gymarathon.com/HomeMobile/InfoDetail?id=15");
            case 5://领物指南
                return Marathon_Fragment_page_ShowHtml.newInstance("http://www.gymarathon.com/HomeMobile/InfoDetail?id=15");
        }

        return null;
    }


    /**
     * 防止fragment销毁，解决切换卡顿问题
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return titles.length;
    }
}
