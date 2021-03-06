package script.scribble.blocks;

import android.graphics.Canvas;
import android.graphics.Rect;

import script.scribble.BlockMenu;
import script.scribble.CodingArea;
import script.scribble.util.ImageHandler;
import script.scribble.util.Vector2f;

public class AndBlock extends Block {
    public AndBlock() {
        id = Block.AND_BLOCK;
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
        int status = ExecuteNextBlock(codingArea);
        if (status == ERROR){
            return Block.ERROR;
        }
        if (status == FALSE){
            return FALSE;
        }
        status = ExecuteNextBlock(codingArea);
        if (status == ERROR){
            return Block.ERROR;
        }
        if (status == FALSE){
            return FALSE;
        }

        return TRUE;
    }

    @Override
    public Block Clone() {
        AndBlock ret = new AndBlock();
        ret.position = new Vector2f(position);
        ret.scale = new Vector2f(scale);
        ret.index = index;
        return ret;
    }
}
