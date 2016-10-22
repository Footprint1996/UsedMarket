package com.maker.use.ui.fragment.homeFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.widget.HeaderScrollHelper;
import com.lzy.widget.loop.LoopViewPager;
import com.lzy.widget.tab.CircleIndicator;
import com.maker.use.R;
import com.maker.use.domain.Top;
import com.maker.use.global.UsedMarketURL;
import com.maker.use.ui.activity.MainActivity;
import com.maker.use.ui.fragment.BaseFragment;
import com.maker.use.ui.view.MyXRecyclerView;
import com.maker.use.utils.UIUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 二手界面
 * Created by XT on 2016/10/21.
 */

public class UsedFragment extends BaseFragment implements HeaderScrollHelper.ScrollableContainer, View.OnClickListener {

    @ViewInject(R.id.cl_root)
    CoordinatorLayout cl_root;
    //hobbyView
    @ViewInject(R.id.ll_hobby_1)
    LinearLayout ll_hobby_1;
    @ViewInject(R.id.ll_hobby_2)
    LinearLayout ll_hobby_2;
    @ViewInject(R.id.ll_hobby_3)
    LinearLayout ll_hobby_3;
    @ViewInject(R.id.ll_hobby_4)
    LinearLayout ll_hobby_4;
    @ViewInject(R.id.ll_hobby_5)
    LinearLayout ll_hobby_5;
    @ViewInject(R.id.ll_hobby_6)
    LinearLayout ll_hobby_6;
    @ViewInject(R.id.iv_hobby_1)
    ImageView iv_hobby_1;
    @ViewInject(R.id.tv_hobby_1)
    TextView tv_hobby_1;
    @ViewInject(R.id.iv_hobby_2)
    ImageView iv_hobby_2;
    @ViewInject(R.id.tv_hobby_2)
    TextView tv_hobby_2;
    @ViewInject(R.id.iv_hobby_3)
    ImageView iv_hobby_3;
    @ViewInject(R.id.tv_hobby_3)
    TextView tv_hobby_3;
    @ViewInject(R.id.iv_hobby_4)
    ImageView iv_hobby_4;
    @ViewInject(R.id.tv_hobby_4)
    TextView tv_hobby_4;
    @ViewInject(R.id.iv_hobby_5)
    ImageView iv_hobby_5;
    @ViewInject(R.id.tv_hobby_5)
    TextView tv_hobby_5;
    @ViewInject(R.id.iv_hobby_6)
    ImageView iv_hobby_6;
    @ViewInject(R.id.tv_hobby_6)
    TextView tv_hobby_6;
    @ViewInject(R.id.pagerHeader)
    private LoopViewPager pagerHeader;
    @ViewInject(R.id.ci)
    private CircleIndicator ci;
    private ArrayList<Top.img> mImgs;
    private MyXRecyclerView mMyXRecyclerView;
    private MainActivity mActivity;
    private View mMainView;
    private String[] mTitleArray = {"#游戏专题","#考研必备","#音乐就是生命","#电子爱好者","#摄影艺术家","#嘻哈一族"};
    private int[] mImgArray = {
            R.mipmap.hobby_1, R.mipmap.hobby_2,
            R.mipmap.hobby_3, R.mipmap.hobby_4,
            R.mipmap.hobby_5, R.mipmap.hobby_6,
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMainView = inflater.inflate(R.layout.fragment_used, null);
        x.view().inject(this, mMainView);

        initView();
        return mMainView;
    }

    public void initView() {
        mActivity = (MainActivity) getActivity();

        mActivity.setOnFragmentChangeListener(new MainActivity.onFragmentChangeListener() {
            @Override
            public void onFragmentChange() {
                pagerHeader.setAutoLoop(false, 0);
            }

            @Override
            public void onFragmentIsHomeFragment() {
                pagerHeader.setAutoLoop(true, 3000);
            }
        });

        //添加MyXRecyclerView
        HashMap<String, String> map = new HashMap<>();
        map.put("all", "all");
        mMyXRecyclerView = new MyXRecyclerView(mActivity, map, cl_root);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mMyXRecyclerView.setLayoutParams(layoutParams);
        cl_root.addView(mMyXRecyclerView, 0, layoutParams);

        //添加头布局（轮播图)
        final View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.list_item_header_home_page, (ViewGroup) getActivity().findViewById(android.R.id.content), false);
        x.view().inject(this, headerView);
        getDataFromServer();
        mMyXRecyclerView.addHeaderView(headerView);

