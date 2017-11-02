package script.scribble;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.content.Context;
import android.view.Display;
import android.graphics.Point;
import android.view.View;
import android.view.WindowManager;
import android.graphics.Canvas;

import java.util.ArrayList;

import script.scribble.util.ImageHandler;
import script.scribble.util.Input;
import script.scribble.util.Touch;
import script.scribble.util.Vector2f;

/* TODO's
    Initial Blocks:
        Statement Blocks:
            move
            rotate
        Control Blocks:
            if-else
            while
        Condition Blocks:
            rightSpaceIsOpen
            leftSpaceIsOpen
            aboveSpaceIsOpen
            belowSpaceIsOpen
        Relation Blocks:
            and
            or
            not
        Event Blocks:
            whenRunIsPressed
            whenButtonIsPressed
 */

/*
BlockMenu class
    Blocks[] blocks - double array, first dimension is category, second dimension is array of blocks
        so you can do something like
            BlockMenu.blocks[curCategory].length to get how many blocks there are of the current category
    int/enum curCategory - can be the following values (enum)
        STATEMENT_BLOCKS
        CONTROL_BLOCKS
        CONDITIONAL_BLOCKS
        RELATIONAL_BLOCKS
        EVENT_BLOCKS
        also have enums for each category for the specific block type within each category
    Block curDraggedBlock

    update()
        handle movement of blocks from menu to coding area
            when you start a drag on a block, it will set curDraggedBlock to a new block of the selected type
        handle changing of block categories
        handle movement of the block menu itself
    draw()
        draw blocks in menu and being moved to coding area

CodingArea class
    Block[] blocks - array of all the blocks in the coding area, ordered by the sequence it will be executed in
    int curExecutingBlock

    update()
        handle movement of the coding area itself
        handle movement of blocks from coding area to menu
            also handle movement of blocks within the coding area block sequence
            when you start a drag on a block, it will set curDraggedBlock to a new block of the selected type
    draw()
        draw blocks in coding area and being moved to menu
        either have them able to move the blocks around freely or have them snap into a set placement
            the latter is easier when we do things like pop-up dialog boxes telling them where a "syntax error" is for example
        handle drawing of dialog boxes for syntax errors
    execute()
        handle execution of blocks

Block class
    image
    position
    index

    update()
    draw()
    int execute(CodingArea codingArea)
        returns a status (enum - ERROR, TRUE, FALSE) - TRUE also means no errors if it's not a conditional block

MoveBlock
    overrides execute()
        very simple, just do Character.move() (when we get around to making a character)
RotateBlock
    overrides execute()
        very simple, just do Character.rotate() (when we get around to making a character)
IfBlock
    lastBlockInIfIndex
    overrides execute()
        executes the next block in CodingArea.blocks (this should be the conditional attached to the if) to see if it returns true or false
            also check if the block after the next is a relational block, if so, do the thing
        if the conditional is true, execute things in the CodingArea.blocks array until curExecutingBlock == lastBlockInIfIndex
    have to think about how to handle expanding the IfBlock so that multiple blocks can fit inside.
WhileBlock
    like an IfBlock, but it does the things within it in a loop until the conditional is false,
        that is, when curExecutingBlock == lastBlockInIfIndex, it sets curExecutingBlock = the while block's index + 1 and keeps going
    Keep track of how many iterations we've done, and if it goes past a certain number, stop the program (prevent infinite loop)
EventBlocks
    Event blocks, when executed, will add a listener to an EventListener class
    this EventListener class will be called on touch input, and will determine if the event was triggered based on the input,
    if the event was triggered, it will call the event block's eventExecute function, which will just execute everything within it
The rest of the blocks are pretty trivial, the relational blocks need only be in the array, the control blocks will handle the execution of them
 */

public class CustomView extends SurfaceView implements Runnable {
    Context context;
    Input input;
    Thread myThread;
    SurfaceHolder myHolder;
    int screen_width, screen_height;
    boolean isRunning;
    BlockMenu blockMenu;
    CodingArea codingArea;


    // create ImageHandler object
    ImageHandler imageHandler = new ImageHandler(this);

