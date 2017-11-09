package script.scribble.blocks;

import android.graphics.Canvas;
import android.util.Log;

import script.scribble.BlockMenu;
import script.scribble.CodingArea;
import script.scribble.OutputWindow;
import script.scribble.Character;

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
        if(OutputWindow.character.direction % 4 == Character.UP) {
            OutputWindow.character.position.y--;
        } else if(OutputWindow.character.direction % 4 == Character.LEFT) {
            OutputWindow.character.position.x--;
        } else if(OutputWindow.character.direction % 4 == Character.DOWN) {
            OutputWindow.character.position.y++;
        } else if(OutputWindow.character.direction % 4 == Character.RIGHT) {
            OutputWindow.character.position.x++;
        }
        Log.d(LOG_TAG, "Move Block Executed");
        return TRUE;
    }
}
