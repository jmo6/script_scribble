package script.scribble.blocks;

import android.graphics.Canvas;
import android.util.Log;

import script.scribble.BlockMenu;
import script.scribble.CodingArea;

public class IsLeftSpaceOpenBlock extends Block {
    public IsLeftSpaceOpenBlock() {
        id = Block.IS_LEFT_SPACE_OPEN_BLOCK;
        category = BlockMenu.STATEMENT_BLOCK;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        //canvas.drawBitmap(imageIndex, x, y);
    }

    @Override
    public int execute(CodingArea codingArea) {
        Log.d(LOG_TAG, "Is Right Space Open Block Executed");
        return 0;
    }

    private final String LOG_TAG =  "IsLeftSpaceOpenBlock";
}
