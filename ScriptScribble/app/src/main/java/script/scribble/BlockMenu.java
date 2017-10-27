package script.scribble;

import java.util.ArrayList;

import script.scribble.blocks.Block;

<<<<<<< HEAD

=======
>>>>>>> b163201367c6c783b68cf2a3cc41ab03e022a8c9
public class BlockMenu {
    // categories
    public static int STATEMENT_BLOCK =  0;
    public static int CONTROL_BLOCK =    1;
    public static int CONDITION_BLOCK=   2;
    public static int RELATION_BLOCK =   3;
    public static int EVENT_BLOCK =      4;

    public ArrayList<Block> blocks;
    public Block draggedBlock = null;
    public int curCategory = STATEMENT_BLOCK;

    void update() {
        // handle switching categories
        // handle moving blocks to coding area (draggedBlock = new Block of whatever type was pressed)
    }

    void draw() {
        // draw categories and blocks within that category
        // draw draggedBlock
    }
}
