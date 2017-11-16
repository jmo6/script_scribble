package script.scribble;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.ArrayList;

import script.scribble.blocks.Block;

public class BlockMenu {
    // categories
    public static final int STATEMENT_BLOCK =  0;
    public static final int CONTROL_BLOCK =    1;
    public static final int CONDITION_BLOCK=   2;
    public static final int RELATION_BLOCK =   3;
    public static final int EVENT_BLOCK =      4;

    public ArrayList<ArrayList<Block>> blocks;
    public Block draggedBlock = null;
    public int curCategory = STATEMENT_BLOCK;

    RectF blockBar = new RectF(
    CustomView.screen_width / 10,
    0,
        CustomView.screen_width / 10 + CustomView.screen_width / 2.75f,
        CustomView.screen_height);
    Paint yellowPaint = new Paint();

    public BlockMenu() {
        yellowPaint.setColor(Color.YELLOW);
    }

    void update() {
        // handle switching categories
        // handle moving blocks to coding area (draggedBlock = new Block of whatever type was pressed)
    }

    void draw(Canvas canvas) {
        // draw categories and blocks within that category
        // draw draggedBlock
        canvas.drawRect(blockBar, yellowPaint);
    }
}
