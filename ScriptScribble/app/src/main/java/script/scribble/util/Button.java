package script.scribble.util;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Button {
	Rect rect;
	int x, y;
	Paint background, font;
	String text;
	int padding;
	
	Button(int x, int y, Paint background, Paint font, String text, int padding) {
		this.x = x;
		this.y = y;
		this.background = background;
		this.font = font;
		this.text = text;
		this.padding = padding;

		this.rect = new Rect();
		this.font.getTextBounds(text, 0, text.length(), rect);
		rect.offset(x, y);
		
		rect.left -= this.padding;
		rect.top -= this.padding;
		rect.right += this.padding;
		rect.bottom += this.padding;
	}
	
	void Draw(Canvas canvas) {
		canvas.drawRect(rect, background);
		canvas.drawText(text, x, y, font);
	}
}