        //添加头布局（店铺）
        View hobbyView = LayoutInflater.from(getActivity()).inflate(R.layout.list_item_header_used_hobby, (ViewGroup) getActivity().findViewById(android.R.id.content), false);
        x.view().inject(this, hobbyView);
        initHobbyView();
        mMyXRecyclerView.addHeaderView(hobbyView);
        //添加监听，在用户滑动到下面时停止图片轮播，节省ui刷新
        mMyXRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                View newView = mMyXRecyclerView.getChildAt(1);

                if (newView != null && newView != headerView) {
                    pagerHeader.setAutoLoop(false, 0);
                } else {
                    pagerHeader.setAutoLoop(true, 3000);
                }
            }
        });
    }

    private void initHobbyView() {
        ll_hobby_1.setOnClickListener(this);
        ll_hobby_2.setOnClickListener(this);
        ll_hobby_3.setOnClickListener(this);
        ll_hobby_4.setOnClickListener(this);
        ll_hobby_5.setOnClickListener(this);
        ll_hobby_6.setOnClickListener(this);

        iv_hobby_1.setImageResource(mImgArray[0]);
        iv_hobby_2.setImageResource(mImgArray[1]);
        iv_hobby_3.setImageResource(mImgArray[2]);
        iv_hobby_4.setImageResource(mImgArray[3]);
        iv_hobby_5.setImageResource(mImgArray[4]);
        iv_hobby_6.setImageResource(mImgArray[5]);
        tv_hobby_1.setText(mTitleArray[0]);
        tv_hobby_2.setText(mTitleArray[1]);
        tv_hobby_3.setText(mTitleArray[2]);
        tv_hobby_4.setText(mTitleArray[3]);
        tv_hobby_5.setText(mTitleArray[4]);
        tv_hobby_6.setText(mTitleArray[5]);
    }

    /**
     * 从服务器上获取数据
     */
    private void getDataFromServer() {
        //获取TOP10图片的地址
        String url = UsedMarketURL.VPImgUrl;
        x.http().get(new RequestParams(url), new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                UIUtils.toast("网络出错啦~");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 解析从服务器获取的JSON数据
     *
     * @param result
     */
    private void processData(String result) {
        Gson gson = new Gson();
        Top top = gson.fromJson(result, Top.class);
        mImgs = top.imgs;
        pagerHeader.setAdapter(new HeaderAdapter());
        ci.setViewPager(pagerHeader);
    }

    @Override
    public View getScrollableView() {
        return mMyXRecyclerView;
    }

    @Override
    public void onStop() {
        super.onStop();
        pagerHeader.setAutoLoop(false, 0);
    }

    @Override
    public void onResume() {
        super.onResume();
        pagerHeader.setAutoLoop(true, 3000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_hobby_1:
                UIUtils.snackBar(v, tv_hobby_1.getText().toString());
                break;
            case R.id.ll_hobby_2:
                UIUtils.snackBar(v, tv_hobby_2.getText().toString());
                break;
            case R.id.ll_hobby_3:
                UIUtils.snackBar(v, tv_hobby_3.getText().toString());
                break;
            case R.id.ll_hobby_4:
                UIUtils.snackBar(v, tv_hobby_4.getText().toString());
                break;
            case R.id.ll_hobby_5:
                UIUtils.snackBar(v, tv_hobby_5.getText().toString());
                break;
            case R.id.ll_hobby_6:
                UIUtils.snackBar(v, tv_hobby_6.getText().toString());
                break;
        }
    }

    private class HeaderAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            x.image().bind(imageView, UsedMarketURL.url_heart + mImgs.get(position).imgUrl);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mImgs.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

}
