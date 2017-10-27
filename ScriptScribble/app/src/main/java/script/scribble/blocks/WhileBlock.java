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
        codingArea.currentExecutingBlockIndex = firstBlockInThenIndex;
        while(status == TRUE) {
            while(codingArea.currentExecutingBlockIndex != lastBlockInThenIndex + 1) {
                // make sure we don't go past blocks.size()
                if(codingArea.currentExecutingBlockIndex >= codingArea.blocks.size()) {
                    // TODO: set a global error string that we can display to the user
                    System.err.println("IF block does not have any condition block or statement blocks within it.");
                    return ERROR;
                }
                // execute the current block
                if(codingArea.blocks.get(codingArea.currentExecutingBlockIndex).execute(codingArea) == Block.ERROR) {
                    return Block.ERROR; // propagate errors up
                }
                // go to the next block
                codingArea.currentExecutingBlockIndex++;
            }
            codingArea.currentExecutingBlockIndex = this.index + 1;
            status = codingArea.blocks.get(codingArea.currentExecutingBlockIndex).execute(codingArea);
            if(status == FALSE) return FALSE;
            if(status == ERROR) return ERROR;
        }

        return TRUE;
    }
}
