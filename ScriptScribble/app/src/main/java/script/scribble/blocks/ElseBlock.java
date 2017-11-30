package script.scribble.blocks;

import android.graphics.Canvas;
import android.graphics.Rect;

import script.scribble.BlockMenu;
import script.scribble.CodingArea;
import script.scribble.util.ImageHandler;

public class ElseBlock extends Block {
    public int lastBlockInElse;
    public int firstBlockInElse;

    public ElseBlock() {
        id = Block.ELSE_BLOCK;
        category = BlockMenu.CONTROL_BLOCK;
    }

    @Override
    public void Update() {

    }

    @Override
    public void Draw(Canvas canvas) {
        Rect src = new Rect(0, 0, ImageHandler.images[id].getWidth(), ImageHandler.images[id].getHeight());
        Rect dest = new Rect((int) position.x, (int) position.y,
                (int) (position.x + ImageHandler.images[id].getWidth() * scale.x), (int) (position.y + ImageHandler.images[id].getHeight() * scale.y));
        canvas.drawBitmap(ImageHandler.images[id], src, dest, null);
    }

    @Override
    public int Execute(CodingArea codingArea) {
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
