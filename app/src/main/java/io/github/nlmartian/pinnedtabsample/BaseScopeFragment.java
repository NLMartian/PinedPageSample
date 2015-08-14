package io.github.nlmartian.pinnedtabsample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

public class BaseScopeFragment extends Fragment implements ScrollTabHolder,
        AbsListView.OnScrollListener {
    public static final String ARG_POSITION = "arg_position";

    private View header;
    protected View listBg;
    protected ListView listView;
    protected View emptyView;
    protected SwipeRefreshLayout swipeRefreshLayout;

    private boolean moveToBottom = false;
    private int previous = 0;


    protected int minHeaderTranslation;
    protected int mPosition;

    protected ListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        minHeaderTranslation = getResources().getDimensionPixelSize(R.dimen.tabBarMarginTop) * (-1);
        mPosition = getArguments().getInt(ARG_POSITION);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView.setOnScrollListener(this);
    }

    protected void setupListView() {
        header = getActivity().getLayoutInflater().inflate(R.layout.layout_main_list_header, listView, false);
        listView.addHeaderView(header, null, false);
    }

    public int getScrollY() {
        View c = listView.getChildAt(0);
        if (c == null) {
            return 0;
        }

        int firstVisiblePosition = listView.getFirstVisiblePosition();
        int top = c.getTop();

        int headerHeight = 0;
        if (firstVisiblePosition >= 1) {
            headerHeight = header.getHeight();
        }

        return -top + firstVisiblePosition * c.getHeight() + headerHeight;
    }

    protected ScrollTabHolder mScrollTabHolder;

    public void setScrollTabHolder(ScrollTabHolder scrollTabHolder) {
        mScrollTabHolder = scrollTabHolder;
    }

    @Override
    public void adjustScroll(int scrollHeight) {
        if (scrollHeight == 0 && listView.getFirstVisiblePosition() >= 1) {
            return;
        }
        listView.setSelectionFromTop(1, scrollHeight);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition) {
        // do nothing
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {}

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (mScrollTabHolder != null) {
            mScrollTabHolder.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount, mPosition);
        }
        int scrollY = getScrollY();
        listBg.setTranslationY(Math.max(-scrollY, minHeaderTranslation));

        if (previous < firstVisibleItem) {
            moveToBottom = true;
        } else if (previous > firstVisibleItem) {
            moveToBottom = false;
        }
        previous = firstVisibleItem;

        if (totalItemCount == firstVisibleItem + visibleItemCount && moveToBottom) {
            // TODO: 加载更多
            swipeRefreshLayout.setRefreshing(true);
        }
    }
}
