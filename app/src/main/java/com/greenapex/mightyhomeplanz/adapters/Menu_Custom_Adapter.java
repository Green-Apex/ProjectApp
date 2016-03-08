package com.greenapex.mightyhomeplanz.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.greenapex.R;
import com.greenapex.widgets.CustomTextView;

public class Menu_Custom_Adapter extends BaseAdapter {
    Activity activity;

    String[] menu;
    int[] menuImages = new int[]{R.drawable.ic_profile, R.drawable.ic_alerts, R.drawable.ic_projects_sidemenu, R.drawable.ic_profile,
            R.drawable.ic_past_payments_sidemenu, R.drawable.ic_logout};

    public Menu_Custom_Adapter(Activity act) {
        activity = act;
        menu = act.getResources().getStringArray(R.array.menus);

    }

    @Override
    public int getCount() {
        return menu.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint({"ViewHolder", "NewApi"})
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vi = convertView;

        holder = new ViewHolder();

        vi = inflater.inflate(R.layout.row_menu, null);
        holder.tv_menu = (CustomTextView) vi.findViewById(R.id.tvMenu_listitemMenu);

        holder.tv_menu.setText(menu[position]);

        holder.tv_menu.setCompoundDrawablesWithIntrinsicBounds(menuImages[position], 0, 0, 0);

        vi.setTag(holder);
        return vi;
    }

    class ViewHolder {
        CustomTextView tv_menu;
    }
}