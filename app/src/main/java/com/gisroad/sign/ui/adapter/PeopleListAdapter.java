package com.gisroad.sign.ui.adapter;
/**
 * Created by stevefat on 17-5-19.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gisroad.sign.R;
import com.gisroad.sign.module.entity.DepartEntity;
import com.gisroad.sign.module.entity.DepartUserEntity;

import java.util.List;

/**
 * Author : stevefat
 * Email :ngh8897@gmail.com
 * Created : 17-5-19 下午3:58
 */
public class PeopleListAdapter extends BaseExpandableListAdapter {
    private Context context;
    //父级的数据
    List<DepartEntity> departBeanList;
    //子级的数据
    List<List<DepartUserEntity>> departUserList;

    public PeopleListAdapter(Context context, List<DepartEntity> departBeanList, List<List<DepartUserEntity>> departUserList) {
        this.context = context;
        this.departBeanList = departBeanList;
        this.departUserList = departUserList;
    }

    @Override
    public int getGroupCount() {
        return departBeanList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return departUserList.get(groupPosition).size() > 0 ? departUserList.get(groupPosition).size() : 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return departUserList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return departUserList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.expanadle_item, parent, false);
        convertView.setPadding(40, 10, 10, 10);
        TextView name = (TextView) convertView.findViewById(R.id.depart_name);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.exp_img);

        if (departUserList.get(groupPosition).size() > 0) {
            imageView.setVisibility(View.VISIBLE);
            if (isExpanded) {
                imageView.setBackgroundResource(R.drawable.ic_up);
            } else {
                imageView.setBackgroundResource(R.drawable.ic_down);
            }

        } else {
            imageView.setVisibility(View.INVISIBLE);
        }

        name.setTextSize(16);
        name.setPadding(0, 10, 10, 10);
        String uName = departBeanList.get(groupPosition).getName();
        name.setText(uName);


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.expanadle_item, parent, false);
        convertView.setPadding(70, 10, 10, 10);
        TextView name = (TextView) convertView.findViewById(R.id.depart_name);
        TextView id = (TextView) convertView.findViewById(R.id.depart_id);
        id.setVisibility(View.VISIBLE);
        name.setTextSize(14);

        String uName = departUserList.get(groupPosition).get(childPosition).getName();
        name.setText(uName);
        String uId = departUserList.get(groupPosition).get(childPosition).getUrl();
        id.setText(uId);


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
