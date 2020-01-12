package com.example.bawei.my2048;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author yaoxinhe
 * @CreateDate 2020/1/12 16:54
 * @Email 1151403054@qq.com
 */
public class GameView extends GridLayout {

    int[][] values = new int[4][4];


    Card[][] cards = new Card[4][4];


// Integer -128-127 == 大于这个数 两个对象


    public GameView(Context context) {
        super(context);
        init();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        setColumnCount(4);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        newGame();
    }

    private void newGame() {
        //ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams((int) (getResources().getDisplayMetrics().widthPixels / 4), (int) (getResources().getDisplayMetrics().widthPixels / 4));
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
// params.width = getResources().getDisplayMetrics().widthPixels / 4;
// params.height = getResources().getDisplayMetrics().widthPixels / 4;
        //Log.e("TAG", params.width + " " + params.height);
        this.removeAllViews();
        // GridLayout.LayoutParams lpa = new GridLayout.LayoutParams(lp);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                values[i][j] = 0;
                cards[i][j] = new Card(getContext(), getResources().getDisplayMetrics().widthPixels / 4);
                // cards[i][j].setLayoutParams(params);
                //cards[i][j].upLv();
                this.addView(cards[i][j]);
            }
        }
        //创建初始的两张卡
        int i = (int) (Math.random() * 16);
        int j = 0;
        do {
            j = (int) (Math.random() * 16);//0-15 15 3 3
        } while (j == i);
        Log.e("TAG", i + " " + j);
        values[i / 4][i % 4] = Math.random() * 20 < 1 ? 4 : 2;
        values[j / 4][j % 4] = Math.random() * 20 < 1 ? 4 : 2;
        setValues();
    }


    float oldx, oldy;
    int move = -1;


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                move = -1;
                oldx = x;
                oldy = y;
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(oldx - x) > Math.abs(oldy - y)) {
                    if (oldx - x > 15) { //左
                        Log.e("TAG", "---------->>>");
                        move = 1;
                    } else if (oldx - x < -15) {//右
                        Log.e("TAG", "---------->>>");
                        move = 2;
                    }
                } else {
                    if (oldy - y > 15) {
                        move = 3;
                    } else if (oldy - y < -15) {
                        move = 4;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                //记录之前的数组
                int[][] temp = new int[4][4];
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        temp[i][j] = values[i][j];
                    }
                }
                switch (move) {
                    case 1:
                        left();
                        break;
                    case 2:
                        right();
                        break;
                    case 3:
                        up();
                        break;
                    case 4:
                        down();
                        break;
                }
                setValues();
                if (move != -1) {
                    //比对当前的数组
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            if (values[i][j] != temp[i][j]) {
                                addCard();
                                return true;
                            }
                        }
                    }
                }
                //判断游戏胜利还是结束4
                if (isWin()) {
                    Toast.makeText(getContext(), "游戏胜利", Toast.LENGTH_SHORT).show();
                }
                if (isOver()) {
                    this.removeAllViews();
                    TextView tv = new TextView(getContext());
                    tv.setText("游戏结束,点击从新开始");
                    this.addView(tv);
                    tv.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            newGame();
                        }
                    });
                }
                break;
        }
        return true;
    }

    private void addCard() {
        while (true) {
            int j = (int) (Math.random() * 16);
            if (values[j / 4][j % 4] == 0) {
                values[j / 4][j % 4] = 2;
                cards[j / 4][j % 4].setNumber(2);
                return;
            }
        }
    }

    public void left() {

        //取出 4个 集合
        for (int i = 0; i < 4; i++) {
            List<Integer> list = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                int value = values[i][j];
                if (value != 0)
                    list.add(value);
            }
            //比较
            Log.e("TAG", list.toString());
            if (list.size() == 0)
                continue;
            else if (list.size() == 1) {
                values[i][0] = list.get(0);
                for (int j = 0; j < 3; j++) {
                    values[i][j + 1] = 0;
                }
            } else if (list.size() == 2) {
                if (list.get(0).equals(list.get(1))) {
                    values[i][0] = list.get(0) * 2;
                    //三个值补0
                    for (int j = 0; j < 3; j++) {
                        values[i][j + 1] = 0;
                    }
                } else {
                    values[i][0] = list.get(0);
                    values[i][1] = list.get(1);
                    values[i][2] = 0;
                    values[i][3] = 0;
                }
            } else if (list.size() == 3) {
                if (list.get(0).equals(list.get(1))) {
                    values[i][0] = list.get(0) * 2;
                    values[i][1] = list.get(2);
                    values[i][2] = 0;
                    values[i][3] = 0;
                } else if (list.get(1).equals(list.get(2))) {
                    values[i][0] = list.get(0);
                    values[i][1] = list.get(2) * 2;
                    values[i][2] = 0;
                    values[i][3] = 0;
                } else {
                    values[i][0] = list.get(0);
                    values[i][1] = list.get(1);
                    values[i][2] = list.get(2);
                    values[i][3] = 0;
                }
            } else {
                if (list.get(0).equals(list.get(1))) {
                    if (list.get(3).equals(list.get(2))) {
                        values[i][0] = list.get(0) * 2;
                        values[i][1] = list.get(2) * 2;
                        values[i][2] = 0;
                        values[i][3] = 0;
                    } else {
                        values[i][0] = list.get(0) * 2;
                        values[i][1] = list.get(2);
                        values[i][2] = list.get(3);
                        values[i][3] = 0;
                    }
                } else {
                    //1和2不相等
                    //先比对2 3 相等，不等
                    if (list.get(1).equals(list.get(2))) {
                        values[i][0] = list.get(0);
                        values[i][1] = list.get(1) * 2;
                        values[i][2] = list.get(3);
                        values[i][3] = 0;
                    } else {
                        if (list.get(2).equals(list.get(3))) {
                            values[i][0] = list.get(0);
                            values[i][1] = list.get(1);
                            values[i][2] = list.get(2) * 2;
                            values[i][3] = 0;
                        }
                    }
                }
            }
        }

    }

    private void delete() {
        // Log.e("TAG", "--------------------执行");
// //遍历
// for (int i = 0; i < 4; i++) {
//  for (int j = 0; j < 3; j++) {
//  Card card = cards[i][j];
//  Log.e("TAG", "i:" + i + " j:" + j + " num:" + card.getNumber());
//  if (card.getNumber() == 0) {
//   boolean isSub = false;
//   for (int k = j; k < 3; k++) {
//   cards[i][k].setNumber(cards[i][k + 1].getNumber());
//   if (cards[i][k + 1].getNumber() != 0) {
//    isSub = true;
//   }
//   }
//   if (isSub)
//   j--;
//   cards[i][3].setNumber(0);
//  } else if (card.getNumber() == cards[i][j + 1].getNumber()) {
//   card.upLv();
//   cards[i][j + 1].setNumber(0);
//   //后面的往前搬
//   for (int k = j + 1; k < 3; k++) {
//   cards[i][k].setNumber(cards[i][k + 1].getNumber());
//   }
//   cards[i][3].setNumber(0);
//   j--;
//  }
//  }
// }


// for (int j = 0; j < 4; j++) { //列
//  for (int i = 3; i >= 1; i--) {
//  Card card = cards[j][i];
//  if (card.getNumber() == 0) {
//   //全行左移
//   //要将
//   //如果是最后一个，不需要理会
//   continue;
//  } else {
//   //判断左边一个
//   if (cards[j][i - 1].getNumber() == 0) {
//   //从i --》i-1
//   for (int k = i - 1; k < 3; k++) {
//    cards[j][k].setNumber(cards[j][k + 1].getNumber());
//   }
//   cards[j][3].setNumber(0);
//
//   } else if (cards[j][i - 1].getNumber() == card.getNumber()) {
//   cards[j][i - 1].upLv();
//   card.setNumber(0);
//   for (int k = i; k < 3; k++) {
//    cards[j][k].setNumber(cards[j][k + 1].getNumber());
//   }
//   cards[j][3].setNumber(0);
//   }
//  }
//  }
// }
    }

    public void right() {
        mirrorH();
        left();
        mirrorH();
    }

    private void mirrorH() {
        for (int i = 0; i < 4; i++) {
            int temp = values[i][0];
            values[i][0] = values[i][3];
            values[i][3] = temp;
            temp = values[i][1];
            values[i][1] = values[i][2];
            values[i][2] = temp;
        }
    }

    public void down() {
        //左旋
        int[][] temp = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                temp[i][j] = values[3 - j][i];
            }
        }
        values = temp;
        left();
        temp = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                temp[i][j] = values[j][3 - i];
            }
        }
        values = temp;
    }

    public void up() {
        mirrorV();
        down();
        mirrorV();
    }

    private void mirrorV() {
        for (int j = 0; j < 4; j++) {
            int temp = values[0][j];
            values[0][j] = values[3][j];
            values[3][j] = temp;
            temp = values[1][j];
            values[1][j] = values[2][j];
            values[2][j] = temp;
        }
    }


    public void setValues() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(values[i][j] + " ");
                cards[i][j].setNumber(values[i][j]);
            }
            System.out.println();
        }

    }

    public boolean isWin() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (values[i][j] == 2048)
                    return true;
            }
        }
        return false;
    }

    public boolean isOver() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (values[i][j] == 0)
                    return false;
            }
        }
        //满了
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int value = values[i][j];
                if (i > 1 && value == values[i - 1][j])
                    return false;
                else if (i < 3 && value == values[i + 1][j])
                    return false;
                else if (j > 1 && value == values[i][j - 1])
                    return false;
                else if (j < 3 && value == values[i][j + 1])
                    return false;
            }
        }
        return true;
    }
}