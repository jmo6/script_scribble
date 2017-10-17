package script.scribble;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class BitmapAnimation {
	int nBmps;
	Bitmap[] bmps;
	int[] startOrder, loopOrder, endOrder;
	int state;
	long startTime, lastTime;
	boolean ended;
	
	BitmapAnimation(Bitmap strip, int widthPerBitmap, int[] startOrder, int[] loopOrder, int[] endOrder) {
		this.nBmps = strip.getWidth() / widthPerBitmap;
		bmps = new Bitmap[nBmps];
		for(int i = 0; i < nBmps; i++) {
			bmps[i] = Bitmap.createBitmap(strip, i * widthPerBitmap, 0, widthPerBitmap, strip.getHeight());
		}
		this.startOrder = startOrder;
		this.loopOrder = loopOrder;
		this.endOrder = endOrder;
		state = 2;
		startTime = 0;
		lastTime = 0;
		ended = true;
	}
	
	void draw(Canvas canvas, float x, float y, int millisPerBitmap) {
		if(startTime == 0) {
			startTime = System.currentTimeMillis();
			lastTime = System.currentTimeMillis();
		}
		int elapsedTime = (int) (lastTime - startTime);
		int index = elapsedTime / millisPerBitmap;
		
		switch(state) {
		case 0:
			if(index >= startOrder.length) {
				canvas.drawBitmap(bmps[startOrder[startOrder.length - 1]], x, y, null);
				startTime = 0;
				index = 0;
				state = 1;
			}
			else
				canvas.drawBitmap(bmps[startOrder[index]], x, y, null);
			break;
		case 1:
			if(index >= loopOrder.length) {
				canvas.drawBitmap(bmps[loopOrder[loopOrder.length - 1]], x, y, null);
				startTime = 0;
				index = 0;
			}
			else
				canvas.drawBitmap(bmps[loopOrder[index]], x, y, null);
			break;
		case 2:
			if(index >= endOrder.length) {
				canvas.drawBitmap(bmps[endOrder[endOrder.length - 1]], x, y, null);
				startTime = 0;
				index = 0;
			}
			else
				canvas.drawBitmap(bmps[endOrder[index]], x, y, null);
			break;
		case 3:
			canvas.drawBitmap(bmps[startOrder[0]], x, y, null);
		}
		
		lastTime = System.currentTimeMillis();
	}
	
	void end() {
		ended = true;
		state = 2;
		startTime = 0;
	}
	
	boolean hasEnded() {
		return ended;
	}
	
	void reset() {
		startTime = 0;
		state = 1;
		ended = false;
	}
	
	void restart() {
		startTime = 0;
		state = 1;
		ended = false;
	}
}
