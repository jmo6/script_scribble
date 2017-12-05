package script.scribble.blocks;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import script.scribble.BlockMenu;
import script.scribble.CodingArea;
import script.scribble.OutputWindow;
import script.scribble.util.ImageHandler;
import script.scribble.util.Vector2f;

public class IsRightSpaceOpenBlock extends Block {
    private final String LOG_TAG =  "IsRightSpaceOpenBlock";

    public IsRightSpaceOpenBlock() {
        id = Block.IS_RIGHT_SPACE_OPEN_BLOCK;
        category = BlockMenu.CONDITION_BLOCK;
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
        Log.d(LOG_TAG, "Is Right Space Open Executed");
        if((int)(OutputWindow.character.position.x + 1) >= OutputWindow.num_cells) {
            return FALSE;
        }
        if(OutputWindow.grid.get((int)(OutputWindow.character.position.x + 1)).get((int)(OutputWindow.character.position.y)) == OutputWindow.OBSTACLE) {
            return FALSE;
        }
        return TRUE;
    }

    @Override
    public Block Clone() {
        IsRightSpaceOpenBlock ret = new IsRightSpaceOpenBlock();
        ret.position = new Vector2f(position);
        ret.scale = new Vector2f(scale);
        ret.index = index;
        return ret;
    }
}
