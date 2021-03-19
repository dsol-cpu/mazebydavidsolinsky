package edu.wm.cs.cs301.AMazeByDavidSolinsky.gui;


import edu.wm.cs.cs301.AMazeByDavidSolinsky.generation.CardinalDirection;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
//import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.Font;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.RenderingHints;
//import java.awt.font.GlyphVector;
//import java.awt.geom.Rectangle2D;
//
//import javax.swing.JComponent;
//
//import generation.CardinalDirection;

/**
 * A component that draws a compass rose.  This class has no other functionality, but superclasses
 * may add functionality to it.
 *
 * @author Sampo Niskanen <sampo.niskanen@iki.fi>
 * Code copied from http://www.soupwizard.com/openrocket/code-coverage/eclemma-20121216/OpenRocket/src/net.sf.openrocket.gui.components.compass/CompassRose.java.html
 * adjusted for Maze setting by
 * @author Peter Kemper
 */
public class CompassRose {
	private static final int greenWM = Color.parseColor("#115740");
	private static final int goldWM = Color.parseColor("#916f41");


    private static final int MAIN_COLOR = greenWM; //new Color(0.4f, 0.4f, 1.0f);
    private static final float MAIN_LENGTH = 0.95f;
    private static final float MAIN_WIDTH = 0.15f;

    private static final int CIRCLE_BORDER = 2;
    private static final int CIRCLE_HIGHLIGHT = Color.argb(1.0f, 1.0f, 1.0f, 0.8f);
    private static final int CIRCLE_SHADE = Color.argb(1.0f, 1.0f, 1.0f, 0.3f); //new Color(0.0f, 0.0f, 0.0f, 0.2f);

    private static final int MARKER_COLOR = Color.BLACK; //Color.WHITE; //Color.BLACK;


    private double scaler;

    private double markerRadius;
    private Typeface typeface;
//    private Font markerFont;

    // (x,y) coordinates of center point on overall area
    private int centerX; // x coordinate of center point
    private int centerY; // y coordinate of center point
    private int size; // size of compass rose
    private CardinalDirection currentDir;


    /**
     * Construct a compass rose with the default settings.
     *
     */
    public CompassRose() {
        this(0.9, 1.7);
    }


    /**
     * Construct a compass rose with the specified settings.
     *
     * @param scaler        The scaler of the rose.  The bordering circle will be this portion of the component dimensions.
     * @param markerRadius  The radius for the marker positions (N/E/S/W), or NaN for no markers.  A value greater than one
     *                      will position the markers outside of the bordering circle.
     */
    public CompassRose(double scaler, double markerRadius) {
        this.scaler = scaler;
        this.markerRadius = markerRadius;
    }

    public void setPositionAndSize(int x, int y, int size) {
    	centerX = x;
    	centerY = y;
    	this.size = size;
    }

    /**
     * Set the current direction such that it can
     * be highlighted on the display
     * @param cd
     */
    public void setCurrentDirection(CardinalDirection cd) {
    	currentDir = cd;
    }

    public void paintComponent(MazePanel g) {


        /* Original code
        Dimension dimension = this.getSize();
        int width = Math.min(dimension.width, dimension.height);
        int mid = width / 2;
        width = (int) (scaler * width);
        */
        int width = (int) (scaler * size);
        final int mainLength = (int) (width * MAIN_LENGTH / 2);
        final int mainWidth = (int) (width * MAIN_WIDTH / 2);

//        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // draw background disc
        drawBackground(g, width);
        // draw main part in all 4 directions in same color
        // x, y arrays used for drawing polygons
        // starting point is always (centerX, centerY)
        g.setColor(MAIN_COLOR);
        final int[] x = new int[3];
        final int[] y = new int[3];
        x[0] = centerX;
        y[0] = centerY;
        drawMainNorth(g, mainLength, mainWidth, x, y);
        drawMainEast(g, mainLength, mainWidth, x, y);
        drawMainSouth(g, mainLength, mainWidth, x, y);
        drawMainWest(g, mainLength, mainWidth, x, y);

        drawBorderCircle(g, width);

        drawDirectionMarker(g, width);
    }


	private void drawBackground(final MazePanel g2, int width) {
		g2.setColor(Color.WHITE);
		final int x = centerX - size;
		final int y = centerY - size;
		final int w = 2 * size;// - 2 * CIRCLE_BORDER;
        g2.addFilledOval(x,y,w,w);
	}


