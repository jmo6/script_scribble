package script.scribble;

import java.util.ArrayList;

import script.scribble.blocks.Block;

public class CodingArea {
    public ArrayList<Block> blocks;
    public int draggedBlockIndex;
    public int currentExecutingBlockIndex;

    void update() {
        // handle moving blocks to block menu
        // call execute when play button is pressed
    }

    void draw() {
        // draw blocks in coding area
        // draw draggedBlock
    }

    void exeute() {
        // loops through blocks array and calls their .execute function
        // handle if a block's execute function returns ERROR
    }
}
