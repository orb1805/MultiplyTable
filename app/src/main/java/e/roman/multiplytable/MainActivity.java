package e.roman.multiplytable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button button;
    ConstraintLayout back;
    Intent i;
   // LinearLayout  linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        button.setBackgroundResource(R.drawable.roundedbutton_main);
        button.setTextColor(Color.parseColor("#D7CCC8"));
        button.setOnClickListener(this);
        back = (ConstraintLayout) findViewById(R.id.back);
        //i = new Intent(this, ChoiceActivity.class);
        File statistic = new File(getFilesDir(), "statistic");
        //back.setBackgroundResource(R.drawable.back_still1);
       // linearLayout = (LinearLayout) findViewById(R.id.linearLayoutid);
    }

    @Override
    public void onClick(View view) {
        i = new Intent(this, ChoiceActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }
}
