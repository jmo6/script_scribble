package script.scribble;

import java.util.ArrayList;

import script.scribble.blocks.Block;

<<<<<<< HEAD

=======
>>>>>>> fbe4c2d1d51009e320688cd8ad7db87bbeeec476
public class CodingArea {
    ArrayList<Block> blocks;
    int draggedBlockIndex;

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
