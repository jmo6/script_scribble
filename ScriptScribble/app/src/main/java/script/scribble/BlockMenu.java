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
import script.scribble.util.Vector2f;

public class BlockMenu {
    // categories
    public static final int STATEMENT_BLOCK =  0;
    public static final int CONTROL_BLOCK =    1;
    public static final int CONDITION_BLOCK=   2;
    public static final int RELATION_BLOCK =   3;
    public static final int EVENT_BLOCK =      4;

    public ArrayList<ArrayList<Block>> blocks;
    public Block draggedBlock = null;
    public int curCategory = CONTROL_BLOCK;

    private RectF blockBar = new RectF(
    CustomView.screen_width / 10,
    0,
        CustomView.screen_width / 10 + CustomView.screen_width / 2.75f,
        CustomView.screen_height);
    private Paint yellowPaint = new Paint();

    public BlockMenu() {
        yellowPaint.setColor(Color.YELLOW);
        blocks = new ArrayList<>();


        final int NUM_CATEGORIES = 5;
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



        //Position STATEMENT_BLOCKS onto block menu
        final float scaleStmtBlock_X = 0.8f;
        final float scaleStmtBlock_Y = 0.6f;

        final int moveBlock_PosY = 230;
        final int moveBlock_PosX = 50;
        blocks.get(STATEMENT_BLOCK).get(0).position.y= blockBar.left - moveBlock_PosY;
        blocks.get(STATEMENT_BLOCK).get(0).position.x= blockBar.top - moveBlock_PosX;
        blocks.get(STATEMENT_BLOCK).get(0).scale =
                new Vector2f(scaleStmtBlock_X, scaleStmtBlock_Y);

        final int rotateBlock_PosX = 40;
        final int rotateBlock_PosY = 90;
        blocks.get(STATEMENT_BLOCK).get(1).position.x=blockBar.top - rotateBlock_PosX;
        blocks.get(STATEMENT_BLOCK).get(1).position.y=blockBar.left - rotateBlock_PosY;
        blocks.get(STATEMENT_BLOCK).get(1).scale = new
                Vector2f(scaleStmtBlock_X, scaleStmtBlock_Y);



        //Position CONTROL_BLOCKS onto block menu
        final int ifBlock_PosX = 100;
        final int ifBlock_PosY = 150;
        blocks.get(CONTROL_BLOCK).get(0).position.x = blockBar.top + ifBlock_PosX;
        blocks.get(CONTROL_BLOCK).get(0).position.y = blockBar.left + ifBlock_PosY;

        final int elseBlock_PosX = 110;
        final int elseBlock_PosY = 390;
        final float scaleControlBlock_X = 0.3f;
        final float scaleControlBlock_Y = 0.3f;
        blocks.get(CONTROL_BLOCK).get(1).position.x = blockBar.left - elseBlock_PosX;
        blocks.get(CONTROL_BLOCK).get(1).position.y = blockBar.top + elseBlock_PosY;
        blocks.get(CONTROL_BLOCK).get(1).scale = new
                Vector2f(scaleControlBlock_X, scaleControlBlock_Y);

        final int whileBlock_PosX = 310;
        final int whileBlock_PosY = 150;
        blocks.get(CONTROL_BLOCK).get(2).position.x = blockBar.top + whileBlock_PosX;
        blocks.get(CONTROL_BLOCK).get(2).position.y = blockBar.left + whileBlock_PosY;





        //Position CONDITION_BLOCK onto block menu
        final float scaleCondBlock_X = 0.7f;
        final float scaleCondBlock_Y = 0.4f;

        final int rightSpaceBlock_PosX = 120;
        final int rightSpaceBlock_PosY = 570;
        blocks.get(CONDITION_BLOCK).get(0).position.x = blockBar.left - rightSpaceBlock_PosX;
        blocks.get(CONDITION_BLOCK).get(0).position.y = blockBar.top + rightSpaceBlock_PosY;
        blocks.get(CONDITION_BLOCK).get(0).scale = new
                Vector2f(scaleCondBlock_X, scaleCondBlock_Y);

        final int leftSpaceBlock_PosX = 120;
        final int leftSpaceBlock_PosY = 670;
        blocks.get(CONDITION_BLOCK).get(1).position.x = blockBar.left - leftSpaceBlock_PosX;
        blocks.get(CONDITION_BLOCK).get(1).position.y = blockBar.top + leftSpaceBlock_PosY;
        blocks.get(CONDITION_BLOCK).get(1).scale = new
                Vector2f(scaleCondBlock_X, scaleCondBlock_Y);

        final int upSpaceBlock_PosX = 120;
        final int upSpaceBlock_PosY = 770;
        blocks.get(CONDITION_BLOCK).get(2).position.x = blockBar.left - upSpaceBlock_PosX;
        blocks.get(CONDITION_BLOCK).get(2).position.y = blockBar.top + upSpaceBlock_PosY;
        blocks.get(CONDITION_BLOCK).get(2).scale = new
                Vector2f(scaleCondBlock_X, scaleCondBlock_Y);

        final int downSpaceBlock_PosX = 120;
        final int downSpaceBlock_PosY = 870;
        blocks.get(CONDITION_BLOCK).get(3).position.x = blockBar.left - downSpaceBlock_PosX;
        blocks.get(CONDITION_BLOCK).get(3).position.y = blockBar.top + downSpaceBlock_PosY;
        blocks.get(CONDITION_BLOCK).get(3).scale = new
                Vector2f(scaleCondBlock_X, scaleCondBlock_Y);



        //Position RELATION_BLOCK onto block menu
        final float scaleRelationBlock_X = 0.4f;
        final float scaleRelationBlock_Y = 0.4f;

        final int andBlock_PosY = 1060;
        final int andBlock_PosX = 0;
        blocks.get(RELATION_BLOCK).get(0).position.x = blockBar.left + andBlock_PosX;
        blocks.get(RELATION_BLOCK).get(0).position.y = blockBar.top + andBlock_PosY;

        final int orBlock_PosX = 190;
        final int orBlock_PosY = 1060;
        blocks.get(RELATION_BLOCK).get(1).position.x = blockBar.left + orBlock_PosX;
        blocks.get(RELATION_BLOCK).get(1).position.y = blockBar.top + orBlock_PosY;

        final int notBlock_PosX = 40;
        final int notBlock_PosY = 1300;
        blocks.get(RELATION_BLOCK).get(2).position.x = blockBar.left + notBlock_PosX;
        blocks.get(RELATION_BLOCK).get(2).position.y = blockBar.top + notBlock_PosY;
        blocks.get(RELATION_BLOCK).get(2).scale = new
                Vector2f(scaleRelationBlock_X, scaleRelationBlock_Y);



    }

