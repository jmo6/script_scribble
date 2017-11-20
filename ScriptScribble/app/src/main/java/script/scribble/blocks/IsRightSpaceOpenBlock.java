package script.scribble.blocks;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import script.scribble.BlockMenu;
import script.scribble.CodingArea;
import script.scribble.OutputWindow;
import script.scribble.util.ImageHandler;

public class IsRightSpaceOpenBlock extends Block {
    public IsRightSpaceOpenBlock() {
        id = Block.IS_RIGHT_SPACE_OPEN_BLOCK;
        category = BlockMenu.CONDITION_BLOCK;
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
        Log.d(LOG_TAG, "Is Right Space Open Executed");
        if(OutputWindow.grid.get((int)OutputWindow.character.position.y).size() <= (int)(OutputWindow.character.position.x + 1)) {
            return FALSE;
        }
        if(OutputWindow.grid.get((int)OutputWindow.character.position.y).get((int)(OutputWindow.character.position.x + 1)) == OutputWindow.OBSTACLE) {
            return FALSE;
        }
        return TRUE;
    }

    private final String LOG_TAG =  "IsRightSpaceOpenBlock";
}
