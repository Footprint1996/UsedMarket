package com.maker.use.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.maker.use.R;
import com.maker.use.domain.User;
import com.maker.use.global.ConstentValue;
import com.maker.use.ui.fragment.ClassifyFragment;
import com.maker.use.ui.fragment.DonateFragment;
import com.maker.use.ui.fragment.HomeFragment;
import com.maker.use.ui.fragment.MessageFragment;
import com.maker.use.ui.view.MainNavigateTabBar;
import com.maker.use.utils.LoginUtils;
import com.maker.use.utils.SpUtil;
import com.maker.use.utils.UIUtils;
import com.nineoldandroids.view.ViewHelper;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;


@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewInject(R.id.dl_root)
    private DrawerLayout dl_root;
    @ViewInject(R.id.mainTabBar)
    private MainNavigateTabBar mNavigateTabBar;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNavigateTabBar.onRestoreInstanceState(savedInstanceState);

        initView();
        initUserData();
    }

    private void initUserData() {
        //在刚打开应用时，检查是否登陆过，如果有的话提取保存的用户信息进行登陆验证
        if (!SpUtil.getBoolean(ConstentValue.IS_LOGIN, false)) {
            String s = SpUtil.getString(ConstentValue.USER, "");
            if (!TextUtils.isEmpty(s)) {
                String[] results = s.split(",");
                User user = new User();
                user.id = Integer.parseInt(results[0]);
                user.username = results[1];
                user.password = results[2];
                user.sex = results[3];

                LoginUtils.login(user.username, user.password, this);
            }
        }

        //在登陆页面登陆后返回的登陆操作
        if ("login".equals(getIntent().getStringExtra("info"))) {
            dl_root.openDrawer(Gravity.LEFT);
        }

    }

    private void initView() {
        String[] tabTags = UIUtils.getStringArray(R.array.tab_tag);
        mNavigateTabBar.addTab(HomeFragment.class, new MainNavigateTabBar.TabParam(R.drawable.main_home_normal, R.drawable.main_home_selected, tabTags[0]));
        mNavigateTabBar.addTab(ClassifyFragment.class, new MainNavigateTabBar.TabParam(R.drawable.main_classify_normal, R.drawable.main_classify_selected, tabTags[1]));
        mNavigateTabBar.addTab(null, new MainNavigateTabBar.TabParam(0, 0, tabTags[2]));
        mNavigateTabBar.addTab(DonateFragment.class, new MainNavigateTabBar.TabParam(R.drawable.main_donate_normal, R.drawable.main_donate_selected, tabTags[3]));
        mNavigateTabBar.addTab(MessageFragment.class, new MainNavigateTabBar.TabParam(R.drawable.main_message_normal, R.drawable.main_message_selected, tabTags[4]));

        //侧边栏监听
        dl_root.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerStateChanged(int newState) {
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                //slideOffset-滑动（0-1）
                View mContent = dl_root.getChildAt(0);
                View mMenu = drawerView;
                float scale = 1 - slideOffset;
                float rightScale = 0.8f + scale * 0.2f;

                if (drawerView.getTag().equals("LEFT")) {

                    float leftScale = 1 - 0.3f * scale;

                    ViewHelper.setScaleX(mMenu, leftScale);
                    ViewHelper.setScaleY(mMenu, leftScale);
                    ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));
                    ViewHelper.setTranslationX(mContent,
                            mMenu.getMeasuredWidth() * (1 - scale));
                    ViewHelper.setPivotX(mContent, 0);
                    ViewHelper.setPivotY(mContent,
                            mContent.getMeasuredHeight() / 2);
                    mContent.invalidate();
                    ViewHelper.setScaleX(mContent, rightScale);
                    ViewHelper.setScaleY(mContent, rightScale);
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
            }
        });

    }

    //保存状态
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mNavigateTabBar.onSaveInstanceState(outState);
    }

    /**
     * 获取DrawerLayout去关闭打开侧边栏
     */
    public DrawerLayout getDrawerLayout() {
        return dl_root;
    }

    //发布按钮触发
    public void issue(View view) {
        startActivity(new Intent(UIUtils.getContext(), IssueActivity.class));
    }

    @Override
    public void onBackPressed() {
        SpUtil.putBoolean(ConstentValue.IS_LOGIN, false);
        super.onBackPressed();
    }
}
