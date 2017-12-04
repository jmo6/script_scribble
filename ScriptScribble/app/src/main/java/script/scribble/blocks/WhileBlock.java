package script.scribble.blocks;

import android.graphics.Canvas;
import android.graphics.Rect;

import script.scribble.BlockMenu;
import script.scribble.CodingArea;
import script.scribble.util.ImageHandler;
import script.scribble.util.Vector2f;

public class WhileBlock extends Block {
    public WhileBlock() {
        id = Block.WHILE_BLOCK;
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
        // TODO: make sure the next block is a relation or condition block, if not, return ERROR
        int status = ExecuteNextBlock(codingArea);
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
                if(ExecuteNextBlock(codingArea) == ERROR) return ERROR;
            }
            // execute the conditions again
            codingArea.currentExecutingBlockIndex = this.index - 1;
//            status = ExecuteNextBlock(codingArea);
//            if(status == FALSE) {
//                codingArea.currentExecutingBlockIndex = lastBlockInThenIndex + 1;
//                return FALSE;
//            }
//            if(status == ERROR) return ERROR;
//            codingArea.currentExecutingBlockIndex = firstBlockInThenIndex - 1;
        }

        return TRUE;
    }

    @Override
    public Block Clone() {
        WhileBlock ret = new WhileBlock();
        ret.position = new Vector2f(position);
        ret.scale = new Vector2f(scale);
        ret.firstBlockInThenIndex = firstBlockInThenIndex;
        ret.lastBlockInThenIndex = lastBlockInThenIndex;
        ret.index = index;
        return ret;
    }
}
