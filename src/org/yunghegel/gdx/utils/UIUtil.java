package org.yunghegel.gdx.utils;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.kotcrab.vis.ui.widget.VisTable;

public class UIUtil {

    public static VisTable wrapActorsInRow(float pad, float space, VisTable... tables){
        VisTable table = new VisTable();
        table.pad(pad);
        table.defaults().space(space);
        for (VisTable t : tables) {
            table.add(t);
        }

        return table;
    }

    public static VisTable wrapActorsInColumn(float pad, float space, VisTable... tables){
        VisTable table = new VisTable();
        table.pad(pad);
        table.defaults().space(space);
        for (VisTable t : tables) {
            table.row();
            table.add(t);
        }
        return table;
    }

    public static Rectangle getTextBounds(BitmapFont font, String text){
        Rectangle bounds = new Rectangle();
        BitmapFont.BitmapFontData data = font.getData();
        GlyphLayout layout = new GlyphLayout(font, text);
        bounds.width = data.getGlyph(' ').width;
        bounds.height = data.capHeight;
        bounds.width *= text.length();


        return bounds;
    }

    public static boolean isInsideRectangle(float x, float y, Rectangle rectangle){
        return x >= rectangle.x && x <= rectangle.x + rectangle.width && y >= rectangle.y && y <= rectangle.y + rectangle.height;
    }

    public static boolean isInsideRectangle(float x, float y, float width, float height, float originX,float originY){
        return x >= originX && x <= originX + width && y >= originY && y <= originY + height;
    }

}
