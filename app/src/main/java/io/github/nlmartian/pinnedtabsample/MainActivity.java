package io.github.nlmartian.pinnedtabsample;

import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;

import io.github.nlmartian.pinnedtabsample.util.DensityUtil;


public class MainActivity extends ActionBarActivity implements ScrollTabHolder {

    private ScreenSlidePagerAdapter homeSlideAdapter;
    private PagerSlidingTabStrip pagerTab;
    private ViewPager viewPager;
    private boolean isPined = false;
    private int minHeaderTranslation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("PinedTabSample");

        minHeaderTranslation = getResources().getDimensionPixelSize(R.dimen.tabBarMarginTop) * (-1)
                + DensityUtil.getActionbarHeight(this);
        pagerTab = (PagerSlidingTabStrip) findViewById(R.id.pager_tab_bar);
        viewPager = (ViewPager) findViewById(R.id.home_slide);

        initViewPager();
    }

    private void initViewPager() {
        homeSlideAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        homeSlideAdapter.setTabHolderScrollingContent(this);
        viewPager.setAdapter(homeSlideAdapter);
        viewPager.setCurrentItem(1);
        pagerTab.setViewPager(viewPager);
        pagerTab.setDividerColorResource(android.R.color.transparent);
        pagerTab.setTypeface(null, Typeface.NORMAL);
        pagerTab.setTextSize(DensityUtil.dip2px(this, 20));
        pagerTab.setOnPageChangeListener(new HomeSideChangeListener());
    }

    @Override
    public void adjustScroll(int scrollHeight) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition) {
        if (viewPager.getCurrentItem() == pagePosition) {
            int scrollY = getScrollY(view);
            if (!isPined && -scrollY <= minHeaderTranslation) {
                isPined = true;
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xffffffff));
            } else if (isPined && -scrollY > minHeaderTranslation) {
                isPined = false;
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0x00ffffff));
            }
            pagerTab.setTranslationY(Math.max(-scrollY, minHeaderTranslation));
        }
    }

    public int getScrollY(AbsListView view) {
        View c = view.getChildAt(0);
        if (c == null) {
            return 0;
        }

        int firstVisiblePosition = view.getFirstVisiblePosition();
        int top = c.getTop();

        int headerHeight = 0;
        if (firstVisiblePosition >= 1) {
            headerHeight = getResources().getDimensionPixelSize(R.dimen.headerHeight);
        }

        return -top + firstVisiblePosition * c.getHeight() + headerHeight;
    }

    public class HomeSideChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            SparseArrayCompat<ScrollTabHolder> scrollTabHolders = homeSlideAdapter.getScrollTabHolders();
            ScrollTabHolder currentHolder = scrollTabHolders.valueAt(position);

            currentHolder.adjustScroll((int) (getResources().getDimensionPixelSize(
                    R.dimen.headerHeight) + pagerTab.getTranslationY()
                    + DensityUtil.dip2px(MainActivity.this, 17f)));
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

}
