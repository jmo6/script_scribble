package script.scribble;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.ArrayList;

import script.scribble.util.ImageHandler;
import script.scribble.util.Vector2f;

public class OutputWindow {
    public static final int EMPTY    = 0;
    public static final int OBSTACLE = 1;
    public static final int GOAL     = 2;

    public static ArrayList<ArrayList<Integer>> grid;
    public static Character character;
    public static int num_cells = 11;
    int grid_cell_width = CustomView.screen_width / num_cells;
    int grid_cell_height = (CustomView.screen_height / 2) / num_cells;
    public Vector2f scale = new Vector2f(0.15f, 0.15f);

    RectF outputWindow = new RectF(
            0,
            0,
            CustomView.screen_width,
            CustomView.screen_height / 2);
    Paint greenPaint = new Paint();
    Paint blackPaint = new Paint();
    public static CodingArea codingArea;

    public OutputWindow(CodingArea codingArea) {
        this.codingArea = codingArea;
        grid = new ArrayList<>();
        for(int i = 0; i < num_cells; i++) {
            grid.add(new ArrayList<Integer>());
            for(int j = 0; j < num_cells; j++) {
                grid.get(i).add(EMPTY);
            }
        }
        character = new Character(grid_cell_width, grid_cell_height);
        Reset();
        greenPaint.setColor(Color.GREEN);
        blackPaint.setColor(Color.BLACK);
    }

    public void Draw(Canvas canvas) {
        canvas.drawRect(outputWindow, greenPaint);

        Bitmap img = ImageHandler.images[ImageHandler.ROCK];
        Rect src = new Rect(0, 0, img.getWidth(), img.getHeight());
        for(int i = 0; i < num_cells; i++) {
            for(int j = 0; j < grid.get(i).size(); j++) {
                if(grid.get(i).get(j) == OBSTACLE) {
                    Rect dest = new Rect(
                            (int) (i * grid_cell_width),
                            (int) (j * grid_cell_height),
                            (int) (i * grid_cell_width + img.getWidth() * scale.x),
                            (int) (j * grid_cell_height + img.getHeight() * scale.y));
                    canvas.drawBitmap(img, src, dest, null);
                } else if(grid.get(i).get(j) == GOAL) {
                    Bitmap flagImg = ImageHandler.images[ImageHandler.FLAG];
                    Rect flagSrc = new Rect(0, 0, flagImg.getWidth(), flagImg.getHeight());
                    Rect dest = new Rect(
                            (int) (i * grid_cell_width),
                            (int) (j * grid_cell_height),
                            (int) (i * grid_cell_width + img.getWidth() * scale.x),
                            (int) (j * grid_cell_height + img.getHeight() * scale.y));
                    canvas.drawBitmap(flagImg, flagSrc, dest, null);
                }
            }
        }

        character.Draw(canvas);
    }

    //

    public static void Reset() {
        codingArea.currentExecutingBlockIndex = 0;
        codingArea.executing = false;

        for(int i = 0; i < num_cells; i++) {
            for(int j = 0; j < num_cells; j++) {
                grid.get(i).set(j, EMPTY);
            }
        }

        character.position.x = num_cells / 2;
        character.position.y = num_cells / 2;
        character.direction = Character.LEFT;

        double goalX = Math.random() * num_cells / 2.0;
        double goalY = Math.random() * num_cells / 2.0;

        if(goalX >= num_cells / 4.0) {
            goalX += num_cells / 2.0;
        }
        if(goalY >= num_cells / 4.0) {
            goalY += num_cells / 2.0;
        }

        grid.get((int) goalX).set((int) goalY, GOAL);

        for(int i = 0; i < grid.size(); i++) {
            if(i == num_cells / 2) continue;; // no obstacles on player x
            for(int j = 0; j < grid.get(i).size(); j++) {
                if(j == (int) goalY) continue; // no obstacles on goal y
                if(Math.random() > 0.5) {
                    grid.get(i).set(j, OBSTACLE);
                }
            }
        }
    }
}
