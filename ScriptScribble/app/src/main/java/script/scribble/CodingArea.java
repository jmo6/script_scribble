package script.scribble;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.Image;
import android.util.Log;

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
    RectF run_btn_rect = new RectF(
            CustomView.screen_width - 150, CustomView.screen_height / 2,
            CustomView.screen_width + 40, CustomView.screen_height / 2 + 100 + 50
    );

    // Temporary attributes for back button
    Paint blackPaint = new Paint();
    RectF back_bnt_rect = new RectF(
            CustomView.screen_width - 150, CustomView.screen_height / 2 + 100 + 20,
            CustomView.screen_width + 40, CustomView.screen_height / 2 + 100 + 100 + 20 + 50
    );

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
        bluePaint.setARGB(255, 0, 0, 255);
    }

    public static boolean Test() {
        CodingArea codingArea = new CodingArea();
        codingArea.blocks.add(new IfBlock());
        codingArea.blocks.get(0).firstBlockInThenIndex = 5;
        codingArea.blocks.get(0).lastBlockInThenIndex = 5;
        codingArea.blocks.get(0).index = 0;
        codingArea.blocks.add(new NotBlock());
        codingArea.blocks.add(new OrBlock());
        codingArea.blocks.add(new IsLeftSpaceOpenBlock());
        codingArea.blocks.add(new IsUpSpaceOpenBlock());

        codingArea.blocks.add(new MoveBlock());
        codingArea.blocks.add(new ElseBlock());
        codingArea.blocks.get(6).firstBlockInThenIndex = 7;
        codingArea.blocks.get(6).lastBlockInThenIndex = 7;
        codingArea.blocks.get(6).index = 6;
        codingArea.blocks.add(new RotateBlock());

        codingArea.blocks.add(new RotateBlock());
        codingArea.blocks.add(new WhileBlock());
        codingArea.blocks.get(9).firstBlockInThenIndex = 13;
        codingArea.blocks.get(9).lastBlockInThenIndex = 13;
        codingArea.blocks.get(9).index = 9;
        codingArea.blocks.add(new AndBlock());
        codingArea.blocks.add(new IsDownSpaceOpenBlock());
        codingArea.blocks.add(new IsRightSpaceOpenBlock());
        codingArea.blocks.add(new MoveBlock());

        OutputWindow.grid = new ArrayList<ArrayList<Integer>>();
        for(int i = 0; i < OutputWindow.num_cells; i++) {
            OutputWindow.grid.add(new ArrayList<Integer>());
            for(int j = 0; j < OutputWindow.num_cells; j++) {
                OutputWindow.grid.get(i).add(OutputWindow.EMPTY);
            }
        }
        codingArea.executing = true;
        while(codingArea.executing) {
            codingArea.Execute(true);
        }
        System.out.println(OutputWindow.character.position.x + ", " + OutputWindow.character.position.y);
        if(OutputWindow.character.position.y == OutputWindow.num_cells / 2 &&
                OutputWindow.character.position.x == OutputWindow.num_cells - 1) {
            OutputWindow.Reset();
            return true;
        }

        OutputWindow.Reset();
        return false;
    }

    // handle moving blocks to block menu
    // call execute when play button is pressed
    void Update(Input input, float blockBarRight) {
        // Look for the RUN button
        // If run button touched then run
        if (input.isRectPressed(run_btn_rect)) {
            executing = true;
            lastExecuteTime = System.currentTimeMillis();
        }
        if(executing && System.currentTimeMillis() >= lastExecuteTime + millisPerExecuteStep) {
            Execute(false);
            lastExecuteTime = System.currentTimeMillis();
        }

        // Look for BACK button
        if(input.isRectPressed(back_bnt_rect)){
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
                } else if(blocks.size() > 1) {
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
        Rect src = new Rect(0, 0, ImageHandler.images[ImageHandler.RUN_BUTTON].getWidth(),
                ImageHandler.images[ImageHandler.RUN_BUTTON].getHeight());
        canvas.drawBitmap(ImageHandler.images[ImageHandler.RUN_BUTTON], src, run_btn_rect, null);

        //Temporary back button for Coding Area
        blackPaint.setColor(Color.BLACK);
        canvas.drawBitmap(ImageHandler.images[ImageHandler.BACK_BUTTON], src, back_bnt_rect, null);
    }

    // loops through blocks array and calls their .execute function
    // handle if a block's execute function returns ERROR
    int Execute(boolean testing) {
        if(!executing) return Block.FALSE;
        // TODO: disable moving of blocks while executing
        if(currentExecutingBlockIndex < blocks.size()) {
            if(blocks.get(currentExecutingBlockIndex).Execute(this) == Block.ERROR)  {
                return Block.ERROR;
            }
            currentExecutingBlockIndex++;
        }
        if(!testing && currentExecutingBlockIndex >= blocks.size()) {
//            executing = false;
//            currentExecutingBlockIndex = 0;
            OutputWindow.Reset();
        } else if(testing && currentExecutingBlockIndex >= blocks.size()) {
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
            int snapInIndex = blockToSnapAboveIndex;
            if(draggedBlockIndex < snapInIndex) {
                snapInIndex--;
            }
            blocks.remove(draggedBlockIndex);
            blocks.add(snapInIndex, toSnapIn);
            // TODO: if there were things above where this was, move the blocks below where it was up
            // TODO: if there are things below where this is now, move the blocks down
            // TODO: loop upwards and see if we're adding above a block that is within other blocks
        } else {
//            System.out.println("below: (" + (toSnapIn.position.x - blockToSnapBelowDist2.x) + ", " + (toSnapIn.position.y - blockToSnapBelowDist2.y) + ")");
            toSnapIn.position = toSnapIn.position.add(blockToSnapBelowDist);
            int snapInIndex = blockToSnapBelowIndex + 1;
            if(draggedBlockIndex < snapInIndex) {
                snapInIndex--;
            }
            blocks.remove(draggedBlockIndex);
            blocks.add(snapInIndex, toSnapIn);
            // TODO: if there were things above where this was, move the blocks below where it was up
            // TODO: if there are things below where this is now, move the blocks down
            // TODO: loop upwards and see if we're adding below a block that is within other blocks
        }
    }
}