	private void drawMainWest(MazePanel g2, int length, int width, int[] x, int[] y) {
		x[1] = centerX - length;
        y[1] = centerY;
        x[2] = centerX - width;
        y[2] = centerY + width;
        g2.addFilledPolygon(x, y, 3);

        y[2] = centerY - width;
        g2.addPolygon(x, y, 3);
	}
	private void drawMainEast(MazePanel g2, int length, int width, int[] x, int[] y) {
		// observation: the 2 triangles to the right are drawn the same
		// way as for the left if one inverts the sign for length and width
		// i.e. exchanges addition and subtraction
		drawMainWest(g2, -length, -width, x, y);
	}
	private void drawMainSouth(MazePanel g2, int length, int width, int[] x, int[] y) {
		x[1] = centerX;
        y[1] = centerY + length;
        x[2] = centerX + width;
        y[2] = centerY + width;
        g2.addFilledPolygon(x, y, 3);

        x[2] = centerX - width;
        g2.addFilledPolygon(x, y, 3);
	}
	private void drawMainNorth(MazePanel g2, int length, int width, int[] x, int[] y) {
		// observation: the 2 triangles to the top are drawn the same
		// way as for the bottom if one inverts the sign for length and width
		// i.e. exchanges addition and subtraction
		drawMainSouth(g2, -length, -width, x, y);
	}

	private void drawBorderCircle(MazePanel g2, int width) {
		final int x = centerX - width / 2 + CIRCLE_BORDER;
		final int y = centerY - width / 2 + CIRCLE_BORDER;
		final int w = width - 2 * CIRCLE_BORDER;
		g2.setColor(CIRCLE_SHADE);
        g2.addArc(x, y, w, w, 45, 180);
        g2.setColor(CIRCLE_HIGHLIGHT);
        g2.addArc(x, y, w, w, 180 + 45, 180);
	}


	private void drawDirectionMarker(MazePanel g2, int width) {
		if (!Double.isNaN(markerRadius) && typeface != null) {

            int pos = (int) (width * markerRadius / 2);

            g2.setColor(MARKER_COLOR);
            /* original code
            drawMarker(g2, mid, mid - pos, trans.get("lbl.north"));
            drawMarker(g2, mid + pos, mid, trans.get("lbl.east"));
            drawMarker(g2, mid, mid + pos, trans.get("lbl.south"));
            drawMarker(g2, mid - pos, mid, trans.get("lbl.west"));
            */
            /* version with color highlighting but stable orientation
             * Highlighting with MarkerColor which is white
             * use gold as color for others */
            // WARNING: north south confusion
            // currendDir South is going upward on the map
            if (CardinalDirection.South == currentDir)
            	g2.setColor(MARKER_COLOR);
            else
            	g2.setColor(goldWM);
            drawMarker(g2, centerX, centerY - pos, "N");
            if (CardinalDirection.East == currentDir)
            	g2.setColor(MARKER_COLOR);
            else
            	g2.setColor(goldWM);
            drawMarker(g2, centerX + pos, centerY, "E");
            // WARNING: north south confusion
            // currendDir North is going downwards on the map
            if (CardinalDirection.North == currentDir)
            	g2.setColor(MARKER_COLOR);
            else
            	g2.setColor(goldWM);
            drawMarker(g2, centerX, centerY + pos, "S");
            if (CardinalDirection.West == currentDir)
            	g2.setColor(MARKER_COLOR);
            else
            	g2.setColor(goldWM);
            drawMarker(g2, centerX - pos, centerY, "W");
        }
	}

    private void drawMarker(MazePanel g2, float x, float y, String str) {
//        GlyphVector gv = markerFont.createGlyphVector(g2.getFontRenderContext(), str);
//        Rect rect = gv.getVisualBounds();
//
//        x -= rect.getWidth() / 2;
//        y += rect.getHeight() / 2;
//
//        g2.drawGlyphVector(gv, x, y);
        g2.addMarker(x,y,str);
    }

    public double getScaler() {
        return scaler;
    }

    public void setScaler(double scaler) {
        this.scaler = scaler;
    }

    public double getMarkerRadius() {
        return markerRadius;
    }

    public void setMarkerRadius(double markerRadius) {
        this.markerRadius = markerRadius;
    }

    public Typeface getTypeface() {
        return typeface;
    }


    public void setMarkerFont(Typeface typeface) {
        this.typeface = typeface;
    }

//    @Override
//    public Dimension getPreferredSize() {
//        Dimension dim = super.getPreferredSize();
//        int min = Math.min(dim.width, dim.height);
//        dim.setSize(min, min);
//        return dim;
//    }

}
