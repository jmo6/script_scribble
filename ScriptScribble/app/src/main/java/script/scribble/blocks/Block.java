package script.scribble.blocks;

import android.graphics.Canvas;

import script.scribble.BlockMenu;
import script.scribble.CodingArea;
import script.scribble.util.Vector2f;

public abstract class Block {
    // execute states
    public static int ERROR = 0;
    public static int TRUE =  1;
    public static int FALSE = 2;

    // block ids
    public static int MOVE_BLOCK =             0;
    public static int ROTATE_BLOCK =           1;

    public static int IF_BLOCK =               2;
    public static int ELSE_BLOCK =             3;
    public static int WHILE_BLOCK =            4;

    public static int IS_RIGHT_SPACE_OPEN_BLOCK = 5;
    public static int IS_LEFT_SPACE_OPEN_BLOCK =  6;
    public static int IS_UP_SPACE_OPEN_BLOCK = 7;
    public static int IS_DOWN_SPACE_OPEN_BLOCK = 8;

    public static int AND_BLOCK =              9;
    public static int OR_BLOCK =               10;
    public static int NOT_BLOCK =              11;

    public static int RUN_PRESSED_BLOCK =      12;
    public static int BUTTON_PRESSED_BLOCK =   13;

    public static int NUM_BLOCKS =             14;

    // this is the index of where this block is in the CodingArea.blocks array
    public int index;
    public int id;
    public int category;
    public int imageIndex;
    public Vector2f position;
    // drawingState is 0 when in BlockMenu, 1 when dragged, 2 when in CodingArea
    public int drawingState;

    public Block(){
        position = new Vector2f();
    }

    // maybe animations here (block specific)
    public abstract void update();

    // for the most part, this will just be canvas.drawBitmap(imageIndex, x, y);
    // this will change based on drawingState
    public abstract void draw(Canvas canvas);

    // block specific
    public abstract int execute(CodingArea codingArea);

    // helper functions go below here
    public int executeNextBlock(CodingArea codingArea) {
        codingArea.currentExecutingBlockIndex++;
        if(codingArea.currentExecutingBlockIndex >= codingArea.blocks.size()) {
            // TODO: set a global error string that we can display to the user
            System.err.println("Expected another block at the end");
            return ERROR;
        }
        return codingArea.blocks.get(codingArea.currentExecutingBlockIndex).execute(codingArea);
    }
}
