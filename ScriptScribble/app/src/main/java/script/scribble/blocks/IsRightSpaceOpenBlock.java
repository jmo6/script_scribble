package script.scribble.blocks;

import android.graphics.Canvas;
import android.util.Log;

import script.scribble.BlockMenu;
import script.scribble.CodingArea;
import script.scribble.OutputWindow;

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
        //canvas.drawBitmap(imageIndex, x, y);
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
