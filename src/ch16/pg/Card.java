package ch16.pg;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Card extends JPanel {

  final static int scale = 34;            // card size factor
  final static int height = 3 * scale;    // 3x5
  final static int width = 5 * scale;
  final static int rollover = 12; // New cards go in difft. pos'ns
  final static int offset = scale;// How far apart to put new cards
  private static final long serialVersionUID = 1L;
  static int cards = 0;
  JTextField title;
  JTextArea body;
  JLabel costLabel;
  int cost;
  private int lastx, lasty;

  public Card() {
    this("(Untitled)", "");
  }

  public Card(final Card from) {
    this(from.title.getText(), from.body.getText());
  }

  private Card(final String titleText, final String bodyText) {
    super();
    setBorder(BorderFactory.createLineBorder(Color.blue, 6));
    cards++;

    setLayout(new BorderLayout());
    final JPanel top = new JPanel(new BorderLayout());

    title = new JTextField(titleText);
    title.setBackground(Color.pink);
    title.addFocusListener(new FocusAdapter() {
      public void focusGained(final FocusEvent e) {
        moveToFront();
      }
    });

    top.add(title, "Center");
    costLabel = new JLabel(" ? ");
    cost = 0;
    top.add(costLabel, "East");
    add(top, "North");

    body = new JTextArea(bodyText);
    body.setLineWrap(true);
    body.setWrapStyleWord(true);
    body.setBackground(Color.pink);
    body.addFocusListener(new FocusAdapter() {
      public void focusGained(final FocusEvent e) {
        moveToFront();
      }
    });

    add(body, "Center");

    setLocation(
        (cards % rollover) * offset,
        (cards % rollover) * offset);
    setSize(width, height);

    // Turn on mouse events so we can detect being dragged
    enableEvents(
        AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK);
  }

  private void moveToFront() {
    final Background background = (Background) this.getParent();
    background.moveToFront(this);
//        background.getContainerListeners()[0].componentRemoved(null);  // force selection to reset
  }

  public void processMouseEvent(final MouseEvent e) {
    if (e.getID() == MouseEvent.MOUSE_PRESSED) {
      lastx = e.getX();
      lasty = e.getY();
      moveToFront();
    } else {
      super.processMouseEvent(e);
    }
  }

  public void processMouseMotionEvent(final MouseEvent e) {
    if (e.getID() == MouseEvent.MOUSE_DRAGGED) {
      final Point here = getLocation();
      setLocation((int) (here.getX() + e.getX() - lastx),
          (int) (here.getY() + e.getY() - lasty));
    } else {
      super.processMouseMotionEvent(e);
    }
  }

  public void rotateCost() {
    final String label = costLabel.getText();
    if (label.equals(" ? ")) {
      costLabel.setText(" 1 ");
      cost = 1;
    } else if (label.equals(" 1 ")) {
      costLabel.setText(" 2 ");
      cost = 2;
    } else if (label.equals(" 2 ")) {
      costLabel.setText(" 3 ");
      cost = 3;
    } else if (label.equals(" 3 ")) {
      costLabel.setText(" >3 ");
      cost = 0;
    } else if (label.equals(" >3 ")) {
      costLabel.setText(" ? ");
      cost = 0;
    } else {  // shouldn't happen
      costLabel.setText(" ? ");
      cost = 0;
    }
  }

  public int cost() {
    return cost;
  }

  public String title() {
    return title.getText();
  }

  public boolean needsSplit() {
    return costLabel.getText().equals(" >3 ");
  }

  public boolean needsEstimate() {
    return costLabel.getText().equals(" ? ");
  }
}
