package io.github.nlmartian.pinnedtabsample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter {

    public static final int LIST_SIZE = 20;

    private int number;

    public ListAdapter(int num) {
        number = num;
    }

    @Override
    public int getCount() {
        return LIST_SIZE;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }
        TextView tvNumber = (TextView) convertView.findViewById(R.id.number);
        tvNumber.setText("tab" + number + " Item" + getItem(position));
        return convertView;
    }
}
