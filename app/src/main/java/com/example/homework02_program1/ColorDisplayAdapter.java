//======================================================================================
// Name: Mike Eberhart
// Date: 9-12-2024
// Desc: An app used to find both the RGB value and the Hexadecimal value too
//======================================================================================
package com.example.homework02_program1;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ColorDisplayAdapter extends BaseAdapter
{

    Context cda_context_main;
    ArrayList<ColorData> cda_color_data_list;

    public ColorDisplayAdapter(Context c, ArrayList<ColorData> cd)
    {
        cda_context_main = c;
        cda_color_data_list = cd;
    }

    @Override
    public int getCount()
    {
        return cda_color_data_list.size();
    }

    @Override
    public Object getItem(int i)
    {
        return cda_color_data_list.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup group)
    {
        if(view == null)
        {
            LayoutInflater mInflater = (LayoutInflater) cda_context_main.getSystemService(MainActivity.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.color_data_cell, null);
        }
        ColorData cd = cda_color_data_list.get(i);
        TextView tv_j2_hex_label = view.findViewById(R.id.tv_v2_hex_label);
        TextView tv_j2_hex_display = view.findViewById(R.id.tv_v2_hex_display);
        TextView tv_j2_red_label = view.findViewById(R.id.tv_v2_red_label);
        TextView tv_j2_red_progress = view.findViewById(R.id.tv_v2_red_progress);
        TextView tv_j2_green_label = view.findViewById(R.id.tv_v2_green_label);
        TextView tv_j2_green_progress = view.findViewById(R.id.tv_v2_green_progress);
        TextView tv_j2_blue_label = view.findViewById(R.id.tv_v2_blue_label);
        TextView tv_j2_blue_progress = view.findViewById(R.id.tv_v2_blue_progress);
        view.setBackgroundColor(Color.rgb(cd.getRed(), cd.getGreen(), cd.getBlue()));
        tv_j2_hex_display.setText(cd.getHex());
        tv_j2_red_progress.setText(Integer.toString(cd.getRed()));
        tv_j2_green_progress.setText(Integer.toString(cd.getGreen()));
        tv_j2_blue_progress.setText(Integer.toString(cd.getBlue()));
        if(cd.getTextColor())
        {
            tv_j2_hex_label.setTextColor(Color.BLACK);
            tv_j2_red_label.setTextColor(Color.BLACK);
            tv_j2_green_label.setTextColor(Color.BLACK);
            tv_j2_blue_label.setTextColor(Color.BLACK);
            tv_j2_hex_display.setTextColor(Color.BLACK);
            tv_j2_red_progress.setTextColor(Color.BLACK);
            tv_j2_green_progress.setTextColor(Color.BLACK);
            tv_j2_blue_progress.setTextColor(Color.BLACK);
        }
        else
        {
            tv_j2_hex_label.setTextColor(Color.WHITE);
            tv_j2_red_label.setTextColor(Color.WHITE);
            tv_j2_green_label.setTextColor(Color.WHITE);
            tv_j2_blue_label.setTextColor(Color.WHITE);
            tv_j2_hex_display.setTextColor(Color.WHITE);
            tv_j2_red_progress.setTextColor(Color.WHITE);
            tv_j2_green_progress.setTextColor(Color.WHITE);
            tv_j2_blue_progress.setTextColor(Color.WHITE);
        }
        return view;
    }
}
