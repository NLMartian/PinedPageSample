package io.github.nlmartian.pinnedtabsample;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.util.SparseArray;
import android.view.ViewGroup;

public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
    private static final int NUM_SLIDES = 3;

    private SparseArrayCompat<ScrollTabHolder> mScrollTabHolders;
    SparseArray<Fragment> registeredFragments = new SparseArray<>();
    private ScrollTabHolder mListener;

    public ScreenSlidePagerAdapter(FragmentManager fm) {
        super(fm);
        mScrollTabHolders = new SparseArrayCompat<>();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence title = null;
        switch (position) {
            case 0:
                title = "tab1";
                break;
            case 1:
                title = "tab2";
                break;
            case 2:
                title = "tab3";
                break;
        }
        return title;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int i) {
        BaseScopeFragment fragment = PageFragment.newInstance(i + 1, i);

        mScrollTabHolders.put(i, fragment);
        if (mListener != null) {
            fragment.setScrollTabHolder(mListener);
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return NUM_SLIDES;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getFragmentAt(int position) {
        return registeredFragments.get(position);
    }

    public void setTabHolderScrollingContent(ScrollTabHolder listener) {
        mListener = listener;
    }

    public SparseArrayCompat<ScrollTabHolder> getScrollTabHolders() {
        return mScrollTabHolders;
    }
}
