package e.roman.multiplytable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.FileObserver;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
//import java.util.concurrent.TimeUnit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Check2 extends AppCompatActivity implements View.OnClickListener{
    TextView textRight, textLeft;
    EditText ans;
    TextView textRW;
    ConstraintLayout back;
    int[] numbers;
    int j;
    int exs;
    int num;
    Handler handler;
    Button buttonAnswer;
    boolean play;
    Random randInt = new Random();
    String statistic;
    //FileOutputStream stat_file;
    //FileOutputStream fos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check2);
        handler = new Handler();
        num = getIntent().getIntExtra("Num", 8);
        ans = (EditText)findViewById(R.id.answer1);
        textRW = (TextView) findViewById(R.id.textRW);
        textLeft = (TextView)findViewById(R.id.textLeft);
        back = (ConstraintLayout)findViewById(R.id.back);
        textLeft.setText(""+num);
        textRW.setText("");
        textRight = (TextView)findViewById(R.id.textRight);
        buttonAnswer = (Button)findViewById(R.id.buttonAnswer);
        buttonAnswer.setOnClickListener(this);
        numbers = new int[8];
        /*try
        {
            stat_file = openFileOutput("statistic", Context.MODE_PRIVATE);
        }
        catch (FileNotFoundException e) {
            File file = new File(getFilesDir(), "statistic");
        }*/
        randomize();
        textRight.setText(""+numbers[j]);
        back.setOnTouchListener(new OnSwipeTouchListener(Check2.this) {
            public void onSwipeRight(){
                finish();
            }
        });
        exs = 1;
        play = true;
        /*Typeface typ = Typeface.createFromAsset(getAssets(), "font/poiretone.ttf");
        buttonAnswer.setTypeface(typ);*/
        //fos = getApplicationContext().openFileOutput("statistic", Context.MODE_PRIVATE);
        statistic = f_read();
    }
    @Override
    public void onClick(View view)
    {
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
        //Random randInt = new Random();
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
    }
    public void check()
    {
        int answer;
        String answ;
        //if(!ans.getText().toString().equals("")) {
            answ = (""+ans.getText());
            if(symbol_check(answ)) {
                answer = Integer.parseInt("" + ans.getText());
                if (answer == num * numbers[j]) {
                    rightFirst();
                    buttonAnswer.setTextSize(12);
                    buttonAnswer.setText("Следующий");
                    buttonAnswer.setTextColor(Color.parseColor("#222222"));
                    buttonAnswer.setBackgroundResource(R.drawable.roundedbutton2);
                    exs = 0;
                    if (j == 7) {
                        //textRW.setText("Молодец");
                        randomize();
                    } else
                        j++;
                    //byte a = 5;
                    //stat_file.write("ab".getBytes());
                } else {
                    wrong();
                    ans.setText("");
                }
            }
        //}
        //String fileContents = "20";
        /*try(FileOutputStream fos = getApplicationContext().openFileOutput("statistic", Context.MODE_PRIVATE))
        {
            fos.write(fileContents.getBytes());
        }
        catch (Exception e) { }*/
        statistic += "20 ";
    }
    public void show()
    {
        textRight.setText(""+numbers[j]);
    }
    public void wrong()
    {
        if(play) {
            play = false;
            textRW.setTextSize(42);
            textRW.setTextColor(Color.parseColor("#D7CCC8"));
            textRW.setText("НЕПРАВИЛЬНО");
            int r, g, b;
            r = 189;
            g = 189;
            b = 189;
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
    public void rightSecond()
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
    public String f_read()
    {
        String contents = "";
        try (FileInputStream fis = openFileInput("statistic");) {
            InputStreamReader inputStreamReader =
                    new InputStreamReader(fis, StandardCharsets.UTF_8);
            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                String line = reader.readLine();
                while (line != null) {
                    stringBuilder.append(line).append('\n');
                    line = reader.readLine();
                }
            } catch (IOException e) {
                // Error occurred when opening raw file for reading.
            } finally {
                contents = stringBuilder.toString();
            }
        }
        catch(Exception e) { }
        return  contents;
    }
    public void f_write(String str)
    {
        try(FileOutputStream fos = getApplicationContext().openFileOutput("statistic", Context.MODE_PRIVATE))
        {
            fos.write(str.getBytes());
        }
        catch (Exception e) { }
    }

    @Override
    public void finish() {
        f_write(statistic);
        super.finish();
    }
}






 /*public void rightSecond1()
    {
        if(play) {
            play = false;
            ans.setText("");
            textRW.setText("ПРАВИЛЬНО");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#348037"));
                }
            }, 62);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#3F8243"));
                }
            }, 125);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#458748"));
                }
            }, 187);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#518D54"));
                }
            }, 250);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#579259"));
                }
            }, 312);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#439265"));
                }
            }, 375);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#49976A"));
                }
            }, 437);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#759D77"));
                }
            }, 500);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#7BA27C"));
                }
            }, 562);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#87A288"));
                }
            }, 625);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#8DA78D"));
                }
            }, 687);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#99AD9A"));
                }
            }, 750);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#A0B39F"));
                }
            }, 812);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#ABBDAB"));
                }
            }, 875);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#B1BDB1"));
                }
            }, 937);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setText("");
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
    }*/
