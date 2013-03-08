package com.bc.decideforme.android;

import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener
{

  private TextView output;
  private Button btnNumberGenerator;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    output = (TextView) this.findViewById(R.id.output);
    btnNumberGenerator = (Button) findViewById(R.id.numberGenerator);
    btnNumberGenerator.setOnClickListener(this);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  private int getRandomDegree()
  {
    // Returns an integer between 0 and 360
    Random randomNumber = new Random();
    int randomDegree = randomNumber.nextInt(360);
    output.setText(Integer.toString(randomDegree));
    return randomDegree;
  }

  @Override
  public void onClick(View view)
  {
    // TODO Auto-generated method stub
    switch (view.getId())
    {
      case R.id.numberGenerator:
        getRandomDegree();
        break;
    }
  }

}
