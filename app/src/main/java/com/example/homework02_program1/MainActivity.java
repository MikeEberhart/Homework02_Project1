//======================================================================================
// Name: Mike Eberhart
// Date: 9-12-2024
// Desc: An app used to find both the RGB value and the Hexadecimal value too
//======================================================================================
package com.example.homework02_program1;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity
{
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
    TextView tv_j_error_display;
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
    Boolean main_color_selected;
    int rgb_red_value;
    int rgb_green_value;
    int rgb_blue_value;
    int pos_of_selected;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        cLayout = findViewById(R.id.cl_v_main);
        lv_j_display_colors = findViewById(R.id.lv_v_display_colors);
        tv_j_hex_label = findViewById(R.id.tv_v_hex_label);
        tv_j_hex_display = findViewById(R.id.tv_v_hex_display);
        tv_j_red_label = findViewById(R.id.tv_v_red_label);
        tv_j_red_progress = findViewById(R.id.tv_v_red_progress);
        tv_j_error_display = findViewById(R.id.tv_v_error_display);
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
        tv_j_error_display.setVisibility(View.INVISIBLE);
        rgb_red_value = 255;
        rgb_green_value = 255;
        rgb_blue_value = 255;
        pos_of_selected = 0;
        main_text_is_black = true;
        main_color_selected = false;
        main_list_of_colors = new ArrayList<ColorData>();
        PopulateListView();
        SeekBarEvent();
        ButtonClickEvents();
    }

    // function used to listen for the onTouch events
    private void ButtonClickEvents()
    {
        btn_j_random.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Random ran = new Random();
                int random_red = ran.nextInt(256);
                int random_green = ran.nextInt(256);
                int random_blue = ran.nextInt(256);
                SettingBackgroundAndHex(random_red, random_green, random_blue);
                main_color_selected = false;
            }
        });
        btn_j_save_color.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SaveColorData();
                SettingBackgroundAndHex(255,255,255);
                main_color_selected = false;
            }
        });
        btn_j_delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(main_color_selected)
                {
                    main_list_of_colors.remove(pos_of_selected);
                    adapter_main.notifyDataSetChanged();
                    SettingBackgroundAndHex(255,255,255);
                    main_color_selected = false;
                }
            }
        });
        lv_j_display_colors.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                ColorData cd_list_pos = main_list_of_colors.get(position);
                pos_of_selected = position;
                main_color_selected = true;
                SettingBackgroundAndHex(cd_list_pos.getRed(), cd_list_pos.getGreen(), cd_list_pos.getBlue());
                Log.d("--ITEM-CLICK--", cd_list_pos.getHex());
            }
        });
    }

    // function used to listen for the Seekbar change and to then update the variables depending on which seekbar is used
    private void SeekBarEvent()
    {
        sb_j_red_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                tv_j_red_progress.setText(Integer.toString(progress));
                rgb_red_value = progress;
                GuiColorSwitch(rgb_red_value, rgb_green_value, rgb_blue_value);
                SettingBackgroundAndHex(rgb_red_value, rgb_green_value, rgb_blue_value);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });
        sb_j_green_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                tv_j_green_progress.setText(Integer.toString(progress));
                rgb_green_value = progress;
                GuiColorSwitch(rgb_red_value, rgb_green_value, rgb_blue_value);
                SettingBackgroundAndHex(rgb_red_value, rgb_green_value, rgb_blue_value);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });
        sb_j_blue_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                tv_j_blue_progress.setText(Integer.toString(progress));
                rgb_blue_value = progress;
                GuiColorSwitch(rgb_red_value, rgb_green_value, rgb_blue_value);
                SettingBackgroundAndHex(rgb_red_value, rgb_green_value, rgb_blue_value);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });
    }

    // function used to populate the listview with data
    private void PopulateListView()
    {
        adapter_main = new ColorDisplayAdapter(this, main_list_of_colors);
        lv_j_display_colors.setAdapter(adapter_main);
    }

    // function used to set the background color and update the hex display and rgb displays
    private void SettingBackgroundAndHex(int r, int g, int b)
    {
        cLayout.setBackgroundColor(Color.rgb(r, g, b));
        String hex = String.format("#%02X%02X%02X",r,g,b);
        sb_j_red_seekbar.setProgress(r);
        sb_j_green_seekbar.setProgress(g);
        sb_j_blue_seekbar.setProgress(b);
        tv_j_hex_display.setText(hex);
    }

    // function used to switch the GUI colors back and forth from black and white depending rgb values
    // I made the threshold for switch the colors back and forth at 120 because even at 100
    // solid colors it seemed hard to see the black GUI, mainly the solid blue.
    private void GuiColorSwitch(int r, int g, int b)
    {
        if(r < 120 && g < 120 && b < 120)
        {
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
        else
        {
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

    // function used to check and see if the color attempting to be saved is already saved in list of colors
    // Android Studio suggested to use @NonNull
    @NonNull
    private Boolean ContainsColorAlready(int red, int green, int blue)
    {
        Log.d("---ContainsColor---", "ContainsColor");
        String hex = String.format("#%02X%02X%02X", red, green, blue);
        for (ColorData cd_hex : main_list_of_colors)
        {
            if (cd_hex.getHex().equals(hex))
            {
                return true;
            }
        }
        return false;
    }

    // function used to save the color to the list of colors
    private void SaveColorData()
    {
        int r = rgb_red_value;
        int g = rgb_green_value;
        int b = rgb_blue_value;
        if(!ContainsColorAlready(r,g,b))
        {
            String hex = String.format("#%02X%02X%02X",r,g,b);
            ColorData new_color = new ColorData(hex,r,g,b,main_text_is_black);
            main_list_of_colors.add(new_color);
            adapter_main.notifyDataSetChanged();
            Log.d("---SaveColorData---","SaveColorData");
        }
        else
        {
            ErrorMessage();
            //PopUpError();
        }
    }

    // function used to show the error message for 2 seconds before setting it to invisible again
    // after some research and reading I found this postDelayed method
    // was trying to use wait() with a try-catch like android studio was suggesting
    // along with some other ideas but nothing really worked like how I needed it to
    private void ErrorMessage()
    {
        tv_j_error_display.setVisibility(View.VISIBLE);
        tv_j_error_display.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                tv_j_error_display.setVisibility(View.INVISIBLE);
            }
        }, 2000);
    }

//    thought about using this for an error message as well but liked the way ErrorMessaged looked over this
//    private void PopUpError()
//    {
//        Toast.makeText(this,"ERROR: COLOR ALREADY EXISTS", Toast.LENGTH_SHORT).show();
//    }
}