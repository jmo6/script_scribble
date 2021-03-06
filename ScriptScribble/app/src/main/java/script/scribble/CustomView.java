package script.scribble;

import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.content.Context;
import android.view.Display;
import android.graphics.Point;
import android.view.WindowManager;
import android.graphics.Canvas;

import script.scribble.util.ImageHandler;
import script.scribble.util.Input;

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

    Update()
        handle movement of blocks from menu to coding area
            when you start a drag on a block, it will set curDraggedBlock to a new block of the selected type
        handle changing of block categories
        handle movement of the block menu itself
    Draw()
        draw blocks in menu and being moved to coding area
 */

public class CustomView extends SurfaceView implements Runnable {
    Context context;
    Input input;
    public Thread myThread;
    SurfaceHolder myHolder;
    public static int screen_width, screen_height;
    public static boolean isRunning;
    BlockMenu blockMenu;
    CodingArea codingArea;
    OutputWindow outputWindow;
    AppCompatActivity parentActivity;

    public CustomView(Context context, Input input, AppCompatActivity parentActivity) {
        super(context);
        this.parentActivity = parentActivity;
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

        ImageHandler.loadImages(this.context);
        codingArea = new CodingArea();
        blockMenu = new BlockMenu(codingArea);
        outputWindow = new OutputWindow(codingArea);
        if(CodingArea.Test()) {
            System.out.println("tests passed");
        } else {
            System.out.println("tests not passed");
        }
        this.myThread.start();
    }

    public void Draw(Canvas canvas) {
        codingArea.Draw(canvas);
        outputWindow.Draw(canvas);
        codingArea.DrawOverOutput(canvas);
        blockMenu.Draw(canvas);
    }

    public void Update() {
        blockMenu.Update(input);
        codingArea.Update(input, blockMenu.blockBar.right);
    }

    @Override
    public void run() {
        while(isRunning) {
            // make sure we got valid stuff
            while(myHolder == null || !myHolder.getSurface().isValid()) {
                try {
                    Thread.sleep(1);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }

            /*********************************** Start Update ********************************************/

            Update();
            input.Refresh();

            /**************************************** End Update *********************************************/

            Canvas canvas = myHolder.lockCanvas();
            if(canvas == null) {
                return;
            }
//            canvas.drawRGB(127, 127, 127);

            /**************************************** Start Draw *********************************************/

            Draw(canvas);

            /**************************************** End Draw *********************************************/

            myHolder.unlockCanvasAndPost(canvas);

            try {
                Thread.sleep(5);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
        parentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                parentActivity.setContentView(R.layout.activity_main_menu);
            }
        });
    }
}
