package script.scribble.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import script.scribble.CustomView;
import script.scribble.R;
import script.scribble.blocks.Block;

public class ImageHandler {
    public static final int SNEK = Block.NUM_BLOCKS;
    public static final int ROCK = Block.NUM_BLOCKS + 1;
    public static final int FLAG = Block.NUM_BLOCKS + 2;
    public static Bitmap[] images;

    public static void loadImages(Context context) {
        final int numExtraImages = 3;
        images = new Bitmap[Block.NUM_BLOCKS + numExtraImages];
        // here we load all our images we want and fill the images array

        images[Block.MOVE_BLOCK] = BitmapFactory.decodeResource(context.getResources(), R.drawable.move_right);
        images[Block.ROTATE_BLOCK] = BitmapFactory.decodeResource(context.getResources(), R.drawable.rotate);
        images[Block.IF_BLOCK] = BitmapFactory.decodeResource(context.getResources(), R.drawable.if_block);
        // TODO: get image for else block
        images[Block.ELSE_BLOCK] = BitmapFactory.decodeResource(context.getResources(), R.drawable.not_block);
        images[Block.WHILE_BLOCK] = BitmapFactory.decodeResource(context.getResources(), R.drawable.while_block);
        images[Block.IS_RIGHT_SPACE_OPEN_BLOCK] = BitmapFactory.decodeResource(context.getResources(), R.drawable.right_space_open);
        images[Block.IS_LEFT_SPACE_OPEN_BLOCK] = BitmapFactory.decodeResource(context.getResources(), R.drawable.left_space_open);
        images[Block.IS_UP_SPACE_OPEN_BLOCK] = BitmapFactory.decodeResource(context.getResources(), R.drawable.up_space_open);
        images[Block.IS_DOWN_SPACE_OPEN_BLOCK] = BitmapFactory.decodeResource(context.getResources(), R.drawable.down_space_open);
        images[Block.AND_BLOCK] = BitmapFactory.decodeResource(context.getResources(), R.drawable.and_block);
        images[Block.OR_BLOCK] = BitmapFactory.decodeResource(context.getResources(), R.drawable.or_block);
        images[Block.NOT_BLOCK] = BitmapFactory.decodeResource(context.getResources(), R.drawable.not_block);
        images[Block.RUN_PRESSED_BLOCK] = BitmapFactory.decodeResource(context.getResources(), R.drawable.condition_block);
        images[Block.BUTTON_PRESSED_BLOCK] = BitmapFactory.decodeResource(context.getResources(), R.drawable.condition_block);

        images[SNEK] = BitmapFactory.decodeResource(context.getResources(), R.drawable.snek);
        images[ROCK] = BitmapFactory.decodeResource(context.getResources(), R.drawable.rock_chan);
        images[FLAG] = BitmapFactory.decodeResource(context.getResources(), R.drawable.flagpole);
    }
}
