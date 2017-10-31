package script.scribble.blocks;

import android.graphics.Canvas;
import android.util.Log;

import script.scribble.BlockMenu;
import script.scribble.CodingArea;

public class RotateBlock extends Block {
    public int lastBlockRotateIndex;

    private final String LOG_TAG =  "RotateBlock";

    public RotateBlock() {
        id = Block.ROTATE_BLOCK;
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
        Log.d(LOG_TAG, "Rotate Block Executed");
        return 0;
    }
}
