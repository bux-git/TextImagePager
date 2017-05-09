package com.dqr.www.textimagepager.textimagepager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.dqr.www.textimagepager.R;
import com.dqr.www.textimagepager.utils.SystemBarHelper;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Description：
 * Author：LiuYM
 * Date： 2017-04-22 14:22
 */

public class ImageViewPager extends AppCompatActivity {
    public static final String IMAGES_LIST_EXTRA = "IMAGES_LIST_EXTRA";
    public static final String IMAGES_LIST_POSITION_EXTRA = "IMAGES_LIST_POSITION_EXTRA";

    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private TextView mTvDesc;

    private ViewPagerAdapter mPagerAdapter;
    private ArrayList<ImagePagerBean> mImages;
    private int mCurPosition;

    private String savePath;
    private String mCurImageUrl;


    public static void start(Context context, ArrayList<ImagePagerBean> images, int selectedPosition) {
        Intent intent = new Intent(context, ImageViewPager.class);
        intent.putExtra(IMAGES_LIST_EXTRA, images);
        intent.putExtra(IMAGES_LIST_POSITION_EXTRA, selectedPosition);
        context.startActivity(intent);
    }

    public static void start(Context context, ImagePagerBean imageBean) {
        Intent intent = new Intent(context, ImageViewPager.class);
        ArrayList<ImagePagerBean> images = (ArrayList<ImagePagerBean>) Arrays.asList(imageBean);
        intent.putExtra(IMAGES_LIST_EXTRA, images);
        intent.putExtra(IMAGES_LIST_POSITION_EXTRA, 0);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_image_view_pager_layout);

        mImages = (ArrayList<ImagePagerBean>) getIntent().getSerializableExtra(IMAGES_LIST_EXTRA);
        int position = getIntent().getIntExtra(IMAGES_LIST_POSITION_EXTRA, 0);

        mToolbar = (Toolbar) findViewById(R.id.toolBar);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mTvDesc = (TextView) findViewById(R.id.tv_desc);

        //沉浸式全屏模式
        SystemBarHelper.immersiveStatusBar(this, 0);
        SystemBarHelper.setHeightAndPadding(this, mToolbar);

        mTvDesc.setText(mImages.get(position).getDesc());
        setNumTitle(position);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //滚动条
        mTvDesc.setMovementMethod(ScrollingMovementMethod.getInstance());


        mPagerAdapter = new ViewPagerAdapter(mImages, this);
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mTvDesc.scrollTo(0, 0);
                String text = mImages.get(position + Math.round(positionOffset)).getDesc();

                if (TextUtils.isEmpty(text)) {
                    mTvDesc.setVisibility(View.GONE);
                } else {
                    mTvDesc.setVisibility(View.VISIBLE);
                    mTvDesc.setText(text);
                }
            }

            @Override
            public void onPageSelected(int position) {
                setNumTitle(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mPagerAdapter.setListener(new ViewPagerAdapter.OnViewPagerAdapterListener() {
            @Override
            public void onClickListener(View view, int position) {
                showOrHideToolbar();
                //showOrHideText();
            }

            @Override
            public void onLongClickListener(View view, int position) {
                showListDialog();
            }
        });

        mViewPager.setCurrentItem(mCurPosition);


    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        this.getMenuInflater().inflate(R.menu.menu_text_image_view_pager, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveBitmap(mCurImageUrl);
                break;
            case R.id.action_share:
                doShare();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * 分享
     */
    private void doShare() {

    }

    /**
     * 保存图片
     * @param imgUrl
     */
    private void saveBitmap(String imgUrl) {

    }


    private void showListDialog(){
        Toast.makeText(this,"长按测试!", Toast.LENGTH_SHORT).show();
    }


    private void setNumTitle(int position) {
        mCurPosition = position;
        mCurImageUrl=mImages.get(mCurPosition).getUrl();
        mToolbar.setTitle(position + 1 + "/" + mImages.size());
    }


    /**
     * 标题和文字的显示与隐藏
     */
    private void showOrHideText() {
        if (mTvDesc.getVisibility() == View.VISIBLE) {
            Animation tvTextAnimaBottom = AnimationUtils.loadAnimation(this, R.anim.anim_exit_from_bottom);

            tvTextAnimaBottom.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mTvDesc.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mTvDesc.startAnimation(tvTextAnimaBottom);

        } else {
            mTvDesc.setVisibility(View.VISIBLE);
            mTvDesc.clearAnimation();
            Animation textAnimaBottom = AnimationUtils.loadAnimation(this, R.anim.anim_enter_from_bottom);
            mTvDesc.startAnimation(textAnimaBottom);

        }
    }


    /**
     * 标题和文字的显示与隐藏
     */
    private void showOrHideToolbar() {
        if (mToolbar.getVisibility() == View.VISIBLE) {
            Animation titleAnimaTop = AnimationUtils.loadAnimation(this, R.anim.anim_exit_from_top);

            titleAnimaTop.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mToolbar.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mToolbar.startAnimation(titleAnimaTop);

        } else {
            mToolbar.setVisibility(View.VISIBLE);
            mToolbar.clearAnimation();
            Animation toolAnimaTop = AnimationUtils.loadAnimation(this, R.anim.anim_enter_from_top);
            mToolbar.startAnimation(toolAnimaTop);

        }
    }
}
