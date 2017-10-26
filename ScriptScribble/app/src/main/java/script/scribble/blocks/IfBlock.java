package script.scribble.blocks;

import android.graphics.Canvas;

import script.scribble.CodingArea;

/**
 * Created by Jordan on 10/25/17.
 */

public class IfBlock extends Block {
    public int lastBlockIfIndex;

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public int execute(CodingArea codingArea) {
        // if the conditional returns TRUE,
        // execute blocks within if
        // handle if blocks return ERROR
        return 0;
    }
}
