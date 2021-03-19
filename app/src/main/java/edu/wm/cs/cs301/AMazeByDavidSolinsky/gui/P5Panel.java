package edu.wm.cs.cs301.AMazeByDavidSolinsky.gui;

/**
 * Provides an adapter for UI object for the first person view
 * and the map view to draw on.
 * The class encapsulates its dependency on AWT.
 * To its clients it offers the notion of an editor:
 * one can clear it, put graphical entities to draw into it,
 * and commit the complete drawing to the UI.
 * The naming is technical as it is for Project 5
 * and should serve as a MazePanel.
 *
 * @author Peter Kemper
 *
 */
public interface P5Panel {
    /**
     * Clears all content from the editor.
     */
    public void clear();

    /**
     * Commits all accumulated drawings to the UI.
     */
    public void commit();

    /**
     * Tells if instance is able to draw. This ability depends on the
     * context, for instance, in a testing environment, drawing
     * may be not possible and not desired.
     * @return true if drawing is possible, false if not.
     */
    public boolean isOperational();

    /**
     * Sets the color for future drawing requests. The color setting
     * will remain in effect till this method is called again and
     * with a different color.
     * @param rgb gives the red green and blue encoded value of the color
     */
    public void setColor(int rgb);

    /**
     * Returns the RGB value for the current color setting.
     * @return integer RGB value
     */
    public int getColor();

    /**
     * Adds a filled rectangle.
     * The rectangle is specified with the {@code (x,y)} coordinates
     * of the upper left corner and then its width for the
     * x-axis and the height for the y-axis.
     * @param x is the x-coordinate of the top left corner
     * @param y is the y-coordinate of the top left corner
     * @param width is the width of the rectangle
     * @param height is the height of the rectangle
     */
    public void addFilledRectangle(int x, int y, int width, int height);

    /**
     * Adds a filled polygon.
     * The polygon is specified with {@code (x,y)} coordinates
     * for the n points it consists of. All x-coordinates
     * are given in a single array, all y-coordinates are
     * given in a separate array. Both arrays must have
     * same length n. The order of points in the arrays
     * matter as lines will be drawn from one point to the next
     * as given by the order in the array.
     * @param xPoints are the x-coordinates of points for the polygon
     * @param yPoints are the y-coordinates of points for the polygon
     * @param nPoints is the number of points, the length of the arrays
     */
    public void addFilledPolygon(int[] xPoints, int[] yPoints, int nPoints);
    /**
     * Adds a polygon.
     * The polygon is not filled.
     * The polygon is specified with {@code (x,y)} coordinates
     * for the n points it consists of. All x-coordinates
     * are given in a single array, all y-coordinates are
     * given in a separate array. Both arrays must have
     * same length n. The order of points in the arrays
     * matter as lines will be drawn from one point to the next
     * as given by the order in the array.
     * @param xPoints are the x-coordinates of points for the polygon
     * @param yPoints are the y-coordinates of points for the polygon
     * @param nPoints is the number of points, the length of the arrays
     */
    public void addPolygon(int[] xPoints, int[] yPoints, int nPoints);

    /**
     * Adds a line.
     * A line is described by {@code (x,y)} coordinates for its
     * starting point and its end point.
     * @param startX is the x-coordinate of the starting point
     * @param startY is the y-coordinate of the starting point
     * @param endX is the x-coordinate of the end point
     * @param endY is the y-coordinate of the end point
     */
    public void addLine(int startX, int startY, int endX, int endY);

    /**
     * Adds a filled oval.
     * The oval is specified with the {@code (x,y)} coordinates
     * of the upper left corner and then its width for the
     * x-axis and the height for the y-axis. An oval is
     * described like a rectangle.
     * @param x is the x-coordinate of the top left corner
     * @param y is the y-coordinate of the top left corner
     * @param width is the width of the oval
     * @param height is the height of the oval
     */
    public void addFilledOval(int x, int y, int width, int height);
    /**
     * Adds the outline of a circular or elliptical arc covering the specified rectangle.
     * The resulting arc begins at startAngle and extends for arcAngle degrees,
     * using the current color. Angles are interpreted such that 0 degrees
     * is at the 3 o'clock position. A positive value indicates a counter-clockwise
     * rotation while a negative value indicates a clockwise rotation.
     * The center of the arc is the center of the rectangle whose origin is
     * (x, y) and whose size is specified by the width and height arguments.
     * The resulting arc covers an area width + 1 pixels wide
     * by height + 1 pixels tall.
     * The angles are specified relative to the non-square extents of
     * the bounding rectangle such that 45 degrees always falls on the
     * line from the center of the ellipse to the upper right corner of
     * the bounding rectangle. As a result, if the bounding rectangle is
     * noticeably longer in one axis than the other, the angles to the start
     * and end of the arc segment will be skewed farther along the longer
     * axis of the bounds.
     *
     * @param x the x coordinate of the upper-left corner of the arc to be drawn.
     * @param y the y coordinate of the upper-left corner of the arc to be drawn.
     * @param width the width of the arc to be drawn.
     * @param height the height of the arc to be drawn.
     * @param startAngle the beginning angle.
     * @param arcAngle the angular extent of the arc, relative to the start angle.
     */
    public void addArc(int x, int y, int width, int height, int startAngle, int arcAngle) ;
    /**
     * Adds a string at the given position
     * @param x the x coordinate
     * @param y the y coordinate
     * @param str the string
     */
    public void addMarker(float x, float y, String str) ;
}
