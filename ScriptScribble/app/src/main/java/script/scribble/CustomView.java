package script.scribble;

import android.graphics.Paint;
import android.graphics.RectF;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.content.Context;
import android.view.Display;
import android.graphics.Point;
import android.view.WindowManager;
import android.graphics.Canvas;

import java.util.ArrayList;

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

public class CustomView extends SurfaceView implements Runnable {
    Context context;
    Input input;
    Thread myThread;
    SurfaceHolder myHolder;
    int screen_width, screen_height;
    boolean isRunning;

    // declare variables here
    Paint blackPaint, redPaint, greenPaint, bluePaint;



    public void pause() {

    }

    public void resume() {

    }


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
        blackPaint = new Paint();
        blackPaint.setARGB(255, 0, 0, 0);

        redPaint = new Paint();
        redPaint.setARGB(255, 255, 0, 0);

        greenPaint = new Paint();
        greenPaint.setARGB(255, 0, 255, 0);

        bluePaint = new Paint();
        bluePaint.setARGB(255, 0, 0, 255);

        this.myThread.start();
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

        while(isRunning) {
            // make sure we got valid stuff
            while(myHolder == null || !myHolder.getSurface().isValid()) {
                try {
                    Thread.sleep(1);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Canvas canvas = myHolder.lockCanvas();
            canvas.drawRGB(127, 127, 127);

            /**************************************** Start Draw *********************************************/

            ArrayList<Touch> swipes = input.getSwipes();
            if(swipes.size() > 0 && swipes.get(0).startedInRect(codingArea.left, codingArea.top, codingArea.right - codingArea.left, codingArea.bottom - codingArea.top)) {
                codingArea.top -= swipes.get(0).getSwipeDist().y;
            }
//            if(input.isRectPressed(codingArea.left, codingArea.top, codingArea.right - codingArea.left, codingArea.bottom - codingArea.top)) {
//                bluePaint.setAlpha(50);
//            }

            canvas.drawRect(codingArea, bluePaint);
            canvas.drawRect(categoryBar, blackPaint);
            redPaint.setAlpha(128);
            canvas.drawRect(blockBar, redPaint);
            redPaint.setAlpha(255);
            canvas.drawRect(runButton, redPaint);
            canvas.drawRect(backToMenuButton, redPaint);

            /**************************************** End Draw *********************************************/

            myHolder.unlockCanvasAndPost(canvas);

            input.refresh();

            /*********************************** Start Update ********************************************/



            /**************************************** End Update *********************************************/

            try {
                Thread.sleep(5);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
