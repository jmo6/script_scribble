package script.scribble.blocks;

import android.graphics.Canvas;
import android.util.Log;

import script.scribble.BlockMenu;
import script.scribble.CodingArea;

public class MoveBlock extends Block {
    public int lastBlockMoveIndex;

    private final String LOG_TAG =  "MoveBlock";

    public MoveBlock() {
        id = Block.MOVE_BLOCK;
        category = BlockMenu.STATEMENT_BLOCK;

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public int execute(CodingArea codingArea) {
        Log.d(LOG_TAG, "Move Block Executed");
        return 0;
    }
}
