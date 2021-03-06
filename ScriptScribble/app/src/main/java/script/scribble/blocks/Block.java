package script.scribble.blocks;

import android.graphics.Canvas;
import android.graphics.RectF;

import script.scribble.CodingArea;
import script.scribble.util.ImageHandler;
import script.scribble.util.Vector2f;

public abstract class Block {
    // execute states
    public static final int ERROR = 0;
    public static final int TRUE =  1;
    public static final int FALSE = 2;

    // block ids
    // STATEMENT
    public static final int MOVE_BLOCK =                0;
    public static final int ROTATE_BLOCK =              1;

    // CONTROL
    public static final int IF_BLOCK =                  2;
    public static final int ELSE_BLOCK =                3;
    public static final int WHILE_BLOCK =               4;

    // CONDITION
    public static final int IS_RIGHT_SPACE_OPEN_BLOCK = 5;
    public static final int IS_LEFT_SPACE_OPEN_BLOCK =  6;
    public static final int IS_UP_SPACE_OPEN_BLOCK =    7;
    public static final int IS_DOWN_SPACE_OPEN_BLOCK =  8;

    // RELATION
    public static final int AND_BLOCK =                 9;
    public static final int OR_BLOCK =                  10;
    public static final int NOT_BLOCK =                 11;

    // EVENT
    public static final int RUN_PRESSED_BLOCK =         12;
    public static final int BUTTON_PRESSED_BLOCK =      13;

    public static final int NUM_BLOCKS =                14;

    // SNAPPEE1 is the coordinate of the top left of the first slot where a block would snap into another block
    public static final int SNAPPEE1_X = 63;
    public static final int SNAPPEE1_Y = 40;
    // SNAPPEE1_TOP is the coordinate of the top left of a block that can have another block snap into it
    public static final int SNAPPEE1_TOP_X = 20;
    public static final int SNAPPEE1_TOP_Y = -15;
    // SNAPPEE1_BOTTOM is the coordinate of the bottom left of a block that can have only 1 block snap into it
    public static final int SNAPPEE1_BOTTOM_X = 20;
    public static final int SNAPPEE1_BOTTOM_Y = 140;

    // SNAPPEE2 is the coordinate of the top left of the second slot where a block would snap into another block
    public static final int SNAPPEE2_X = 63;
    public static final int SNAPPEE2_Y = 151;
    // SNAPPEE2_BOTTOM is the coordinate of the bottom left of a block that can have 2 blocks snap into it
    public static final int SNAPPEE2_BOTTOM_X = 20;
    public static final int SNAPPEE2_BOTTOM_Y = 258;

    // SNAPPER is the coordinate of the top left of a block that cannot have other blocks snap into it
    public static final int SNAPPER_X = 63;
    public static final int SNAPPER_Y = 40;
    // SNAPPER is the coordinate of the bottom left of a block that cannot have other blocks snap into it
    public static final int SNAPPER_BOTTOM_X = 63;
    public static final int SNAPPER_BOTTOM_Y = 90;

    // this is the index of where this block is in the CodingArea.blocks array
    public int index;
    public int id;
    public int category;
    public Vector2f position;
    public Vector2f scale;
    public int firstBlockInThenIndex;
    public int lastBlockInThenIndex;

    public Block(){
        position = new Vector2f();
        scale = new Vector2f(0.5f, 0.5f);
    }

    public RectF getImageRect() {
        return new RectF(position.x, position.y, position.x + ImageHandler.images[id].getWidth() * scale.x,
                position.y + ImageHandler.images[id].getHeight() * scale.y);
    }

    // maybe animations here (block specific)
    public abstract void Update();

    // for the most part, this will just be canvas.drawBitmap(imageIndex, x, y);
    // this will change based on drawingState
    public abstract void Draw(Canvas canvas);

    // block specific
    public abstract int Execute(CodingArea codingArea);

    public abstract Block Clone();

    // helper functions go below here
    public int ExecuteNextBlock(CodingArea codingArea) {
        codingArea.currentExecutingBlockIndex++;
        if(codingArea.currentExecutingBlockIndex >= codingArea.blocks.size()) {
            // TODO: set a global error string that we can display to the user
            System.err.println("Expected another block at the end");
            return ERROR;
        }
        return codingArea.blocks.get(codingArea.currentExecutingBlockIndex).Execute(codingArea);
    }
}
