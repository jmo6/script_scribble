package script.scribble.blocks;

import android.graphics.Canvas;

import script.scribble.BlockMenu;
import script.scribble.CodingArea;

public class ElseBlock extends Block {
    public int lastBlockElseIndex;

    public ElseBlock() {
        id = Block.ELSE_BLOCK;
        category = BlockMenu.CONTROL_BLOCK;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public int execute(CodingArea codingArea) {
        int status = executeNextBlock(codingArea);
        if(status == FALSE) return FALSE;
        if(status == ERROR) return ERROR;

        return 0;
    }
}
