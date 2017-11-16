package script.scribble.blocks;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import script.scribble.BlockMenu;
import script.scribble.CodingArea;
import script.scribble.util.ImageHandler;

public class IsLeftSpaceOpenBlock extends Block {
    public IsLeftSpaceOpenBlock() {
        id = Block.IS_LEFT_SPACE_OPEN_BLOCK;
        category = BlockMenu.CONDITION_BLOCK;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        Rect src = new Rect(0, 0, ImageHandler.images[id].getWidth(), ImageHandler.images[id].getHeight());
        Rect dest = new Rect((int) position.x, (int) position.y,
                (int) (position.x + ImageHandler.images[id].getWidth() * scale.x), (int) (ImageHandler.images[id].getHeight() * scale.y));
        canvas.drawBitmap(ImageHandler.images[id], src, dest, null);
    }

    @Override
    public int execute(CodingArea codingArea) {
        Log.d(LOG_TAG, "Is Left Space Open Block Executed");
        return TRUE;
    }

    private final String LOG_TAG =  "IsLeftSpaceOpenBlock";
}
