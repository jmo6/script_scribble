package script.scribble;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.ArrayList;

public class OutputWindow {
    public static final int EMPTY    = 0;
    public static final int OBSTACLE = 1;

    public static ArrayList<ArrayList<Integer>> grid;
    public static Character character;
    int num_cells = 10;
    int grid_cell_width = CustomView.screen_width / num_cells;
    int grid_cell_height = (CustomView.screen_height / 2) / num_cells;

    RectF outputWindow = new RectF(
            0,
            0,
            CustomView.screen_width,
            CustomView.screen_height / 2);
    Paint greenPaint = new Paint();
    Paint blackPaint = new Paint();

    public OutputWindow() {
        grid = new ArrayList<>();
        for(int i = 0; i < num_cells; i++) {
            grid.add(new ArrayList<Integer>());
            for(int j = 0; j < num_cells; j++) {
                grid.get(i).add(EMPTY);
            }
        }

        character = new Character(grid_cell_width, grid_cell_height);
        greenPaint.setColor(Color.GREEN);
        blackPaint.setColor(Color.BLACK);

        // debug
        grid.get(1).set(5, OBSTACLE);
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(outputWindow, greenPaint);

        for(int i = 0; i < num_cells; i++) {
            for(int j = 0; j < grid.get(i).size(); j++) {
                if(grid.get(i).get(j) == OBSTACLE) {
                    canvas.drawRect(j * grid_cell_width, i * grid_cell_height,
                            j * grid_cell_width + grid_cell_width, i * grid_cell_height + grid_cell_height, blackPaint);
                }
            }
        }

        character.draw(canvas);
    }
}
