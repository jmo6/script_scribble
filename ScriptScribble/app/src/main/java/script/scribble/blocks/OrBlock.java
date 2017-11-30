package script.scribble.blocks;

import android.graphics.Canvas;
import android.graphics.Rect;

import script.scribble.BlockMenu;
import script.scribble.CodingArea;
import script.scribble.util.ImageHandler;

public class OrBlock extends Block {
    public int lastBlockOrIndex;

    public OrBlock() {
        id = Block.OR_BLOCK;
        category = BlockMenu.RELATION_BLOCK;
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
