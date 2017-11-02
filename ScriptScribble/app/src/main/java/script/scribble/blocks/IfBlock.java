package script.scribble.blocks;

import android.graphics.Canvas;

import script.scribble.BlockMenu;
import script.scribble.CodingArea;
import script.scribble.util.ImageHandler;

public class IfBlock extends Block {
    // this is the index of the last block within the "then" part of this while Block
    public int firstBlockInThenIndex;
    public int lastBlockInThenIndex;

    public IfBlock() {
        super();
        id = Block.IF_BLOCK;
        category = BlockMenu.CONTROL_BLOCK;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(ImageHandler.images[id], position.x, position.y, null);
//        canvas.drawBitmap(ImageHandler.images[id], position.x, position.y, null);
//        canvas.drawBitmap(tempBMP, new Rect(0, 0, tempBMP.getWidth(), tempBMP.getHeight()), new Rect(0, 0, 700, 450), null);
    }

    @Override
    public int execute(CodingArea codingArea) {
        int status = executeNextBlock(codingArea);
        codingArea.lastIfStatus = status;
        if(status == FALSE) return FALSE;
        if(status == ERROR) return ERROR;

        // if it hasn't returned yet, it must be TRUE, so execute the stuff within the THEN
        codingArea.currentExecutingBlockIndex = firstBlockInThenIndex - 1;
        while(codingArea.currentExecutingBlockIndex != lastBlockInThenIndex) {
            if(executeNextBlock(codingArea) == ERROR) return ERROR;
        }

        return TRUE;
    }
}
