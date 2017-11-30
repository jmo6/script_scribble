package script.scribble;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

import script.scribble.util.ImageHandler;
import script.scribble.util.Vector2f;

public class Character {
    public static final int UP    = 0;
    public static final int LEFT  = 1;
    public static final int DOWN  = 2;
    public static final int RIGHT = 3;

    public int width;
    public int height;
    public Vector2f position;
    public Vector2f scale;
    public int direction = LEFT;
    Paint bluePaint = new Paint();

    public Character(int grid_cell_width, int grid_cell_height) {
        width = grid_cell_width;
        height = grid_cell_height;
        position = new Vector2f();
        scale= new Vector2f(0.2f, 0.2f);
        bluePaint.setColor(Color.BLUE);
    }

    public void Draw(Canvas canvas) {
        Bitmap img = ImageHandler.images[ImageHandler.SNEK];
        Matrix matrix = new Matrix();
        matrix.postTranslate(-img.getWidth() / 2, -img.getHeight() / 2);
        matrix.postScale(0.3f, 0.3f);

        if(direction % 4 == RIGHT) {
            matrix.postRotate(180);
            matrix.postTranslate(position.x * width + width / 2,
                    position.y * height + height / 2);
            canvas.drawBitmap(img, matrix, null);
        } else if(direction % 4 == LEFT) {
//            matrix.postRotate(0);
            matrix.postTranslate(position.x * width + width / 2,
                    position.y * height + height / 2);
            canvas.drawBitmap(img, matrix, null);
        } else if(direction % 4 == DOWN) {
            matrix.postRotate(270);
            matrix.postTranslate(position.x * width + width / 2,
                    position.y * height + height / 2);
            canvas.drawBitmap(img, matrix, null);
        } else if(direction % 4 == UP) {
            matrix.postRotate(90);
            matrix.postTranslate(position.x * width + width / 2,
                    position.y * height + height / 2);
            canvas.drawBitmap(img, matrix, null);
        }
    }
}
