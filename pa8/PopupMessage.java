package pa8;

import java.awt.Color;
import java.util.ArrayList;
import objectdraw.*;
 
/**
 * Creates a popup message with configurable text on a canvas.
 */
public class PopupMessage {
 
  private static final double HALF_DIVISOR = 2.0;
 
  private static final int WIDTH = 250;
  private static final int HEIGHT = 84;
  private static final int HALF_WIDTH = WIDTH / 2;
  private static final int HALF_HEIGHT = HEIGHT / 2;
 
  private static final int ARC_SIZE = 30;
 
 
  private Location centerPoint;
  private FilledRoundedRect popupBox;
  private ArrayList<Text> messageText;
 
 
  /**
   * Create a new popup message by setting up the popup box centered on the 
   * point passed in that text can later be displayed on.  The popup box is
   * initially hidden.
   *
   * @param center The location to center the popup message on
   * @param color The color of the popup box
   * @param canvas The canvas to display the popup message on
   */
  public PopupMessage(Location center, Color color, DrawingCanvas canvas) {
    centerPoint = new Location(center);
 
    // Calculate the position of the popup box
    Location upperLeft = new Location(center);
    upperLeft.translate(-HALF_WIDTH, -HALF_HEIGHT);
 
    // Create the popup box
    popupBox = new FilledRoundedRect(
        upperLeft, WIDTH, HEIGHT, ARC_SIZE, ARC_SIZE, canvas);
    popupBox.setColor(color);
    popupBox.hide();
 
    messageText = new ArrayList<Text>();
  }
 
  /**
   * Display a popup message with the text passed in.  The text will be
   * centered in the popup box.
   *
   * @param text The text to display in the popup message
   */
  public void display(Text text) {
    // Display the text centered in the popup box by passing 0 for the offsets
    display(text, 0, 0);
  }
 
  /**
   * Display a popup message with the text passed in.  The text will be
   * centered in the popup box and then offset by the offset amounts passed in.
   *
   * @param text The text to display in the popup message
   * @param xOffset The amount to offset the text in the x direction
   * @param yOffset The amount to offset the text in the y direction
   */
  public void display(Text text, int xOffset, int yOffset) {
    // Center the text over the popup box
    double halfTextWidth = text.getWidth() / HALF_DIVISOR;
    double halfTextHeight = text.getHeight() / HALF_DIVISOR;
    text.moveTo(centerPoint);
    text.move(-halfTextWidth, -halfTextHeight);
    // Offset the text by the amounts passed in
    text.move(xOffset, yOffset);
 
    // If this is the first text object being added, show the popup box first
    if (messageText.isEmpty()) {
      popupBox.sendToFront();
      popupBox.show();
    }
 
    // Display the popup message
    text.sendToFront();
    text.show();
 
    // Save a reference to the text we are displaying so we can hide it later
    messageText.add(text);
  }
 
  /**
   * Hide the popup message currently being displayed.
   */
  public void hide() {
    if (!messageText.isEmpty()) {
      // Hide all the text objects being displayed
      for (Text text : messageText) {
        text.hide();
      }
 
      messageText.clear();
      popupBox.hide();
    }
  }
}
