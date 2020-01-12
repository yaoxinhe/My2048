package com.example.bawei.my2048;


import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;
/**
 * @Author yaoxinhe
 * @CreateDate 2020/1/12 16:53
 * @Email 1151403054@qq.com
 */

public class Card extends FrameLayout {

    //2
    TextView tv;

    private int number = 0;
    int width;

    public Card(Context context, int width) {
        super(context);
        this.width = width;
        init();
    }


    private void init() {
        tv = new TextView(getContext());
        setPadding(5, 5, 5, 5);
        FrameLayout.LayoutParams lp = new LayoutParams(width - 10, width - 10);
        tv.setLayoutParams(lp);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(48);
        this.addView(tv);
        setColor();
    }

    public void setNumber(int number) {
        this.number = number;
        if (number == 0)
            tv.setText("");
        else
            tv.setText(number + "");
        setColor();
    }

    @Override
    public String toString() {
        return "Card{" +
                "tv=" + tv +
                ", number=" + number +
                ", width=" + width +
                '}';
    }

    private void setColor() {
        switch (number) {
            case 0:
                tv.setBackgroundColor(getResources().getColor(R.color.c0));
                break;
            case 2:
                tv.setBackgroundColor(getResources().getColor(R.color.c2));
                break;
            case 4:
                tv.setBackgroundColor(getResources().getColor(R.color.c4));
                break;
            case 8:
                tv.setBackgroundColor(getResources().getColor(R.color.c8));
                break;
            case 16:
                tv.setBackgroundColor(getResources().getColor(R.color.c16));
                break;
            case 32:
                tv.setBackgroundColor(getResources().getColor(R.color.c32));
                break;
            case 64:
                tv.setBackgroundColor(getResources().getColor(R.color.c64));
                break;
            case 128:
                tv.setBackgroundColor(getResources().getColor(R.color.c128));
                break;
            case 256:
                tv.setBackgroundColor(getResources().getColor(R.color.c256));
                break;
            case 512:
                tv.setBackgroundColor(getResources().getColor(R.color.c512));
                break;
            case 1024:
                tv.setBackgroundColor(getResources().getColor(R.color.c1024));
                break;
            case 2048:
                tv.setBackgroundColor(getResources().getColor(R.color.c2048));
                break;
        }
    }

    public int getNumber() {
        return number;
    }

}
