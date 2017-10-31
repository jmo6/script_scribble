package script.scribble.blocks;

import android.graphics.Canvas;

import script.scribble.BlockMenu;
import script.scribble.CodingArea;

public class WhileBlock extends Block {
    // this is the index of the last block within the "then" part of this while Block
    public int firstBlockInThenIndex;
    public int lastBlockInThenIndex;

    public WhileBlock() {
        id = Block.WHILE_BLOCK;
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
        int status = executeNextBlock(codingArea);
        if(status == FALSE) return FALSE;
        if(status == ERROR) return ERROR;

        // if it hasn't returned yet, it must be TRUE, so execute the stuff within the THEN
        codingArea.currentExecutingBlockIndex = firstBlockInThenIndex - 1;
        while(status == TRUE) {
            while(codingArea.currentExecutingBlockIndex != lastBlockInThenIndex) {
                if(executeNextBlock(codingArea) == ERROR) return ERROR;
            }
            // execute the conditions again
            codingArea.currentExecutingBlockIndex = this.index;
            status = executeNextBlock(codingArea);
            if(status == FALSE) return FALSE;
            if(status == ERROR) return ERROR;
            codingArea.currentExecutingBlockIndex = firstBlockInThenIndex - 1;
        }

        return TRUE;
    }
}
