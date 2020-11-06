package e.roman.multiplytable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChoiceActivity extends AppCompatActivity implements View.OnClickListener {

    ConstraintLayout back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        Button button2 = (Button)findViewById(R.id.button2);
        Button button3 = (Button)findViewById(R.id.button3);
        Button button4 = (Button)findViewById(R.id.button4);
        Button button5 = (Button)findViewById(R.id.button5);
        Button button6 = (Button)findViewById(R.id.button6);
        Button button7 = (Button)findViewById(R.id.button7);
        Button button8 = (Button)findViewById(R.id.button8);
        Button button9 = (Button)findViewById(R.id.button9);
        Button buttonAll = (Button)findViewById(R.id.buttonAll);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        buttonAll.setOnClickListener(this);
        back = (ConstraintLayout)findViewById(R.id.back);
        back.setOnTouchListener(new OnSwipeTouchListener(ChoiceActivity.this) {
            public void onSwipeRight(){
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(ChoiceActivity.this, Check2.class);;
        switch (view.getId())
        {
            case R.id.button2:
                i.putExtra("Num", 2);
                break;
            case R.id.button3:
                i.putExtra("Num", 3);
                break;
            case R.id.button4:
                i.putExtra("Num", 4);
                break;
            case R.id.button5:
                i.putExtra("Num", 5);
                break;
            case R.id.button6:
                i.putExtra("Num", 6);
                break;
            case R.id.button7:
                i.putExtra("Num", 7);
                break;
            case R.id.button8:
                i.putExtra("Num", 8);
                break;
            case R.id.button9:
                i.putExtra("Num", 9);
                break;
            case R.id.buttonAll:
                i = new Intent(ChoiceActivity.this, CheckAll.class);
                break;
        }
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }
}
