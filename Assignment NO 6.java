import javax.swing.*;
import java.awt.*;

public class PatternDrawing extends JPanel {

    // ---------- DDA Algorithm ----------
    private void drawLineDDA(Graphics g, int x0, int y0, int x1, int y1, String style) {
        int dx = x1 - x0;
        int dy = y1 - y0;

        int steps = Math.max(Math.abs(dx), Math.abs(dy));
        float xInc = dx / (float) steps;
        float yInc = dy / (float) steps;

        float x = x0;
        float y = y0;

        for (int i = 0; i <= steps; i++) {
            if (style.equals("DOTTED")) {
                if (i % 2 == 0) g.fillRect(Math.round(x), Math.round(y), 1, 1);
            } else if (style.equals("THICK")) {
                g.fillRect(Math.round(x), Math.round(y), 3, 3);
            } else {
                g.fillRect(Math.round(x), Math.round(y), 1, 1);
            }
            x += xInc;
            y += yInc;
        }
    }

    // ---------- Bresenham Algorithm ----------
    private void drawLineBresenham(Graphics g, int x0, int y0, int x1, int y1, String style) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = (x0 < x1) ? 1 : -1;
        int sy = (y0 < y1) ? 1 : -1;
        int err = dx - dy;

        int count = 0;

        while (true) {
            if (style.equals("DASHED")) {
                if ((count / 5) % 2 == 0) g.fillRect(x0, y0, 1, 1);
            } else { // SOLID
                g.fillRect(x0, y0, 1, 1);
            }

            if (x0 == x1 && y0 == y1) break;
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x0 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }
            count++;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE);

        // Outer Rectangle (DDA - Dotted)
        g.setColor(Color.BLUE);
        drawLineDDA(g, 50, 50, 250, 50, "DOTTED");
        drawLineDDA(g, 250, 50, 250, 200, "DOTTED");
        drawLineDDA(g, 250, 200, 50, 200, "DOTTED");
        drawLineDDA(g, 50, 200, 50, 50, "DOTTED");

        // Inner Rectangle (DDA - Thick)
        g.setColor(Color.GREEN);
        drawLineDDA(g, 100, 100, 200, 100, "THICK");
        drawLineDDA(g, 200, 100, 200, 150, "THICK");
        drawLineDDA(g, 200, 150, 100, 150, "THICK");
        drawLineDDA(g, 100, 150, 100, 100, "THICK");

        // Diamond (Bresenham - mix styles)
        g.setColor(Color.RED);
        int midX = (50 + 250) / 2; // 150
        int midY = (50 + 200) / 2; // 125

        // Four diamond points: mid of each outer rectangle side
        int topMidX = midX;
        int topMidY = 50;

        int rightMidX = 250;
        int rightMidY = midY;

        int bottomMidX = midX;
        int bottomMidY = 200;

        int leftMidX = 50;
        int leftMidY = midY;

        // Draw diamond lines (dashed)
        drawLineBresenham(g, topMidX, topMidY, rightMidX, rightMidY, "DASHED");
        drawLineBresenham(g, rightMidX, rightMidY, bottomMidX, bottomMidY, "DASHED");
        drawLineBresenham(g, bottomMidX, bottomMidY, leftMidX, leftMidY, "DASHED");
        drawLineBresenham(g, leftMidX, leftMidY, topMidX, topMidY, "DASHED");
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pattern Drawing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 300);
        frame.add(new PatternDrawing());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}