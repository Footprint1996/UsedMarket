package com.maker.use.ui.fragment;

import android.app.ActivityOptions;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.maker.use.R;
import com.maker.use.domain.User;
import com.maker.use.global.ConstentValue;
import com.maker.use.global.UsedMarketURL;
import com.maker.use.ui.activity.CommodityListActivity;
import com.maker.use.ui.activity.LoginActivity;
import com.maker.use.ui.activity.MainActivity;
import com.maker.use.ui.activity.MyUserDetailActivity;
import com.maker.use.utils.GlideUtils;
import com.maker.use.utils.LoginUtils;
import com.maker.use.utils.SpUtil;
import com.maker.use.utils.UIUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


/**
 * 左测边栏
 * Created by XT on 2016/9/26.
 */

public class MenuLeftFragment extends Fragment implements View.OnClickListener {
    public User mUser;
    @ViewInject(R.id.iv_icon)
    ImageView iv_icon;
    @ViewInject(R.id.iv_sex)
    ImageView iv_sex;
    @ViewInject(R.id.tv_username)
    TextView tv_username;
    @ViewInject(R.id.rl_issue)
    RelativeLayout rl_issue;
    private MainActivity mActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_left, container, false);
        x.view().inject(this, view);

        initView();
        return view;
    }

    private void initView() {
        mActivity = (MainActivity) getActivity();
        iv_icon.setOnClickListener(this);
        rl_issue.setOnClickListener(this);

        LoginUtils.setOnLoginListener(new LoginUtils.onLoginListener() {
            @Override
            public void onLogin(User user) {
                mUser = user;
                //用户名
                tv_username.setText("你好，" + user.getUsername());
                //头像
                GlideUtils.setCircleImageViewImg(mActivity, UsedMarketURL.HEAD + mUser.getNarrowHeadPortraitPath(), iv_icon);
                //用户性别
                if (user.getSex() == 1) {
                    iv_sex.setImageResource(R.drawable.sex_man);
                } else {
                    iv_sex.setImageResource(R.drawable.sex_woman);
                }

                UIUtils.toast("登陆成功");
                //更新登陆状态
                SpUtil.putBoolean(ConstentValue.IS_LOGIN, true);

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_icon:
                if (SpUtil.getBoolean(ConstentValue.IS_LOGIN, false)) {
                    Intent intent = new Intent(UIUtils.getContext(), MyUserDetailActivity.class);
                    intent.putExtra("user", mUser);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(UIUtils.getContext(), LoginActivity.class);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(mActivity).toBundle());
                    } else {
                        startActivity(intent);
                    }
                }
                break;
            case R.id.rl_issue:
                if (SpUtil.getBoolean(ConstentValue.IS_LOGIN, false)) {
                    Intent intent = new Intent(UIUtils.getContext(), CommodityListActivity.class);
                    intent.putExtra("type", "t_commodity.user_id");
                    intent.putExtra("queryValue", SpUtil.getUserId());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(mActivity).toBundle());
                    } else {
                        startActivity(intent);
                    }
                } else {
                    UIUtils.toast("请先登陆");
                    Intent intent = new Intent(UIUtils.getContext(), LoginActivity.class);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(mActivity).toBundle());
                    } else {
                        startActivity(intent);
                    }
                }
                break;
            case R.id.rl_donate:
                if (SpUtil.getBoolean(ConstentValue.IS_LOGIN, false)) {
                } else {
                    UIUtils.toast("请先登陆");
                    Intent intent = new Intent(UIUtils.getContext(), LoginActivity.class);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(mActivity).toBundle());
                    } else {
                        startActivity(intent);
                    }
                }
                break;
        }
    }

}
