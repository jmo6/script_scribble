package script.scribble;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.Image;

import java.lang.reflect.Array;
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
import script.scribble.util.Rectf;
import script.scribble.util.Vector2f;

public class BlockMenu {
    // categories
    public static final int STATEMENT_BLOCK =  0;
    public static final int CONTROL_BLOCK =    1;
    public static final int CONDITION_BLOCK=   2;
    public static final int RELATION_BLOCK =   3;
    public static final int EVENT_BLOCK =      4;
    public static final int NUM_CATEGORIES =   4;

    public ArrayList<ArrayList<Block>> blocks;
    public Block draggedBlock = null;
    public int curCategory = STATEMENT_BLOCK;
    private ArrayList<RectF> categoryButtons;

    public RectF blockBar = new RectF(
            CustomView.screen_width / 10,
            0,
            CustomView.screen_width / 10 + CustomView.screen_width / 2.75f,
            CustomView.screen_height);
    private RectF categoryBar = new RectF(
            0,
            0,
            CustomView.screen_width / 10,
            CustomView.screen_height);

    private Paint yellowPaint = new Paint();
    private Paint cyanPaint = new Paint();
    private Paint magentaPaint = new Paint();
    private CodingArea codingArea;

    public BlockMenu(CodingArea codingArea) {
        this.codingArea = codingArea;
        yellowPaint.setColor(Color.YELLOW);
        cyanPaint.setColor(Color.CYAN);
        magentaPaint.setColor(Color.MAGENTA);

        blocks = new ArrayList<>();
        categoryButtons = new ArrayList<>();

        final float padding = 20.0f;

        // initialize the category buttons
        for(int i = 0; i < NUM_CATEGORIES; i++){
            blocks.add(new ArrayList<Block>());
            categoryButtons.add(new RectF(0, i * CustomView.screen_width / 10 + i * padding,
                    CustomView.screen_width / 10, (i + 1) * CustomView.screen_width / 10 + i * padding));
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

        // initialize the positions of the blocks
        for(int i = 0; i < NUM_CATEGORIES; i++) {
            for(int j = 0; j < blocks.get(i).size(); j++) {
                Block block = blocks.get(i).get(j);
                block.position.x = blockBar.left + blockBar.width() / 2.0f - ImageHandler.images[block.id].getWidth() * block.scale.x / 2.0f;
                block.position.y = j * ImageHandler.images[block.id].getHeight() * block.scale.y;
            }
        }
    }

    void Update(Input input) {
        // handle switching categories
        for(int i = 0; i < NUM_CATEGORIES; i++) {
            if(input.isRectPressed(categoryButtons.get(i))) {
                curCategory = i;
                break;
            }
        }
        // handle the making of the dragged block when a block in the block menu is pressed
        for(int i = 0; i < blocks.get(curCategory).size(); i++) {
            Block block = blocks.get(curCategory).get(i);
            if(input.isRectPressed(block.getImageRect()) &&
                    (Input.dragStatus == Input.DRAGGING_BLOCK_FROM_BLOCK_MENU || Input.dragStatus == Input.DRAGGING_NOTHING)) {
                if(draggedBlock == null) {
                    draggedBlock = block.Clone();
                }
            }
        }
        // dragging for the dragged block
        if(draggedBlock != null && input.wasRectTouched(draggedBlock.getImageRect()) &&
                (Input.dragStatus == Input.DRAGGING_BLOCK_FROM_BLOCK_MENU || Input.dragStatus == Input.DRAGGING_NOTHING)) {
            Input.dragStatus = Input.DRAGGING_BLOCK_FROM_BLOCK_MENU;
            Vector2f swipeVec = input.getTouches().get(0).current.sub(input.getTouches().get(0).last);
            draggedBlock.position = draggedBlock.position.add(swipeVec);
        }
        // if block is released on CodingArea, pass it on to it, either way we reset draggedBlock
        if(draggedBlock != null && input.getTouches().size() > 0 && input.getTouches().get(0).isReleased()) {
            if(draggedBlock.position.x + draggedBlock.getImageRect().width() / 2 > blockBar.right &&
                    draggedBlock.position.y + draggedBlock.getImageRect().height() / 2 > CustomView.screen_height / 2) {
                codingArea.blocks.add(draggedBlock);
                // snap into place
                if(codingArea.blocks.size() != 1) {
                    codingArea.draggedBlockIndex = codingArea.blocks.size() - 1;
                    codingArea.SnapInPlace();
                    codingArea.draggedBlockIndex = -1;
                }
            }
            draggedBlock = null;
        }

        // dragging the block menu itself
        if(input.wasRectTouched(blockBar) && (Input.dragStatus == Input.DRAGGING_BLOCK_MENU || Input.dragStatus == Input.DRAGGING_NOTHING)) {
            Input.dragStatus = Input.DRAGGING_BLOCK_MENU;
            Vector2f swipeVec = input.getTouches().get(0).current.sub(input.getTouches().get(0).last);
            blockBar.left += swipeVec.x;
            if(blockBar.left > CustomView.screen_width / 10) {
                swipeVec.x -= blockBar.left - CustomView.screen_width / 10;
                blockBar.left = CustomView.screen_width / 10;
            }
            blockBar.right += swipeVec.x;
            categoryBar.left += swipeVec.x;
            categoryBar.right += swipeVec.x;
            for(int i = 0; i < NUM_CATEGORIES; i++) {
                categoryButtons.get(i).left += swipeVec.x;
                categoryButtons.get(i).right += swipeVec.x;
                for(int j = 0; j < blocks.get(i).size(); j++) {
                    blocks.get(i).get(j).position.x += swipeVec.x;
                }
            }
        }
    }

    void Draw(Canvas canvas) {
        canvas.drawRect(blockBar, yellowPaint);
        canvas.drawRect(categoryBar, cyanPaint);

        // draw category buttons
        Rect src = new Rect(0, 0, ImageHandler.images[ImageHandler.STATEMENT].getWidth(),
                ImageHandler.images[ImageHandler.STATEMENT].getHeight());
        for(int i = 0; i < NUM_CATEGORIES; i++) {
            canvas.drawBitmap(ImageHandler.images[ImageHandler.STATEMENT + i], src, categoryButtons.get(i), null);
//            canvas.drawRect(categoryButtons.get(i), magentaPaint);
        }

        // draw available blocks in block menu
        for(int i = 0; i < blocks.get(curCategory).size(); i++) {
            blocks.get(curCategory).get(i).Draw(canvas);
        }

        // draw the dragged block if we have one
        if(draggedBlock != null) {
            draggedBlock.Draw(canvas);
        }
        if(codingArea.draggedBlockIndex != -1) {
            codingArea.blocks.get(codingArea.draggedBlockIndex).Draw(canvas);
        }
    }
}
