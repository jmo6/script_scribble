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
import script.scribble.util.Vector2f;

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
    private final int RUN_BTN_Y = CustomView.screen_height / 2;

    // Temporary attributes for back button
    Paint blackPaint = new Paint();
    private final int BACK_BTN_WIDTH = 100;
    private final int BACK_BTN_HEIGHT = 100;
    private final int BACK_BTN_X = CustomView.screen_width - BACK_BTN_WIDTH;
    private final int BACK_BTN_Y = CustomView.screen_height / 2 + RUN_BTN_HEIGHT + 20;

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
        draggedBlockIndex = -1;
        blocks = new ArrayList<Block>();
        // debug code
//        blocks.add(new RotateBlock());
//        blocks.add(new RotateBlock());
//        blocks.add(new RotateBlock());
//        blocks.add(new MoveBlock());
//        blocks.add(new RotateBlock());
//        blocks.add(new WhileBlock());
//        ((WhileBlock)blocks.get(5)).firstBlockInThenIndex = 9;
//        ((WhileBlock)blocks.get(5)).lastBlockInThenIndex = 9;
//        ((WhileBlock)blocks.get(5)).index = 5;
//        blocks.add(new AndBlock());
//        blocks.add(new IsRightSpaceOpenBlock());
//        blocks.add(new IsLeftSpaceOpenBlock());
//        blocks.add(new MoveBlock());
//        Execute();

        bluePaint.setARGB(255, 0, 0, 255);

    }

    // handle moving blocks to block menu
    // call execute when play button is pressed
    void Update(Input input, float blockBarRight) {
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
            CustomView.isRunning = false;
        }

        // get a block to be dragged
        // TODO: get the block whose center is closest to the touch
        // TODO: get the actual center of each block or redo the images
        if(draggedBlockIndex == -1) {
            for(int i = 0; i < blocks.size(); i++) {
                Block b = blocks.get(i);
                if(input.isRectPressed(b.getImageRect()) && (Input.dragStatus == Input.DRAGGING_NOTHING ||
                        Input.dragStatus == Input.DRAGGING_BLOCK_FROM_CODING_AREA)) {
                    Input.dragStatus = Input.DRAGGING_BLOCK_FROM_CODING_AREA;
                    draggedBlockIndex = i;
                }
            }
        }
        if(draggedBlockIndex != -1) {
            // move the dragged block
            Block b = blocks.get(draggedBlockIndex);
            Vector2f swipeVec = input.getTouches().get(0).current.sub(input.getTouches().get(0).last);
            b.position = b.position.add(swipeVec);
            if(input.getTouches().size() > 0 && input.getTouches().get(0).isReleased()) {
                if(b.position.x + b.getImageRect().width() / 2 <= blockBarRight) {
                    blocks.remove(draggedBlockIndex);
                } else {
                    SnapInPlace();
                }
                draggedBlockIndex = -1;
            }
        }

        // scrolling the blocks around in the coding area
        if(input.wasRectTouched(codingArea) && (Input.dragStatus == Input.DRAGGING_CODING_AREA || Input.dragStatus == Input.DRAGGING_NOTHING)) {
            Input.dragStatus = Input.DRAGGING_CODING_AREA;
            Vector2f swipeVec = input.getTouches().get(0).current.sub(input.getTouches().get(0).last);

            for(int i = 0; i < blocks.size(); i++) {
                blocks.get(i).position = blocks.get(i).position.add(swipeVec);
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
    }

    void DrawOverOutput(Canvas canvas) {
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
        if(currentExecutingBlockIndex >= blocks.size()) {
            executing = false;
            currentExecutingBlockIndex = 0;
        }
        return Block.TRUE;
    }

    //When This block is placed on CodingArea check all blocks in the array
    // or any that is close to This block if it's close enough call snap in place.
    public void CheckBlocksToSnapIn() {
        int distanceForSnapIn = 2;
        for(int blockBeingChecked = 0; blockBeingChecked < blocks.size(); blockBeingChecked++){       //for loop through all blocks in array
            if ((blocks.get(blockBeingChecked).position.distFrom(this.blocks.get(draggedBlockIndex).position))  <= distanceForSnapIn  ){                              //if block being checked position is within This block area
                SnapInPlace();
                break;
            }
        }
    }

    boolean isBlockSnappee1(Block b) {
        if(b.id == Block.ELSE_BLOCK) return true;
        if(b.id == Block.NOT_BLOCK) return true;
        return false;
    }

    boolean isBlockSnappee2(Block b) {
        if(isBlockSnappee1(b)) return false;
        if(isBlockSnapper(b)) return false;
        return true;
    }

    boolean isBlockSnapper(Block b) {
        if(b.category == BlockMenu.STATEMENT_BLOCK) return true;
        if(b.category == BlockMenu.CONDITION_BLOCK) return true;
        return false;
    }

    Vector2f getTopLeft(Block b) {
        Vector2f toSnapInTopLeft = new Vector2f();
        if(isBlockSnappee1(b)) {
            toSnapInTopLeft = new Vector2f(b.position.x + Block.SNAPPEE1_TOP_X, b.position.y + Block.SNAPPEE1_TOP_Y);
        } else if(isBlockSnappee2(b)) {
            toSnapInTopLeft = new Vector2f(b.position.x + Block.SNAPPEE1_TOP_X, b.position.y + Block.SNAPPEE1_TOP_Y);
        } else {
            toSnapInTopLeft = new Vector2f(b.position.x + Block.SNAPPER_X, b.position.y + Block.SNAPPER_Y);
        }
        return toSnapInTopLeft;
    }

    Vector2f getBottomLeft(Block b) {
        Vector2f toSnapInBottomLeft = new Vector2f();
        if(isBlockSnappee1(b)) {
            toSnapInBottomLeft = new Vector2f(b.position.x + Block.SNAPPEE1_BOTTOM_X, b.position.y + Block.SNAPPEE1_BOTTOM_Y);
        } else if(isBlockSnappee2(b)) {
            toSnapInBottomLeft = new Vector2f(b.position.x + Block.SNAPPEE2_BOTTOM_X, b.position.y + Block.SNAPPEE2_BOTTOM_Y);
        } else {
            toSnapInBottomLeft = new Vector2f(b.position.x + Block.SNAPPER_BOTTOM_X, b.position.y + Block.SNAPPER_BOTTOM_Y);
        }
        return toSnapInBottomLeft;
    }

    //Get attaching block coordinates and snap This block into it.
    public void SnapInPlace() {
        if(blocks.size() == 0) return; // don't snap if this is the first block
        Block toSnapIn = blocks.get(draggedBlockIndex);
        Vector2f toSnapInBottomLeft = getBottomLeft(toSnapIn);
        Vector2f toSnapInTopLeft = getTopLeft(toSnapIn);

        int blockToSnap1IntoIndex = 0;
        int blockToSnap2IntoIndex = 0;
        int blockToSnapAboveIndex = 0;
        int blockToSnapBelowIndex = 0;
        Vector2f blockToSnap1IntoDist = new Vector2f(99999999, 99999999);
        Vector2f blockToSnap2IntoDist = new Vector2f(99999999, 99999999);
        Vector2f blockToSnapAboveDist = new Vector2f(99999999, 99999999);
        Vector2f blockToSnapBelowDist = new Vector2f(99999999, 99999999);

//        Vector2f blockToSnap1IntoDist2 = new Vector2f(99999999, 99999999);
//        Vector2f blockToSnap2IntoDist2 = new Vector2f(99999999, 99999999);
//        Vector2f blockToSnapAboveDist2 = new Vector2f(99999999, 99999999);
//        Vector2f blockToSnapBelowDist2 = new Vector2f(99999999, 99999999);

        // check if we can snap onto a block
        for(int i = 0; i < blocks.size(); i++) {
            if(i == draggedBlockIndex) continue;
            Block b = blocks.get(i);
            // check if we can snap into this block's first slot
            if(isBlockSnappee1(b) || isBlockSnappee2(b)) {
                Vector2f snappee1 = new Vector2f(b.position.x + Block.SNAPPEE1_X, b.position.y + Block.SNAPPEE1_Y);
                Vector2f snap1IntoDist = snappee1.sub(toSnapInTopLeft);
                if(snap1IntoDist.length() < blockToSnap1IntoDist.length()) {
                    blockToSnap1IntoIndex = i;
                    blockToSnap1IntoDist = snap1IntoDist;
//                    blockToSnap1IntoDist2 = b.position;
                }
            }
            // check if we can snap into this block's second slot
            if(isBlockSnappee2(b)) {
                Vector2f snappee2 = new Vector2f(b.position.x + Block.SNAPPEE2_X, b.position.y + Block.SNAPPEE2_Y);
                Vector2f snap2IntoDist = snappee2.sub(toSnapInTopLeft);
                if(snap2IntoDist.length() < blockToSnap2IntoDist.length()) {
                    blockToSnap2IntoIndex = i;
                    blockToSnap2IntoDist = snap2IntoDist;
//                    blockToSnap2IntoDist2 = b.position;
                }
            }
            // check if we can snap above this
            Vector2f snapAboveDist = getTopLeft(b).sub(toSnapInBottomLeft);
            if(snapAboveDist.length() < blockToSnapAboveDist.length()) {
                blockToSnapAboveIndex = i;
                blockToSnapAboveDist = snapAboveDist;
//                blockToSnapAboveDist2 = b.position;
            }
            // check if we can snap below this block
            Vector2f snapBelowDist = getBottomLeft(b).sub(toSnapInTopLeft);
            if(snapBelowDist.length() < blockToSnapBelowDist.length()) {
                blockToSnapBelowIndex = i;
                blockToSnapBelowDist = snapBelowDist;
//                blockToSnapBelowDist2 = b.position;
            }
        }
        if(blockToSnap1IntoDist.length() < blockToSnap2IntoDist.length() &&
                blockToSnap1IntoDist.length() < blockToSnapAboveDist.length() &&
                blockToSnap1IntoDist.length() < blockToSnapBelowDist.length()) {
//            System.out.println("snap1: (" + (toSnapIn.position.x - blockToSnap1IntoDist2.x) + ", " + (toSnapIn.position.y - blockToSnap1IntoDist2.y) + ")");
            toSnapIn.position = toSnapIn.position.add(blockToSnap1IntoDist);
            // TODO: reflect snapping in blocks array
        } else if(blockToSnap2IntoDist.length() < blockToSnapAboveDist.length() &&
                blockToSnap2IntoDist.length() < blockToSnapBelowDist.length()) {
//            System.out.println("snap2: (" + (toSnapIn.position.x - blockToSnap2IntoDist2.x) + ", " + (toSnapIn.position.y - blockToSnap2IntoDist2.y) + ")");
            toSnapIn.position = toSnapIn.position.add(blockToSnap2IntoDist);
            // TODO: reflect snapping in blocks array
        } else if(blockToSnapAboveDist.length() < blockToSnapBelowDist.length()) {
//            System.out.println("above: (" + (toSnapIn.position.x - blockToSnapAboveDist2.x) + ", " + (toSnapIn.position.y - blockToSnapAboveDist2.y) + ")");
            toSnapIn.position = toSnapIn.position.add(blockToSnapAboveDist);
            // TODO: reflect snapping in blocks array
        } else {
//            System.out.println("below: (" + (toSnapIn.position.x - blockToSnapBelowDist2.x) + ", " + (toSnapIn.position.y - blockToSnapBelowDist2.y) + ")");
            toSnapIn.position = toSnapIn.position.add(blockToSnapBelowDist);
            // TODO: reflect snapping in blocks array
        }
    }
}
