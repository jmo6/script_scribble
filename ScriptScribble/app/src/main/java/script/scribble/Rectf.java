package script.scribble;

public class Rectf {
    public float x, y, width, height;

    public Rectf(float width, float height) {
        this.x = 0;
        this.y = 0;
        this.width = width;
        this.height = height;
    }

    public Rectf(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectf(Vector2f topLeft, float width, float height) {
        this.x = topLeft.x;
        this.y = topLeft.y;
        this.width = width;
        this.height = height;
    }

    public Rectf(Rectf rect) {
        this.x = rect.x;
        this.y = rect.y;
        this.width = rect.width;
        this.height = rect.height;
    }

    public boolean isInRect(float x, float y) {
        if(x >= this.x && x < this.x + width && y >= this.y
                && y < this.y + height) {
            return true;
        }
        return false;
    }

    public boolean isInRect(Vector2f v) {
        if(v.x >= x && v.x < x + width && v.y >= y && v.y < y + height) {
            return true;
        }
        return false;
    }

    public static boolean isInRect(float x, float y, Rectf rect) {
        if(x >= rect.x && x < rect.x + rect.width && y >= rect.y
                && y < rect.y + rect.height) {
            return true;
        }
        return false;
    }

    public static boolean isInRect(Vector2f v, Rectf rect) {
        if(v.x >= rect.x && v.x < rect.x + rect.width && v.y >= rect.y
                && v.y < rect.y + rect.height) {
            return true;
        }
        return false;
    }

    public boolean collidingWithRect(Rectf rect) {
        if(this.x + this.width > rect.x && this.x < rect.x + rect.width
                && this.y + this.height > rect.y
                && this.y < rect.y + rect.height) {
            return true;
        }
        return false;
    }
}
