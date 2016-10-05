package com.maker.use.ui.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.maker.use.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 发布页面
 * Created by XT on 2016/10/4.
 */
@ContentView(R.layout.activity_issue)
public class IssueActivity extends Activity {

    @ViewInject(R.id.iv_root)
    ImageView iv_root;
    @ViewInject(R.id.ll_collection)
    LinearLayout ll_collection;
    @ViewInject(R.id.ll_issue)
    LinearLayout ll_issue;
    private int mScreenWidth;
    private int mScreenHeight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        x.view().inject(this);

        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mScreenWidth = dm.widthPixels;
        mScreenHeight = dm.heightPixels;

        initAnim();
    }

    private void initAnim() {
        // 缩放动画
        PropertyValuesHolder p2 = PropertyValuesHolder.ofFloat("ScaleX", 1.0f, 1.5f, 1.0f, 2.0f, 1.0f, 1.0f, 1.5f, 1.0f);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(iv_root, p2);
        objectAnimator.setDuration(500).start();
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ll_issue.setVisibility(View.VISIBLE);
                ll_collection.setVisibility(View.VISIBLE);

                PropertyValuesHolder p1 = PropertyValuesHolder.ofFloat("ScaleY", 0.1f, 1.0f);
                PropertyValuesHolder p2 = PropertyValuesHolder.ofFloat("ScaleX", 0.1f, 1.0f);
                ObjectAnimator.ofPropertyValuesHolder(ll_issue, p1, p2).setDuration(500).start();
                ObjectAnimator.ofPropertyValuesHolder(ll_collection, p1, p2).setDuration(500).start();

                ll_collection.animate().x(mScreenWidth / 2 - ll_collection.getWidth() * 2).y(mScreenHeight / 2);
                ll_issue.animate().x(mScreenWidth / 2 + ll_issue.getWidth()).y(mScreenHeight / 2);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void dismiss(View view) {
        dismissAnim();
    }

    @Override
    public void onBackPressed() {
        dismissAnim();
    }

    private void dismissAnim() {
        PropertyValuesHolder p1 = PropertyValuesHolder.ofFloat("ScaleY", 1.0f, 0.0f);
        PropertyValuesHolder p2 = PropertyValuesHolder.ofFloat("ScaleX", 1.0f, 0.0f);
        ObjectAnimator.ofPropertyValuesHolder(ll_issue, p1, p2).setDuration(500).start();
        ObjectAnimator.ofPropertyValuesHolder(ll_collection, p1, p2).setDuration(500).start();
        ll_collection.animate().x(mScreenWidth / 2 - ll_collection.getWidth() / 2).y(mScreenHeight - iv_root.getHeight() * 2);
        ll_issue.animate().x(mScreenWidth / 2 - ll_collection.getWidth() / 2).y(mScreenHeight - iv_root.getHeight() * 2).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ll_issue.setVisibility(View.INVISIBLE);
                ll_collection.setVisibility(View.INVISIBLE);
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
