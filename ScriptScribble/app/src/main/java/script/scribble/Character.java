package script.scribble;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import script.scribble.util.Vector2f;

public class Character {
    public static final int UP    = 0;
    public static final int LEFT  = 1;
    public static final int DOWN  = 2;
    public static final int RIGHT = 3;

    public int width;
    public int height;
    public Vector2f position;
    public int direction = RIGHT;
    Paint bluePaint = new Paint();

    public Character(int grid_cell_width, int grid_cell_height) {
        width = grid_cell_width;
        height = grid_cell_height;
        position = new Vector2f();
        bluePaint.setColor(Color.BLUE);
    }

    public void Draw(Canvas canvas) {
        canvas.drawRect(position.x * width, position.y * height,
                position.x * width + width, position.y * height + height, bluePaint);
    }
}
