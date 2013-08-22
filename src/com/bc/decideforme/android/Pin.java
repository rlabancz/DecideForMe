package com.bc.decideforme.android;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

public class Pin {
	private Bitmap img; // the image of the ball
	private int coordX = 0; // the x coordinate at the canvas
	private int coordY = 0; // the y coordinate at the canvas
	private double angle = 0; // the angle of the pin
	private int id; // gives every pin his own id, for now not necessary

	public Pin(Context context, int drawable, int id) {

		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		img = BitmapFactory.decodeResource(context.getResources(), drawable);
		this.id = id;
	}

	public Pin(Context context, Bitmap img, Point point, int id) {

		/*BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		img = BitmapFactory.decodeResource(context.getResources(), drawable);
		*/
		this.img =img;
		coordX = point.x;
		coordY = point.y;
		angle = 0;
		this.id = id;
	}

	void setX(int newValue) {
		coordX = newValue;
	}

	public int getX() {
		return coordX;
	}

	void setY(int newValue) {
		coordY = newValue;
	}

	public int getY() {
		return coordY;
	}

	void setAngle(int newValue) {
		angle = newValue;
	}

	public double getAngle() {
		return angle;
	}

	public int getID() {
		return id;
	}

	public Bitmap getBitmap() {
		return img;
	}

	public void movePin(int goX, int goY, int centerX, int centerY, int radius) {

		float degrees = (float) ((float) ((Math.toDegrees(Math.atan2(goX
				- centerX, centerY - goY)) + 360.0)) % 360.0);
		// and to make it count 0-360
		if (degrees < 0) {
			degrees += 2 * Math.PI;
		}

		double radians = (Math.atan2(goX - centerX, centerY - goY));
		// get X coordinate from angle
		double deltaX = radius * Math.sin(radians);
		double deltaY = radius * Math.cos(radians);
		coordX = (int) (deltaX + centerX);
		coordY = (int) (centerY - deltaY);
		angle = radians;

		// check the borders, and set the direction if a border has reached
		/*
		 * if (coordX > 270) { goRight = false; } if (coordX < 0) { goRight =
		 * true; } if (coordY > 400) { goDown = false; } if (coordY < 0) {
		 * goDown = true; }
		 */
		// move the x and y
		// if (goRight) {
		// coordX += goX;
		// } else {
		// coordX -= goX;
		// }
		// if (goDown) {
		// coordY += goY;
		// } else {
		// coordY -= goY;
		// }

	}

}
