package script.scribble;

import android.graphics.Canvas;

import java.util.ArrayList;

import script.scribble.blocks.Block;
import script.scribble.blocks.IfBlock;

public class CodingArea {
    public ArrayList<Block> blocks;
    public int draggedBlockIndex;
    public int currentExecutingBlockIndex;
    public int lastIfStatus;

    public CodingArea() {
        blocks = new ArrayList<Block>();
        // debug code
        blocks.add(new IfBlock());
    }

    // handle moving blocks to block menu
    // call execute when play button is pressed
    void update() {
    }

    // draw blocks in coding area
    // draw draggedBlock
    void draw(Canvas canvas) {
        for(int i = 0; i < blocks.size(); i++) {
            blocks.get(i).draw(canvas);
        }
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
