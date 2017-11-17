package script.scribble.blocks;

import android.graphics.Canvas;
import android.graphics.Rect;

import script.scribble.BlockMenu;
import script.scribble.CodingArea;
import script.scribble.util.ImageHandler;

public class NotBlock extends Block {
    public int lastBlockNotIndex;

    public NotBlock() {
        id = Block.NOT_BLOCK;
        category = BlockMenu.RELATION_BLOCK;
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
        if(status == ERROR) return ERROR;
        if(status == TRUE) return FALSE;
        return TRUE;
    }
}
