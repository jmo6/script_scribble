package script.scribble;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.ArrayList;

import script.scribble.blocks.AndBlock;
import script.scribble.blocks.Block;
import script.scribble.blocks.ElseBlock;
import script.scribble.blocks.IfBlock;
import script.scribble.blocks.IsDownSpaceOpenBlock;
import script.scribble.blocks.IsLeftSpaceOpenBlock;
import script.scribble.blocks.IsRightSpaceOpenBlock;
import script.scribble.blocks.IsUpSpaceOpenBlock;
import script.scribble.blocks.MoveBlock;
import script.scribble.blocks.NotBlock;
import script.scribble.blocks.OrBlock;
import script.scribble.blocks.RotateBlock;
import script.scribble.blocks.WhileBlock;
import script.scribble.util.ImageHandler;

public class BlockMenu {
    // categories
    public static final int STATEMENT_BLOCK =  0;
    public static final int CONTROL_BLOCK =    1;
    public static final int CONDITION_BLOCK=   2;
    public static final int RELATION_BLOCK =   3;
    public static final int EVENT_BLOCK =      4;
    private final int NUM_CATEGORIES = 5;

    public ArrayList<ArrayList<Block>> blocks;
    public Block draggedBlock = null;
    public int curCategory = CONTROL_BLOCK;

    RectF blockBar = new RectF(
    CustomView.screen_width / 10,
    0,
        CustomView.screen_width / 10 + CustomView.screen_width / 2.75f,
        CustomView.screen_height);
    Paint yellowPaint = new Paint();

    public BlockMenu() {
        yellowPaint.setColor(Color.YELLOW);
        blocks = new ArrayList<>();


        for(int blocksToAdd = 0; blocksToAdd < NUM_CATEGORIES; blocksToAdd++){
            blocks.add(new ArrayList<Block>());
        }

        // Initialize Statement Blocks
        blocks.get(STATEMENT_BLOCK).add(new MoveBlock());
        blocks.get(STATEMENT_BLOCK).add(new RotateBlock());

        // Initialize Control Blocks
        blocks.get(CONTROL_BLOCK).add(new IfBlock());
        blocks.get(CONTROL_BLOCK).add(new ElseBlock());
        blocks.get(CONTROL_BLOCK).add(new WhileBlock());


        // Initial Condition Blocks
        blocks.get(CONDITION_BLOCK).add(new IsRightSpaceOpenBlock());
        blocks.get(CONDITION_BLOCK).add(new IsLeftSpaceOpenBlock());
        blocks.get(CONDITION_BLOCK).add(new IsDownSpaceOpenBlock());
        blocks.get(CONDITION_BLOCK).add(new IsUpSpaceOpenBlock());


        // Initial Relation Blocks
        blocks.get(RELATION_BLOCK).add(new AndBlock());
        blocks.get(RELATION_BLOCK).add(new OrBlock());
        blocks.get(RELATION_BLOCK).add(new NotBlock());



        // Initial Event Blocks
        // Missing class
        //blocks.get(EVENT_BLOCK).add(new );
        //blocks.get(EVENT_BLOCK).add(new MoveBlock());

        /*
            Need to resize images
        */


        //Position STATEMENT_BLOCKS onto block menu
        blocks.get(STATEMENT_BLOCK).get(0).position.x= blockBar.left + 100;
        blocks.get(STATEMENT_BLOCK).get(1).position.x=blockBar.left - 250;
        blocks.get(STATEMENT_BLOCK).get(1).position.y=blockBar.top;


        /*
        //Position CONTROL_BLOCKS onto block menu
        blocks.get(CONTROL_BLOCK).get(0).position.x = blockBar.left;
        blocks.get(CONTROL_BLOCK).get(0).position.y = blockBar.top;
        blocks.get(CONTROL_BLOCK).get(1).position.x = blockBar.left;
        blocks.get(CONTROL_BLOCK).get(1).position.y = blockBar.top;
        blocks.get(CONTROL_BLOCK).get(2).position.x = blockBar.left;
        blocks.get(CONTROL_BLOCK).get(2).position.y = blockBar.top;
        */


        /*
        //Position CONDITION_BLOCK onto block menu
        blocks.get(CONDITION_BLOCK).get(0).position.x = blockBar.left;
        blocks.get(CONDITION_BLOCK).get(0).position.y = blockBar.top;
        blocks.get(CONDITION_BLOCK).get(1).position.x = blockBar.left;
        blocks.get(CONDITION_BLOCK).get(1).position.y = blockBar.top;
        blocks.get(CONDITION_BLOCK).get(2).position.x = blockBar.left;
        blocks.get(CONDITION_BLOCK).get(2).position.y = blockBar.top;
        blocks.get(CONDITION_BLOCK).get(3).position.x = blockBar.left;
        blocks.get(CONDITION_BLOCK).get(3).position.y = blockBar.top;
        */


        /*
        //Position RELATION_BLOCK onto block menu
        blocks.get(RELATION_BLOCK).get(0).position.x = blockBar.left;
        blocks.get(RELATION_BLOCK).get(0).position.y = blockBar.top;
        blocks.get(RELATION_BLOCK).get(1).position.x = blockBar.left;
        blocks.get(RELATION_BLOCK).get(1).position.y = blockBar.top;
        blocks.get(RELATION_BLOCK).get(2).position.x = blockBar.left;
        blocks.get(RELATION_BLOCK).get(2).position.y = blockBar.top;
        */

    }

    void update() {
        // handle switching categories
        // handle moving blocks to coding area (draggedBlock = new Block of whatever type was pressed)
    }


    void draw(Canvas canvas) {
        // draw categories and blocks within that category
        // draw draggedBlock
        canvas.drawRect(blockBar, yellowPaint);

        final int ALINE_X = 300;
        final int ALINE_Y = 210;



        // Load all CONTROL blocks onto block menu
        for(int i = 0; i < blocks.get(STATEMENT_BLOCK).size();i++){
            Block temp = blocks.get(STATEMENT_BLOCK).get(i);
            canvas.drawBitmap(ImageHandler.images[temp.id],
                    temp.position.x-ALINE_X, temp.position.y-ALINE_Y, null);
        }



        /*
        // Load all CONTROL blocks onto block menu
        for(int i = 0; i < blocks.get(CONTROL_BLOCK).size();i++){
            Block temp = blocks.get(CONTROL_BLOCK).get(i);
            canvas.drawBitmap(ImageHandler.images[temp.id],
                    temp.position.x-ALINE_X, temp.position.y-ALINE_Y, null);
        }
        */


        /*
        // Load all CONTROL blocks onto block menu
        for(int i = 0; i < blocks.get(CONDITION_BLOCK).size();i++){
            Block temp = blocks.get(CONDITION_BLOCK).get(i);
            canvas.drawBitmap(ImageHandler.images[temp.id],
                    temp.position.x-ALINE_X, temp.position.y-ALINE_Y, null);
        }
        */

        /*
        // Load all CONTROL blocks onto block menu
        for (int i = 0; i < blocks.get(RELATION_BLOCK).size(); i++) {
            Block temp = blocks.get(RELATION_BLOCK).get(i);
            canvas.drawBitmap(ImageHandler.images[temp.id],
                    temp.position.x - ALINE_X, temp.position.y - ALINE_Y, null);
        }
        */
    }
}
