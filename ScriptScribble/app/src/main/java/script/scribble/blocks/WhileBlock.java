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
        // TODO: make sure the next block is a relation or condition block, if not, return ERROR
        int status = executeNextBlock(codingArea);
        if(status == FALSE) {
            codingArea.currentExecutingBlockIndex = lastBlockInThenIndex + 1;
            return FALSE;
        }
        if(status == ERROR) return ERROR;

        // if it hasn't returned yet, it must be TRUE, so execute the stuff within the THEN
        // TODO: make sure each block in the "THEN" part of the IfBlock is a statement or control block, if not, return ERROR
        codingArea.currentExecutingBlockIndex = firstBlockInThenIndex - 1;
        if(status == TRUE) {
            while(codingArea.currentExecutingBlockIndex != lastBlockInThenIndex) {
                if(executeNextBlock(codingArea) == ERROR) return ERROR;
            }
            // execute the conditions again
            codingArea.currentExecutingBlockIndex = this.index - 1;
//            status = executeNextBlock(codingArea);
//            if(status == FALSE) {
//                codingArea.currentExecutingBlockIndex = lastBlockInThenIndex + 1;
//                return FALSE;
//            }
//            if(status == ERROR) return ERROR;
//            codingArea.currentExecutingBlockIndex = firstBlockInThenIndex - 1;
        }

        return TRUE;
    }
}
