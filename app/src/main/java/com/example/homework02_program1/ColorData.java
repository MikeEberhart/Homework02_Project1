//======================================================================================
// Name: Mike Eberhart
// Date: 9-12-2024
// Desc: An app used to find both the RGB value and the Hexadecimal value too
//======================================================================================

package com.example.homework02_program1;

public class ColorData
{
    private String cd_hex;
    private int cd_red;
    private int cd_green;
    private int cd_blue;
    private boolean cd_text_is_black;

    // Constructor // not being used but kept it anyways
    public ColorData()
    {

    }

    // Overloaded Constructor //
    public ColorData(String h, int r, int g, int b, boolean t)
    {
        cd_hex = h;
        cd_red = r;
        cd_green = g;
        cd_blue = b;
        cd_text_is_black = t;
    }

    // Getters
    public String getHex()
    {
        return cd_hex;
    }
    public int getRed()
    {
        return cd_red;
    }
    public int getGreen()
    {
        return cd_green;
    }
    public int getBlue()
    {
        return cd_blue;
    }
    public Boolean getTextColor()
    {
        return cd_text_is_black;
    }
    // Setters // not being used but kept them anyways.
    public void setHex(String h)
    {
        cd_hex = h;
    }
    public void setRed(int r)
    {
        cd_red = r;
    }
    public void setGreen(int g)
    {
        cd_green = g;
    }
    public void setBlue(int b)
    {
        cd_blue = b;
    }
    public void setTextColor(Boolean t)
    {
        cd_text_is_black = t;
    }
}
