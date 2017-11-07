package script.scribble;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

import script.scribble.blocks.AndBlock;
import script.scribble.blocks.Block;
import script.scribble.blocks.IfBlock;
import script.scribble.blocks.IsLeftSpaceOpenBlock;
import script.scribble.blocks.IsRightSpaceOpenBlock;
import script.scribble.blocks.MoveBlock;
import script.scribble.util.Input;

public class CodingArea {
    public ArrayList<Block> blocks;
    public int draggedBlockIndex;
    public int currentExecutingBlockIndex;
    public int lastIfStatus;
    Paint redPaint = new Paint();
    private final String RUN_TAG = "RUN EXECUTED";
    private final int RECT_X = 30;
    private final int RECT_Y = 30;
    private final int RECT_WIDTH = 80;
    private final int RECT_HEIGHT = 80;

    RectF codingArea = new RectF(
        0,
        CustomView.screen_height / 2,
        CustomView.screen_width,
        CustomView. screen_height);
    Paint bluePaint = new Paint();

    public CodingArea() {
        blocks = new ArrayList<Block>();
        // debug code
        blocks.add(new IfBlock());
        ((IfBlock)blocks.get(0)).firstBlockInThenIndex = 4;
        ((IfBlock)blocks.get(0)).lastBlockInThenIndex = 4;
        blocks.add(new AndBlock());
        blocks.add(new IsRightSpaceOpenBlock());
        blocks.add(new IsLeftSpaceOpenBlock());
        blocks.add(new MoveBlock());
//        execute();

        bluePaint.setARGB(255, 0, 0, 255);
    }

    // handle moving blocks to block menu
    // call execute when play button is pressed
    void update(Input input) {
        // Look for the RUN button
        // If run button touched then run
        if (input.isRectPressed(RECT_X,RECT_Y,RECT_WIDTH-RECT_X,RECT_HEIGHT-RECT_Y)){
            execute();
        }
    }

    // draw blocks in coding area
    // draw draggedBlock
    void draw(Canvas canvas) {
        canvas.drawRect(codingArea, bluePaint);

        for(int i = 0; i < blocks.size(); i++) {
            blocks.get(i).draw(canvas);
        }

        //Temporary run button for Coding Area
        redPaint.setColor(Color.RED);
        canvas.drawRect(RECT_X,RECT_Y,RECT_WIDTH,RECT_HEIGHT, redPaint);
    }


    // loops through blocks array and calls their .execute function
    // handle if a block's execute function returns ERROR
    int execute() {
        // TODO: disable moving of blocks while executing
        for(currentExecutingBlockIndex = 0; currentExecutingBlockIndex < blocks.size(); currentExecutingBlockIndex++) {
            if(blocks.get(currentExecutingBlockIndex).execute(this) == Block.ERROR)  {
                return Block.ERROR;
            }
        }
        return Block.TRUE;
    }
}
