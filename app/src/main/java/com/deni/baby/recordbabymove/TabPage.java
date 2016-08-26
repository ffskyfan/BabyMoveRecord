package com.deni.baby.recordbabymove;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;

import com.deni.baby.recordbabymove.fragment.RecordListFragment;

/**
 * Created by deni on 2016/8/25.
 *
 */

public class TabPage extends FragmentActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        //獲取TabHost控制元件
        FragmentTabHost mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        //設定Tab頁面的顯示區域，帶入Context、FragmentManager、Container ID
        //mTabHost.setup(this, getSupportFragmentManager(), R.id.tab_content_list);

        /**
         新增Tab結構說明 :
         首先帶入Tab分頁標籤的Tag資訊並可設定Tab標籤上顯示的文字與圖片，
         再來帶入Tab頁面要顯示連結的Fragment Class，最後可帶入Bundle資訊。
         **/

        //小黑人建立一個Tab，這個Tab的Tag設定為one，
        //並設定Tab上顯示的文字為第一堂課與icon圖片，Tab連結切換至
        //LessonOneFragment class，無夾帶Bundle資訊。
        mTabHost.addTab(mTabHost.newTabSpec("one").setIndicator("第一堂課",getResources().getDrawable(R.mipmap.ic_launcher))
                , RecordListFragment.class,null);

        //同上方Tab設定，不同處為帶入參數的差異
        //mTabHost.addTab(mTabHost.newTabSpec("two")
        //                .setIndicator("第二堂課",getResources().getDrawable(R.drawable.lesson2_item))
        //        ,LessonTwoFragment.class,null);

        //同上方Tab設定，不同處為帶入參數的差異
        //mTabHost.addTab(mTabHost.newTabSpec("three")
        //                .setIndicator("第三堂課",getResources().getDrawable(R.drawable.lesson3_item))
        //        ,LessonThreeFragment.class, null);

        //同上方Tab設定，不同處為帶入參數的差異
        //mTabHost.addTab(mTabHost.newTabSpec("four")
        //                .setIndicator("第四堂課",getResources().getDrawable(R.drawable.lesson4_item))
        //        ,LessonFourFragment.class,null);
    }

    /**
     方法權限設定為Public目的是可以讓Fragment取得內容 。
     */

    //Tab - Lesson One的文字內容
    public String getLessonOneText()
    {
        return "小黑人的Android教室\n- 第一堂課 -";
    }

    //Tab - Lesson Two的文字內容
    public String getLessonTwoText()
    {
        return "小黑人的Android教室\n- 第二堂課 -";
    }

    //Tab - Lesson Three的文字內容
    public String getLessonThreeText()
    {
        return "小黑人的Android教室\n- 第三堂課 -";
    }

    //Tab - Lesson Four的文字內容
    public String getLessonFourText()
    {
        return "小黑人的Android教室\n- 第四堂課 -";
    }

}
