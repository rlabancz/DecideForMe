package com.bc.decideforme.android;

import android.app.Activity;
import android.os.Bundle;

public class Main extends Activity
{
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    // draw the view
    setContentView(new DrawView(this));

  }

}