    void Update() {
        // handle switching categories
        // handle moving blocks to coding area (draggedBlock = new Block of whatever type was pressed)
    }

    void Draw(Canvas canvas) {
        // draw categories and blocks within that category
        // draw draggedBlock
        canvas.drawRect(blockBar, yellowPaint);

        // Place all STATEMENT blocks onto block menu
        for(int blocksToDraw = 0; blocksToDraw < blocks.get(STATEMENT_BLOCK).size(); blocksToDraw++){
            Block statementBlock = blocks.get(STATEMENT_BLOCK).get(blocksToDraw);
            statementBlock.Draw(canvas);
        }

        // Place all CONTROL blocks onto block menu
        for(int blocksToDraw = 0; blocksToDraw < blocks.get(CONTROL_BLOCK).size(); blocksToDraw++){
            Block controlBlock = blocks.get(CONTROL_BLOCK).get(blocksToDraw);
            controlBlock.Draw(canvas);
        }

        // Place all CONDITION blocks onto block menu
        for(int blocksToDraw = 0; blocksToDraw < blocks.get(CONDITION_BLOCK).size();blocksToDraw++){
            Block conditionBlock = blocks.get(CONDITION_BLOCK).get(blocksToDraw);
            conditionBlock.Draw(canvas);
        }

        // Place all RELATION blocks onto block menu
        for (int blocksToDraw = 0; blocksToDraw < blocks.get(RELATION_BLOCK).size(); blocksToDraw++){
            Block relationBlock = blocks.get(RELATION_BLOCK).get(blocksToDraw);
            relationBlock.Draw(canvas);
        }

    }
}
