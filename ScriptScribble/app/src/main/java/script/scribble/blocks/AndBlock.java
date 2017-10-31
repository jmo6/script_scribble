package script.scribble.blocks;

import android.graphics.Canvas;

import script.scribble.BlockMenu;
import script.scribble.CodingArea;

public class AndBlock extends Block {
    public int lastBlockAndIndex;

    public AndBlock() {
        id = Block.AND_BLOCK;
        category = BlockMenu.RELATION_BLOCK;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public int execute(CodingArea codingArea) {
        int status = executeNextBlock(codingArea);
        if (status == ERROR){
            return Block.ERROR;
        }
        if (status == FALSE){
            return FALSE;
        }
        status = executeNextBlock(codingArea);
        if (status == ERROR){
            return Block.ERROR;
        }
        if (status == FALSE){
            return FALSE;
        }

        return TRUE;
    }
}
