package io.github.nlmartian.pinnedtabsample;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import io.github.nlmartian.pinnedtabsample.util.DensityUtil;

public class PageFragment extends BaseScopeFragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final String ARG_NUM = "arg_num";

    private int number;

    private Handler handler;

    public static PageFragment newInstance(int num, int pos) {
        PageFragment f = new PageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_NUM, num);
        args.putInt(ARG_POSITION, pos);
        f.setArguments(args);
        return f;
    }

    public PageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            number = getArguments().getInt(ARG_NUM);
        }
        handler = new Handler();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_page, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(0xff00ffff, 0xffff00ff, 0xffffff00, 0xffffffff);
        int startOffet = getResources().getDimensionPixelSize(R.dimen.headerHeight);
        int endOffet = getResources().getDimensionPixelSize(R.dimen.headerHeight)
                + DensityUtil.dip2px(getActivity(), 40f);
        swipeRefreshLayout.setProgressViewOffset(true, startOffet, endOffet);
        swipeRefreshLayout.setOnRefreshListener(this);

        listBg = view.findViewById(R.id.list_bg_white);
        listView = (ListView) view.findViewById(R.id.listview);
        setupListView();
        listView.setAdapter(new ListAdapter(number));
    }

    @Override
    public void onRefresh() {
        delayHideRefreshEffect();
    }

    @Override
    protected void loadMore() {
        super.loadMore();
        swipeRefreshLayout.setRefreshing(true);

        delayHideRefreshEffect();
    }

    private void delayHideRefreshEffect() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);
    }
}
