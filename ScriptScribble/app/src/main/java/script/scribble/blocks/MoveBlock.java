package script.scribble.blocks;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import script.scribble.BlockMenu;
import script.scribble.CodingArea;
import script.scribble.OutputWindow;
import script.scribble.Character;
import script.scribble.util.ImageHandler;
import script.scribble.util.Vector2f;

public class MoveBlock extends Block {
    private final String LOG_TAG =  "MoveBlock";

    public MoveBlock() {
        id = Block.MOVE_BLOCK;
        category = BlockMenu.STATEMENT_BLOCK;
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
        if(OutputWindow.character.direction % 4 == Character.UP) {
            OutputWindow.character.position.y--;
        } else if(OutputWindow.character.direction % 4 == Character.LEFT) {
            OutputWindow.character.position.x--;
        } else if(OutputWindow.character.direction % 4 == Character.DOWN) {
            OutputWindow.character.position.y++;
        } else if(OutputWindow.character.direction % 4 == Character.RIGHT) {
            OutputWindow.character.position.x++;
        }
        if(OutputWindow.character.position.x < 0) OutputWindow.character.position.x = 0;
        if(OutputWindow.character.position.y < 0) OutputWindow.character.position.y = 0;
        if(OutputWindow.character.position.x >= OutputWindow.grid.size()) OutputWindow.character.position.x = OutputWindow.grid.size() - 1;
        if(OutputWindow.character.position.y >= OutputWindow.grid.size()) OutputWindow.character.position.y = OutputWindow.grid.size() - 1;

        if(OutputWindow.grid.get((int)OutputWindow.character.position.x).get((int)OutputWindow.character.position.y) == OutputWindow.GOAL) {
            // this is the end for you Anakin, I have the high ground
            OutputWindow.Reset();
        }
        Log.d(LOG_TAG, "Move Block Executed");
        return TRUE;
    }

    @Override
    public Block Clone() {
        MoveBlock ret = new MoveBlock();
        ret.position = new Vector2f(position);
        ret.scale = new Vector2f(scale);
        ret.index = index;
        return ret;
    }
}
