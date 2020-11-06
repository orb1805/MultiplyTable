package e.roman.multiplytable;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.constraintlayout.widget.ConstraintLayout;

        import android.graphics.Color;
        import android.os.Bundle;
        import android.os.Handler;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import java.util.Random;

public class CheckAll extends AppCompatActivity implements View.OnClickListener{
    TextView textRight, textLeft, textRW;
    EditText ans;
    ConstraintLayout back;
    int[] numbers;
    int[] numbers2;
    int j, p;
    int exs;
    Button buttonAnswer;
    Handler handler;
    boolean play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_all);
        handler = new Handler();
        ans = (EditText)findViewById(R.id.answer1);
        textLeft = (TextView)findViewById(R.id.textLeft);
        numbers = new int[8];
        numbers2 = new int[8];
        randomize();
        textRight = (TextView)findViewById(R.id.textRight);
        textRW = (TextView)findViewById(R.id.textRW);
        buttonAnswer = (Button)findViewById(R.id.buttonAnswer);
        buttonAnswer.setOnClickListener(this); /////
        textLeft.setText(""+numbers2[p]);
        textRight.setText(""+numbers[j]);
        textRW.setText("");
        back = (ConstraintLayout)findViewById(R.id.back);
        back.setOnTouchListener(new OnSwipeTouchListener(CheckAll.this) {
            public void onSwipeRight(){
                finish();
            }
        });
        exs = 1;
        play = true;
    }

    @Override
    public void onClick(View view) {
        if(exs == 1)
        {
            check();
        }
        else
        {
            buttonAnswer.setTextSize(14);
            buttonAnswer.setText("Ответить");
            buttonAnswer.setBackgroundResource(R.drawable.roundedbutton1);
            buttonAnswer.setTextColor(Color.WHITE);
            rightSecond();
            show();
            exs = 1;
        }
    }
    public void randomize() {
        Random randInt = new Random();
        j = 0;
        for (int i = 0; i <= 7; i++) {
            numbers[i] = 0;
        }
        for (int i = 2; i < 10; i++) {
            j = randInt.nextInt(8);
            if (numbers[j] == 0) {
                numbers[j] = i;
            } else {
                while (numbers[j] != 0) {
                    j++;
                    j %= 8;
                }
                numbers[j] = i;
            }
        }
        j = 0;
        p = 0;
        for (int i = 0; i <= 7; i++) {
            numbers2[i] = 0;
        }
        for (int i = 2; i < 10; i++) {
            p = randInt.nextInt(8);
            if (numbers2[p] == 0) {
                numbers2[p] = i;
            } else {
                while (numbers2[p] != 0) {
                    p++;
                    p %= 8;
                }
                numbers2[p] = i;
            }
        }
        p = 0;
    }
    public void check() {
        int answer;
        String answ;
        answ = ("" + ans.getText());
        if (symbol_check(answ)) {
            answer = Integer.parseInt("" + ans.getText());
            if(answer == numbers2[p] * numbers[j]) {
            rightFirst();
            buttonAnswer.setTextSize(12);
            buttonAnswer.setText("Следующий");
            buttonAnswer.setTextColor(Color.BLACK);
            buttonAnswer.setBackgroundResource(R.drawable.roundedbutton2);
            exs = 0;
            if (p == 7) {
                p += 2;
                p %= 8;
                j++;
                j %= 8;
            } else {
                j++;
                j %= 8;
                p++;
            }
            if (j == 0 && p == 0)
                randomize();
        } else {
            wrong();
            ans.setText("");
        }
    }
    }
    public void show()
    {
        textRight.setText(""+numbers[j]);
        textLeft.setText(""+numbers2[p]);
    }
    public void wrong()
    {
        if(play) {
            play = false;
            textRW.setTextSize(42);
            textRW.setTextColor(Color.parseColor("#D7CCC8"));
            textRW.setText("НЕПРАВИЛЬНО");
            int r, g, b;
            r = 215;
            g = 204;
            b = 200;
            for (int i = 0; i < 1000; i += 50) {
                r -= 0.25;
                g -= 8;
                b -= 8;
                final String color = "#" + change(r) + change(g) + change(b);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textRW.setTextColor(Color.parseColor(color));
                    }
                }, i);
            }
            for (int i = 0; i < 1000; i += 50) {
                r += 0.25;
                g += 8;
                b += 8;
                final String color = "#" + change(r) + change(g) + change(b);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textRW.setTextColor(Color.parseColor(color));
                    }
                }, i + 1000);
            }
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setText("");
                    play = true;
                }
            }, 2000);
        }
        else
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    wrong();
                }
            }, 500);
    }
    public void rightFirst()
    {
        if(play)
        {
            play = false;
            textRW.setTextSize(50);
            textRW.setText("ПРАВИЛЬНО");
            textRW.setTextColor(Color.parseColor("#D7CCC8"));
            int r, g, b;
            r = 215;
            g = 204;
            b = 200;
            for (int i = 0; i < 1000; i += 50) {
                r -= 8;
                g -= 4;
                b -= 7;
                final String color = "#" + change(r) + change(g) + change(b);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textRW.setTextColor(Color.parseColor(color));
                    }
                }, i);
            }
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#2E7D32"));
                    play = true;
                }
            }, 1000);
        }
        else
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    rightFirst();
                }
            }, 500);
    }
    public  void rightSecond()
    {
        if(play)
        {
            play = false;
            ans.setText("");
            int r, g, b;
            r = 46;
            g = 125;
            b = 50;
            for (int i = 0; i < 1000; i += 50) {
                r += 8;
                g += 4;
                b += 7;
                final String color = "#" + change(r) + change(g) + change(b);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textRW.setTextColor(Color.parseColor(color));
                    }
                }, i);
            }
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#D7CCC8"));
                    play = true;
                }
            }, 1000);
        }
        else
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    rightSecond();
                }
            }, 500);
    }
    public String change(int i)
    {
        String res = "";
        int p = 0;
        while (i > 0)
        {
            switch (i % 16)
            {
                case  0:
                    res = "0" + res;
                    break;
                case 1:
                    res = "1" + res;
                    break;
                case 2:
                    res = "2" + res;
                    break;
                case 3:
                    res = "3" + res;
                    break;
                case 4:
                    res = "4" + res;
                    break;
                case 5:
                    res = "5" + res;
                    break;
                case 6:
                    res = "6" + res;
                    break;
                case 7:
                    res = "7" + res;
                    break;
                case 8:
                    res = "8" + res;
                    break;
                case 9:
                    res = "9" + res;
                    break;
                case 10:
                    res = "A" + res;
                    break;
                case 11:
                    res = "B"+res;
                    break;
                case 12:
                    res = "C"+res;
                    break;
                case 13:
                    res = "D"+res;
                    break;
                case 14:
                    res = "E"+res;
                    break;
                case 15:
                    res = "F"+res;
                    break;
            }
            i /= 16;
            p++;
        }
        if(p == 1)
            res = "0" + res;
        return res;
    }
    public boolean symbol_check(String a)
    {
        if (a != "")
        {
            for(int i = 0; i < a.length(); i++)
                if(!(a.charAt(i) >= '0' && a.charAt(i) <= '9'))
                    return false;
        }
        else
            return false;
        return true;
    }
}
