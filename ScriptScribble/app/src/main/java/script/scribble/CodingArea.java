package script.scribble;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

import script.scribble.blocks.AndBlock;
import script.scribble.blocks.Block;
import script.scribble.blocks.IfBlock;
import script.scribble.blocks.IsLeftSpaceOpenBlock;
import script.scribble.blocks.IsRightSpaceOpenBlock;
import script.scribble.blocks.MoveBlock;
import script.scribble.blocks.RotateBlock;
import script.scribble.blocks.WhileBlock;
import script.scribble.util.ImageHandler;
import script.scribble.util.Input;

public class CodingArea {
    public ArrayList<Block> blocks;
    public int draggedBlockIndex;
    public int currentExecutingBlockIndex;
    public int lastIfStatus;

    // Temporary Attributes for run button
    Paint redPaint = new Paint();
    private final String CODING_AREA = "BACK_BUTTON EXECUTED";
    private final int RUN_BTN_WIDTH = 100;
    private final int RUN_BTN_HEIGHT = 100;
    private final int RUN_BTN_X = CustomView.screen_width - RUN_BTN_WIDTH;
    private final int RUN_BTN_Y = CustomView.screen_height / 2 - RUN_BTN_HEIGHT;

    // Temporary attributes for back button
    Paint blackPaint = new Paint();
    private final int BACK_BTN_WIDTH = 100;
    private final int BACK_BTN_HEIGHT = 100;
    private final int BACK_BTN_X = CustomView.screen_width - BACK_BTN_WIDTH;
    private final int BACK_BTN_Y = 0;

    public boolean executing = false;
    final long millisPerExecuteStep = 1000;
    long lastExecuteTime = 0;

    RectF codingArea = new RectF(
        0,
        CustomView.screen_height / 2,
        CustomView.screen_width,
        CustomView. screen_height);
    Paint bluePaint = new Paint();


    public CodingArea() {
        blocks = new ArrayList<Block>();
        // debug code
        blocks.add(new RotateBlock());
        blocks.add(new RotateBlock());
        blocks.add(new RotateBlock());
        blocks.add(new MoveBlock());
        blocks.add(new RotateBlock());
        blocks.add(new WhileBlock());
        ((WhileBlock)blocks.get(5)).firstBlockInThenIndex = 9;
        ((WhileBlock)blocks.get(5)).lastBlockInThenIndex = 9;
        ((WhileBlock)blocks.get(5)).index = 5;
        blocks.add(new AndBlock());
        blocks.add(new IsRightSpaceOpenBlock());
        blocks.add(new IsLeftSpaceOpenBlock());
        blocks.add(new MoveBlock());
//        Execute();

        bluePaint.setARGB(255, 0, 0, 255);

    }

    // handle moving blocks to block menu
    // call execute when play button is pressed
    void Update(Input input) {
        // Look for the RUN button
        // If run button touched then run
        if (input.isRectPressed(RUN_BTN_X, RUN_BTN_Y, RUN_BTN_WIDTH, RUN_BTN_HEIGHT)) {
            executing = true;
            lastExecuteTime = System.currentTimeMillis();
        }
        if(executing && System.currentTimeMillis() >= lastExecuteTime + millisPerExecuteStep) {
            Execute();
            lastExecuteTime = System.currentTimeMillis();
        }

        // Look for BACK button
        if(input.isRectPressed(BACK_BTN_X, BACK_BTN_Y,
                BACK_BTN_WIDTH, BACK_BTN_HEIGHT)){
            // TODO: mutexes if needed
            System.out.println("back to main menu");
            CustomView.isRunning = false;
        }

        // block dragging
        for(int i = 0; i < blocks.size(); i++) {
            Block b = blocks.get(i);
            if(input.wasRectTouched(b.position.x, b.position.y,
                    ImageHandler.images[b.id].getWidth() * b.scale.x,
                    ImageHandler.images[b.id].getHeight() * b.scale.y)) {
                // block is touched, move block
                b.position.x += input.getTouches().get(0).current.x - input.getTouches().get(0).last.x;
                b.position.y += input.getTouches().get(0).current.y - input.getTouches().get(0).last.y;
            }
        }
    }

    // draw blocks in coding area
    // draw draggedBlock
    void Draw(Canvas canvas) {
        canvas.drawRect(codingArea, bluePaint);

        for(int i = 0; i < blocks.size(); i++) {
            blocks.get(i).Draw(canvas);
        }

        //Temporary run button for Coding Area
        redPaint.setColor(Color.RED);
        canvas.drawRect(RUN_BTN_X, RUN_BTN_Y, RUN_BTN_X + RUN_BTN_WIDTH, RUN_BTN_Y + RUN_BTN_HEIGHT, redPaint);

        //Temporary back button for Coding Area
        blackPaint.setColor(Color.BLACK);
        canvas.drawRect(BACK_BTN_X, BACK_BTN_Y, BACK_BTN_X + BACK_BTN_WIDTH, BACK_BTN_Y + BACK_BTN_HEIGHT, blackPaint);
    }

    // loops through blocks array and calls their .execute function
    // handle if a block's execute function returns ERROR
    int Execute() {
        if(!executing) return Block.FALSE;
        // TODO: disable moving of blocks while executing
        if(currentExecutingBlockIndex < blocks.size()) {
            if(blocks.get(currentExecutingBlockIndex).Execute(this) == Block.ERROR)  {
                return Block.ERROR;
            }
            currentExecutingBlockIndex++;
        }
        if(currentExecutingBlockIndex >= blocks.size()) executing = false;
        return Block.TRUE;
    }

    //When This block is placed on CodingArea check all blocks in the array
    // or any that is close to This block if it's close enough call snap in place.
    public void CheckBlocksToSnapIn() {
        int distanceForSnapIn = 2;
        for(int blockBeingChecked = 0; blockBeingChecked < blocks.size(); blockBeingChecked++){       //for loop through all blocks in array
            if ((blocks.get(blockBeingChecked).position.distFrom(this.blocks.get(draggedBlockIndex).position))  <= distanceForSnapIn  ){                              //if block being checked position is within This block area
                SnapInPlace(blocks.get(blockBeingChecked));
                break;
            }
        }
    }

    //Get attaching block coordinates and snap This block into it.
    public void SnapInPlace(Block attachingBlock) {

        //Check which 2 points of attaching block is closer to This block.
        //Subtract the distance of the 2 points with This block and compare which one is less
        //and move thisBlock to that point


        /*
        int distanceOfTopLeftCornerToTopIndentionPosition = blocks.get(attachingBlock).position

        int distanceToTopIndention = (blocks.get(attachingBlock).position.distFrom(this.blocks.get(draggedBlockIndex).position) ;
        int distanceToBottomIndention = (blocks.get(attachingBlock).position.distFrom(this.blocks.get(draggedBlockIndex).position);

        if(distanceToTopIndention < distanceToBottomIndention){
            thisBlockPosition = attachingBlockTopIndentPosition;
        }
        else(){
            thisBlockPosition = attachingBlockBottomIndentPosition;

        */

    }
}
