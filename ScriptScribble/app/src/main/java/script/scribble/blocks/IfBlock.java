package script.scribble.blocks;

import android.graphics.Canvas;
import android.graphics.Rect;

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
        Rect src = new Rect(0, 0, ImageHandler.images[id].getWidth(), ImageHandler.images[id].getHeight());
        Rect dest = new Rect((int) position.x, (int) position.y,
                (int) (position.x + ImageHandler.images[id].getWidth() * scale.x), (int) (position.y + ImageHandler.images[id].getHeight() * scale.y));
        canvas.drawBitmap(ImageHandler.images[id], src, dest, null);
    }

    @Override
    public int execute(CodingArea codingArea) {
        // TODO: make sure the next block is a relation or condition block, if not, return ERROR
        int status = executeNextBlock(codingArea);
        codingArea.lastIfStatus = status;
        if(status == FALSE) {
            codingArea.currentExecutingBlockIndex = lastBlockInThenIndex + 1;
            return FALSE;
        }
        if(status == ERROR) return ERROR;

        // if it hasn't returned yet, it must be TRUE, so execute the stuff within the THEN
        // TODO: make sure each block in the "THEN" part of the IfBlock is a statement or control block, if not, return ERROR
        codingArea.currentExecutingBlockIndex = firstBlockInThenIndex - 1;
        while(codingArea.currentExecutingBlockIndex != lastBlockInThenIndex) {
            if(executeNextBlock(codingArea) == ERROR) return ERROR;
        }

        return TRUE;
    }
}
