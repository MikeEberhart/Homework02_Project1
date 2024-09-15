//======================================================================================
// Name: Mike Eberhart
// Date: 9-12-2024
// Desc: An app used to find both the RGB value and the Hexadecimal value too
//======================================================================================
package com.example.homework02_program1;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout cLayout;
    ListView lv_j_display_colors;
    TextView tv_j_hex_label;
    TextView tv_j_hex_display;
    TextView tv_j_red_label;
    TextView tv_j_red_progress;
    TextView tv_j_green_label;
    TextView tv_j_green_progress;
    TextView tv_j_blue_label;
    TextView tv_j_blue_progress;
    SeekBar sb_j_red_seekbar;
    SeekBar sb_j_green_seekbar;
    SeekBar sb_j_blue_seekbar;
    ImageButton btn_j_random;
    ImageButton btn_j_save_color;
    ImageButton btn_j_delete;
    ImageView iv_j_hex_underline;
    ArrayList<ColorData> main_list_of_colors;
    ColorDisplayAdapter adapter_main;
    Boolean main_text_is_black;
    int rgb_red_value;
    int rgb_green_value;
    int rgb_blue_value;
    int pos_of_selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        cLayout = findViewById(R.id.cl_v_main);
        lv_j_display_colors = findViewById(R.id.lv_v_display_colors);
        tv_j_hex_label = findViewById(R.id.tv_v_hex_label);
        tv_j_hex_display = findViewById(R.id.tv_v_hex_display);
        tv_j_red_label = findViewById(R.id.tv_v_red_label);
        tv_j_red_progress = findViewById(R.id.tv_v_red_progress);
        sb_j_red_seekbar = findViewById(R.id.sb_v_red_seekbar);
        tv_j_green_label = findViewById(R.id.tv_v_green_label);
        tv_j_green_progress = findViewById(R.id.tv_v_green_progress);
        sb_j_green_seekbar = findViewById(R.id.sb_v_green_seekbar);
        tv_j_blue_label = findViewById(R.id.tv_v_blue_label);
        tv_j_blue_progress = findViewById(R.id.tv_v_blue_progress);
        sb_j_blue_seekbar = findViewById(R.id.sb_v_blue_seekbar);
        btn_j_random = findViewById(R.id.btn_v_random);
        btn_j_save_color = findViewById(R.id.btn_v_save_color);
        btn_j_delete = findViewById(R.id.btn_v_delete);
        iv_j_hex_underline = findViewById(R.id.iv_v_hex_underline);
        rgb_red_value = 255;
        rgb_green_value = 255;
        rgb_blue_value = 255;
        pos_of_selected = 0;
        main_text_is_black = true;
        main_list_of_colors = new ArrayList<ColorData>();
        PopulateListView();
        SeekBarEvent();
        ButtonTouchEvents();
    }

    // function used to listen for the onTouch events
    private void ButtonTouchEvents() {
        btn_j_random.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    Random ran = new Random();
                    int random_red = ran.nextInt(256);
                    int random_green = ran.nextInt(256);
                    int random_blue = ran.nextInt(256);
                    SettingBackgroundAndHex(random_red, random_green, random_blue);
                    v.performClick();
                    return true;
                }
                return true;
            }
        });
        btn_j_save_color.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    SaveColorData();
                    ResetToDefault();
                    v.performClick();
                    return true;
                }
                return true;
            }
        });
        btn_j_delete.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    main_list_of_colors.remove(pos_of_selected);
                    adapter_main.notifyDataSetChanged();
                    ResetToDefault();
                    v.performClick();
                    return true;
                }
                return true;
            }
        });
        lv_j_display_colors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ColorData cd_list_pos = main_list_of_colors.get(position);
                pos_of_selected = position;
                SettingBackgroundAndHex(cd_list_pos.getRed(), cd_list_pos.getGreen(), cd_list_pos.getBlue());
                Log.d("--ITEM-CLICK--", cd_list_pos.getHex());
            }
        });
    }

    // function used to listen for the Seekbar change and to then update the variables depending on which seekbar is used
    private void SeekBarEvent() {
        sb_j_red_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_j_red_progress.setText(Integer.toString(progress));
                rgb_red_value = progress;
                ColorSwitch(rgb_red_value, rgb_green_value, rgb_blue_value);
                SettingBackgroundAndHex(rgb_red_value, rgb_green_value, rgb_blue_value);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sb_j_green_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_j_green_progress.setText(Integer.toString(progress));
                rgb_green_value = progress;
                ColorSwitch(rgb_red_value, rgb_green_value, rgb_blue_value);
                SettingBackgroundAndHex(rgb_red_value, rgb_green_value, rgb_blue_value);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sb_j_blue_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_j_blue_progress.setText(Integer.toString(progress));
                rgb_blue_value = progress;
                ColorSwitch(rgb_red_value, rgb_green_value, rgb_blue_value);
                SettingBackgroundAndHex(rgb_red_value, rgb_green_value, rgb_blue_value);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    // function used to set the background color and update the hex display and rgb displays
    private void SettingBackgroundAndHex(int r, int g, int b) {
        cLayout.setBackgroundColor(Color.rgb(r, g, b));
        String hex = String.format("#%02X%02X%02X",r,g,b);
        sb_j_red_seekbar.setProgress(r);
        sb_j_green_seekbar.setProgress(g);
        sb_j_blue_seekbar.setProgress(b);
        tv_j_hex_display.setText(hex);
    }

    // function used to switch the GUI colors back and forth from black and white depending rgb values
    private void ColorSwitch(int r, int g, int b) {
        if(r < 100 && g < 100 && b < 100) {
            tv_j_hex_label.setTextColor(Color.WHITE);
            tv_j_hex_display.setTextColor(Color.WHITE);
            tv_j_hex_display.setBackgroundResource(R.drawable.wht_sqr_transparent_label);
            iv_j_hex_underline.setImageResource(R.drawable.wht_line_label);
            tv_j_red_label.setTextColor(Color.WHITE);
            tv_j_red_progress.setTextColor(Color.WHITE);
            tv_j_green_label.setTextColor(Color.WHITE);
            tv_j_green_progress.setTextColor(Color.WHITE);
            tv_j_blue_label.setTextColor(Color.WHITE);
            tv_j_blue_progress.setTextColor(Color.WHITE);
            btn_j_random.setImageResource(R.drawable.whitedice);
            btn_j_save_color.setImageResource(R.drawable.whitesave);
            btn_j_delete.setImageResource(R.drawable.whitedelete);
            btn_j_random.setBackgroundResource(R.drawable.wht_rnd_transparent_btn);
            btn_j_save_color.setBackgroundResource(R.drawable.wht_rnd_transparent_btn);
            btn_j_delete.setBackgroundResource(R.drawable.wht_rnd_transparent_btn);
            main_text_is_black = false;
        }
        else {
            tv_j_hex_label.setTextColor(Color.BLACK);
            tv_j_hex_display.setTextColor(Color.BLACK);
            tv_j_hex_display.setBackgroundResource(R.drawable.blk_sqr_transparent_label);
            iv_j_hex_underline.setImageResource(R.drawable.blk_line_label);
            tv_j_red_label.setTextColor(Color.BLACK);
            tv_j_red_progress.setTextColor(Color.BLACK);
            tv_j_green_label.setTextColor(Color.BLACK);
            tv_j_green_progress.setTextColor(Color.BLACK);
            tv_j_blue_label.setTextColor(Color.BLACK);
            tv_j_blue_progress.setTextColor(Color.BLACK);
            btn_j_random.setImageResource(R.drawable.blackdice);
            btn_j_save_color.setImageResource(R.drawable.blacksave);
            btn_j_delete.setImageResource(R.drawable.blackdelete);
            btn_j_random.setBackgroundResource(R.drawable.blk_rnd_transparent_btn);
            btn_j_save_color.setBackgroundResource(R.drawable.blk_rnd_transparent_btn);
            btn_j_delete.setBackgroundResource(R.drawable.blk_rnd_transparent_btn);
            main_text_is_black = true;
        }
    }

    // function used to set the list view up
    private void PopulateListView() {
        adapter_main = new ColorDisplayAdapter(this, main_list_of_colors);
        lv_j_display_colors.setAdapter(adapter_main);
    }

    // function used to reset everything back to default settings when color is saved
    private void ResetToDefault() {
        int r = 255;
        int g = 255;
        int b = 255;
        cLayout.setBackgroundColor(Color.rgb(r, g, b));
        String hex = String.format("#%02X%02X%02X",r,g,b);
        sb_j_red_seekbar.setProgress(r);
        sb_j_green_seekbar.setProgress(g);
        sb_j_blue_seekbar.setProgress(b);
        tv_j_hex_display.setText(hex);
    }

    private void SaveColorData() {
        int r = rgb_red_value;
        int g = rgb_green_value;
        int b = rgb_blue_value;
        String hex = String.format("#%02X%02X%02X",r,g,b);
//        Object obj =; // trying to make sure new data doesn't already exist in the ArrayList
//        if()
//        {
//
//        }
        ColorData new_color = new ColorData();
        new_color.setHex(hex);
        new_color.setRed(r);
        new_color.setGreen(g);
        new_color.setBlue(b);
        new_color.setTextColor(main_text_is_black);
        main_list_of_colors.add(new_color);
        adapter_main.notifyDataSetChanged();
    }
}