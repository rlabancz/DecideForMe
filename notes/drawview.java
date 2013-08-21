package eas.org;

import java.util.Vector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

public class DrawView extends View {
	
	public Vector<Pin> pins = new Vector<Pin>();

	private int pinID = 0; // variable to know what ball is being dragged
	private int lastPinID = 0; // variable to know what ball is being dragged

	public DrawView(Context context) {
		super(context);
		setFocusable(true); // necessary for getting the touch events

		createNextPin(context);
	}

	private void createNextPin(Context context) {
		Point point = new Point();
		point.x = 50;
		point.y = 20;

		pins.add( new Pin( context, R.drawable.bol_blauw, point ) );
		lastPinID = pins.elementAt(pins.size()-1).getID();
	}

	// the method that draws the balls
	@Override
	protected void onDraw(Canvas canvas) {
		// canvas.drawColor(0xFFCCCCCC); //if you want another background color

		// draw the balls on the canvas
		int pinsLength = pins.size();
		for (int i=0 ; i < pinsLength; i++)
		{
			canvas.drawBitmap(pins.elementAt(i).getBitmap(), pins.elementAt(i).getX(), pins.elementAt(i).getY(), null);
		}
	}

	// events when touching the screen
	public boolean onTouchEvent(MotionEvent event) {
		int eventaction = event.getAction();
		((Main) this.getContext()).setTitle("eventaction = " + eventaction);
		int X = (int) event.getX();
		int Y = (int) event.getY();

		switch (eventaction) {

		case MotionEvent.ACTION_DOWN: // touch down so check if the finger is on
										// a ball
			pinID = 0;
			for (Pin pin : pins) {
				// check if inside the bounds of the ball (circle)
				// get the center for the ball
				int centerX = pin.getX() + 25;
				int centerY = pin.getY() + 25;

				// calculate the radius from the touch to the center of the ball
				double radCircle = Math
						.sqrt((double) (((centerX - X) * (centerX - X)) + (centerY - Y)
								* (centerY - Y)));

				// if the radius is smaller then 23 (radius of a ball is 22),
				// then it must be on the ball
				if (radCircle < 23) {
					pinID = pin.getID();
					break;
				}

				// check all the bounds of the ball (square)
				// if (X > ball.getX() && X < ball.getX()+50 && Y > ball.getY()
				// && Y < ball.getY()+50){
				// balID = ball.getID();
				// break;
				// }
			}

			break;

		case MotionEvent.ACTION_MOVE: // touch drag with the ball
			// move the balls the same as the finger
			if (pinID > 0) {
				pins.elementAt(pinID - 1).setX(X - 25);
				pins.elementAt(pinID - 1).setY(Y - 25);
			}
			
			

			break;

		case MotionEvent.ACTION_UP:
			// draw the next pin if the last created pin was moved
			if(pinID == lastPinID)
			{
				createNextPin(this.getContext());
			}
			break;
		}
		// redraw the canvas
		invalidate();
		return true;

	}
}
