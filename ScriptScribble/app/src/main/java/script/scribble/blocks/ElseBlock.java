package script.scribble.blocks;

import android.graphics.Canvas;

import script.scribble.BlockMenu;
import script.scribble.CodingArea;

public class ElseBlock extends Block {
    public int lastBlockInElse;
    public int firstBlockInElse;

    public ElseBlock() {
        id = Block.ELSE_BLOCK;
        category = BlockMenu.CONTROL_BLOCK;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public int execute(CodingArea codingArea) {
        int status = 0;

        if(codingArea.lastIfStatus == TRUE){
            status = FALSE;
        }
        if(codingArea.lastIfStatus == FALSE){
            status = TRUE;
        }
        if(status == FALSE) {
            codingArea.currentExecutingBlockIndex = lastBlockInElse + 1;
            return FALSE;
        }
        // if it hasn't returned yet, it must be TRUE, so execute the stuff within the THEN
        codingArea.currentExecutingBlockIndex = firstBlockInElse - 1;
        while(codingArea.currentExecutingBlockIndex != lastBlockInElse) {
            if(executeNextBlock(codingArea) == ERROR) return ERROR;
        }

        return TRUE;
    }
}