/*public void rightFirst1()
    {
        if(play) {
            play = false;
            textRW.setTextSize(60);
            textRW.setTextColor(Color.parseColor("#B7BDB7"));
            textRW.setText("ПРАВИЛЬНО");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#B1BDB1"));
                }
            }, 62);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#ABBDAB"));
                    //textRW.setText("#AB");
                }
            }, 125);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#A0B39F"));
                    //textRW.setText("#3F");
                }
            }, 187);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#99AD9A"));
                    //textRW.setText("#99");
                }
            }, 250);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#8DA78D"));
                    //textRW.setText("#3F");
                }
            }, 312);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#87A288"));
                    //textRW.setText("#87");
                }
            }, 375);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#7BA27C"));
                    //textRW.setText("#3F");
                }
            }, 437);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#759D77"));
                    //textRW.setText("#75");
                }
            }, 500);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#49976A"));
                    //textRW.setText("#3F");
                }
            }, 562);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#439265"));
                    //textRW.setText("#43");
                }
            }, 625);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#579259"));
                    //textRW.setText("#3F");
                }
            }, 687);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#518D54"));
                    //textRW.setText("#51");
                }
            }, 750);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#458748"));
                    //textRW.setText("#3F");
                }
            }, 812);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#3F8243"));
                    //textRW.setText("#3F");
                }
            }, 875);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#348037"));
                    //textRW.setText("#3F");
                }
            }, 937);
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
    }*/
/*public void wrong()
    {
        if(play) {
            play = false;
            textRW.setTextSize(50);
            textRW.setTextColor(Color.parseColor("#BDBDBD"));
            textRW.setText("НЕПРАВИЛЬНО");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#BDA3A3"));
                }
            }, 62);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#BD9A9A"));
                }
            }, 125);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#BD9191"));
                }
            }, 187);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#BC8888"));
                }
            }, 250);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#BC7F7F"));
                }
            }, 312);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#BC7676"));
                }
            }, 375);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#BB6D6D"));
                }
            }, 437);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#BB6464"));
                }
            }, 500);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#BB5B5B"));
                }
            }, 562);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#BA5252"));
                }
            }, 625);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#BA4949"));
                }
            }, 687);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#B94040"));
                }
            }, 750);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#B93737"));
                }
            }, 812);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#B82E2E"));
                }
            }, 875);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#B82525"));
                }
            }, 937);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#B71C1C"));
                }
            }, 1000);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#B82525"));
                }
            }, 1062);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#B82E2E"));
                }
            }, 1125);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#B93737"));
                }
            }, 1187);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#B94040"));
                }
            }, 1250);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#BA4949"));
                }
            }, 1312);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#BA5252"));
                }
            }, 1375);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#BB5B5B"));
                }
            }, 1437);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#BB6464"));
                }
            }, 1500);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#BB6D6D"));
                }
            }, 1562);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#BC7676"));
                }
            }, 1625);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#BC7F7F"));
                }
            }, 1687);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#BC8888"));
                }
            }, 1750);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#BD9191"));
                }
            }, 1812);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#BD9A9A"));
                }
            }, 1875);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textRW.setTextColor(Color.parseColor("#BDA3A3"));
                }
            }, 1937);
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
    }*/