package com.bc.decideforme.android;

import java.util.Vector;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class DrawView extends View {

	/** The radius of the circle */
	private int circleRadius;

	/** The circle's X coordinate */
	private int circleX;

	/** The circle's Y coordinate */
	private int circleY;

	/** The X coordinate for 12 O'Clock */
	private int startPointX;

	/** The Y coordinate for 12 O'Clock */
	private int startPointY;

	public Vector<Pin> pins = new Vector<Pin>();

	private int pinID = 0; // variable to know what pin is being dragged
	private int lastPinID = 0; // variable to know what pin is being dragged
	private static int pinCounter;

	public DrawView(Context context) {
		super(context);
		pinCounter = 1;
		pinID = 0;
		lastPinID = 0;
		setFocusable(true); // necessary for getting the touch events
		getScreenSize();
		createNextPin(context);
	}

	private void createNextPin(Context context) {
		Point point = new Point();
		point.x = startPointX;
		point.y = startPointY;
		
		pins.add(new Pin(context, R.drawable.triangle, point, pinCounter));
		lastPinID = pins.elementAt(pins.size() - 1).getID();
		pinCounter++;
	}

	@SuppressLint("NewApi")
	private void getScreenSize() {
		WindowManager wm = (WindowManager) this.getContext().getSystemService(
				Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;

		calcCircle(height, width);

	}

	private void calcCircle(int height, int width) {
		float maxDiameter = (width > height) ? height : width; // Choose the
		// smaller
		float diameter = (float) (maxDiameter * 0.75);
		circleX = width / 2; // Center X for circle
		circleY = height / 2; // Center Y for circle
		circleRadius = (int) (diameter / 2); // Radius of the outer circle

		startPointX = circleX; // 12 O'clock X coordinate
		startPointY = circleY - circleRadius;// 12 O'clock Y coordinate
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	// the method that draws the pins
	@Override
	protected void onDraw(Canvas canvas) {
		// canvas.drawColor(0xFFCCCCCC); //if you want another background color
		Log.d("DecideForMe", "ON DRAW: "+ Integer.toString(pinID));
		
		// draw the pins on the canvas
		int pinsLength = pins.size();
		for (int i = 0; i < pinsLength; i++) {
			canvas.drawBitmap(pins.elementAt(i).getBitmap(), pins.elementAt(i)
					.getX(), pins.elementAt(i).getY(), null);
			// canvas.rotate(i*10);
		}
	}

	// events when touching the screen
	public boolean onTouchEvent(MotionEvent event) {
		int eventaction = event.getAction();
		((Main) this.getContext()).setTitle("eventaction = " + eventaction);
		int touchX = (int) event.getX();
		int touchY = (int) event.getY();

		switch (eventaction) {

		case MotionEvent.ACTION_DOWN: // touch down so check if the finger is on
										// a pin
			pinID = 0;
			for (Pin pin : pins) {
				// check if inside the bounds of the pin (circle)
				// get the center for the pin
				Log.d("DecideForMe", "ACTION DOWN: "+ Integer.toString(pinID));
				int pinX = pin.getX() + 25;
				int pinY = pin.getY() + 25;

				// calculate the radius from the touch to the center of the pin
				double radCircle = Math.sqrt((double) (Math.pow(
						(pinX - touchX), 2) + Math.pow((pinY - touchY), 2)));

				// if the radius is smaller then 23 (radius of a ball is 25),
				// then it must be on the ball
				if (radCircle < 23) {
					pinID = pin.getID();
					break;
				}
			}

			break;

		case MotionEvent.ACTION_MOVE: // touch drag with the ball
			// move the balls the same as the finger
			if (pinID > 0) {
				// pins.elementAt(pinID - 1).setX(touchX-25);
				// pins.elementAt(pinID - 1).setY(touchY-25);
				Log.d("DecideForMe", "ACTION MOVE: "+ Integer.toString(pinID));
				pins.elementAt(pinID - 1).movePin(touchX, touchY, circleX,
						circleY, circleRadius);
			}
			break;

		case MotionEvent.ACTION_UP:
			// draw the next pin if the last created pin was moved
			if (pinID == lastPinID) {
				createNextPin(this.getContext());
			}
			break;
		}
		// redraw the canvas
		invalidate();
		return true;

	}

}
