package ch16.pg;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JLayeredPane;

public class Background extends JLayeredPane {

  private static final long serialVersionUID = 1L;

  int lineDistance;

  public Background(final int width, final int height, final int lineDistance) {
    super();
    setPreferredSize(new Dimension(width, height));
    this.lineDistance = lineDistance;

    setOpaque(true);
    setBackground(Color.yellow);
    setForeground(Color.orange);
  }

  public void paintComponent(final Graphics g) {
    super.paintComponent(g);
    final int height = getHeight();
    for (int i = 0; i < this.getWidth(); i += lineDistance) {
      g.drawLine(i, 0, i, height);
    }
  }
}
