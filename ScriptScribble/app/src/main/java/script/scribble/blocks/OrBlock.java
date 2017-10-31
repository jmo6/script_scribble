package script.scribble.blocks;

import android.graphics.Canvas;

import script.scribble.BlockMenu;
import script.scribble.CodingArea;

public class OrBlock extends Block {
    public int lastBlockOrIndex;

    public OrBlock() {
        id = Block.OR_BLOCK;
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
        if (status == TRUE){
            return TRUE;
        }
        status = executeNextBlock(codingArea);
        if (status == ERROR){
            return Block.ERROR;
        }
        if (status == TRUE){
            return TRUE;
        }

        return FALSE;
    }
}