    // declare variables here
//    Paint blackPaint, redPaint, greenPaint, bluePaint;

    public CustomView(Context context, Input input) {
        super(context);
        this.context = context;
        this.input = input;
        this.myThread = new Thread(this);
        isRunning = true;
        this.myHolder = getHolder();

        // get screen size
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screen_width = size.x;
        screen_height = size.y;

        // initialize variables here
//        blackPaint = new Paint();
//        blackPaint.setARGB(255, 0, 0, 0);

//        redPaint = new Paint();
//        redPaint.setARGB(255, 255, 0, 0);
//
//        greenPaint = new Paint();
//        greenPaint.setARGB(255, 0, 255, 0);
//
//        bluePaint = new Paint();
//        bluePaint.setARGB(255, 0, 0, 255);

        blockMenu = new BlockMenu();
        codingArea = new CodingArea();
        ImageHandler.loadImages(this.context);
        this.myThread.start();
    }

    public void Draw(Canvas canvas) {
        blockMenu.draw(canvas);
        codingArea.draw(canvas);
    }



    public void Update() {
        codingArea.update(input);
    }

    @Override
    public void run() {
        RectF categoryBar = new RectF(
                0,
                0,
                screen_width / 10,
                screen_height);
        RectF blockBar = new RectF(
                screen_width / 10,
                0,
                screen_width / 10 + screen_width / 2.75f,
                screen_height);
        RectF runButton = new RectF(
                screen_width - screen_width / 10,
                screen_height / 2 - screen_height / 20,
                screen_width,
                screen_height / 2);
        RectF backToMenuButton = new RectF(
                screen_width - screen_width / 10,
                0,
                screen_width,
                screen_height / 20);
        RectF codingArea = new RectF(
                0,
                screen_height / 2,
                screen_width,
                screen_height);

        Vector2f temp = new Vector2f();

        Bitmap tempBMP = BitmapFactory.decodeResource(context.getResources(), R.drawable.condition_block);

        while(isRunning) {
            // make sure we got valid stuff
            while(myHolder == null || !myHolder.getSurface().isValid()) {
                try {
                    Thread.sleep(1);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //input.refresh();

            /*********************************** Start Update ********************************************/

            Update();
            input.refresh();
            //Log.d("RUN" , "Update");

            /**************************************** End Update *********************************************/

            Canvas canvas = myHolder.lockCanvas();
            if(canvas == null) {
                try {
                    Thread.sleep(1);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
            canvas.drawRGB(127, 127, 127);

            /**************************************** Start Draw *********************************************/

            // below is temp code
//            ArrayList<Touch> swipes = input.getSwipes();
//            if(swipes.size() > 0 && swipes.get(0).isInRect(codingArea.left, codingArea.top, codingArea.right - codingArea.left, codingArea.bottom - codingArea.top)) {
//                codingArea.top -= swipes.get(0).getSwipeDist().y;
//            }
//            if(swipes.size() > 0 && swipes.get(0).isInRect(blockBar.left, blockBar.top, blockBar.right - blockBar.left, blockBar.bottom - blockBar.top)) {
//                blockBar.right -= swipes.get(0).getSwipeDist().x;
//            }
//            if(input.isRectPressed(codingArea.left, codingArea.top, codingArea.right - codingArea.left, codingArea.bottom - codingArea.top)) {
//                bluePaint.setAlpha(50);
//            }

//            canvas.drawRect(codingArea, bluePaint);
//            canvas.drawRect(categoryBar, blackPaint);
//            redPaint.setAlpha(128);
//            canvas.drawRect(blockBar, redPaint);
//            redPaint.setAlpha(255);
//            canvas.drawRect(runButton, redPaint);
//            canvas.drawRect(backToMenuButton, redPaint);
//
//            canvas.drawBitmap(tempBMP, new Rect(0, 0, tempBMP.getWidth(), tempBMP.getHeight()), new Rect(0, 0, 700, 450), null);

            Draw(canvas);



            /**************************************** End Draw *********************************************/

            myHolder.unlockCanvasAndPost(canvas);

            try {
                Thread.sleep(5);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void pause() {

    }

    public void resume() {

    }
}
