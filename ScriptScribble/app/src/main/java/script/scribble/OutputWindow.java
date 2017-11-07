package script.scribble;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.ArrayList;

public class OutputWindow {
    Character character;
    ArrayList<Obstacle> obstacles;

    RectF outputWindow = new RectF(
            0,
            0,
            CustomView.screen_width,
            CustomView.screen_height / 2);
    Paint greenPaint = new Paint();

    public OutputWindow() {
        obstacles = new ArrayList<Obstacle>();
        character = new Character();
        greenPaint.setColor(Color.GREEN);
    }

    public void draw(Canvas canavs) {
        canavs.drawRect(outputWindow, greenPaint);
        for(int i = 0; i < obstacles.size(); i++) {
            obstacles.get(i).draw();
        }
        character.draw();
    }
}
