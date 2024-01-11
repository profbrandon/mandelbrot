import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

class Viewer extends JFrame {

    private int width = 601;
    private int height = 601;
    private Color[][] buffer;

    private JPanel panel = new JPanel() {

        @Override
        public void paint (Graphics g) {
            super.paint (g);

            for (int i = 0; i < width; ++i) {
                for (int j = 0; j < height; ++j) {
                    g.setColor (buffer[j][i]);
                    g.drawLine (width - i, j, width - i, j);
                }
            }
        }
    };

    public Viewer (double x, double y, double mag, int iterations) {
        super ("Mandelbrot Viewer - (x: " + x + ", y: " + y + ", mag: " + mag + ")");

        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        setVisible (true);

        add (panel);

        panel.setBackground (Color.BLACK);
        panel.setPreferredSize(new Dimension (width, height));
    
        pack ();

        buffer = Mandelbrot.compute (width, height, new Mandelbrot.Point (x, y), mag, iterations);

        repaint ();
    }

    public static void main (String args[]) {
        double x, y, mag;
        int iterations;

        if (args.length == 4) {
            x = Double.parseDouble (args[0]);
            y = Double.parseDouble (args[1]);
            mag = Double.parseDouble (args[2]);
            iterations = Integer.parseInt (args[3]);
        }
        else {
            x = 0;
            y = 0;
            mag = 1;
            iterations = 1000;
        }

        SwingUtilities.invokeLater (() -> {
            Viewer v = new Viewer (x, y , mag, iterations);
        });
    }
}