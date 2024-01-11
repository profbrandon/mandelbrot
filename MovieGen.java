import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


class MovieGen {

    private static void generate (Mandelbrot.Point start, Mandelbrot.Point end, int steps, double startMag, double endMag, String path, int width, int height) {

        Color[][] buffer;

        for (int step = 0; step <= steps; ++step) {

            System.out.println ("Image " + step + " of " + steps + ":");

            // Computing the image

            double t   = Math.pow ((double) step / (double) steps, 0.1);
            double n   = endMag - startMag;
            double mag = n / (n - t * n + t) + startMag;

            Mandelbrot.Point center = new Mandelbrot.Point ((1 - t) * start.x () + t * end.x (), (1 - t) * start.y () + t * end.y ());

            buffer = Mandelbrot.compute (width, height, center, mag, (int) (mag * 100));

            // Converting to image

            BufferedImage image = new BufferedImage (width, height, BufferedImage.TYPE_3BYTE_BGR);
            Graphics2D graphics = image.createGraphics ();

            for (int i = 0; i < width; ++i) {
                for (int j = 0; j < height; ++j) {
                    graphics.setColor (buffer[j][i]);
                    graphics.drawLine (width - i, j, width - i, j);
                }
            }

            // Writing the image

            graphics.dispose ();

            try {
                File file = new File (path + "img_" + step + ".png");
                ImageIO.write (image, "png", file);
            }
            catch (IOException e) {
                System.out.println ("Failed to write image.");
            }
        }

        System.out.println ("Movie complete!");
        System.out.print ("\007");
        System.out.flush ();
    }

    public static void main (String args[]) {
        if (args.length == 1 && args[0].equals ("help")) {
            System.out.println ("args: startX startY endX endY steps startMag endMag path width height");
            return;
        }
        else if (args.length != 10) {
            System.out.println ("Incorrect arguments");
            return;
        }

        Mandelbrot.Point start = new Mandelbrot.Point (Double.parseDouble (args[0]), Double.parseDouble (args[1]));
        Mandelbrot.Point end = new Mandelbrot.Point (Double.parseDouble (args[2]), Double.parseDouble (args[3]));

        int steps = Integer.parseInt (args[4]);

        double startMag = Double.parseDouble (args[5]);
        double endMag = Double.parseDouble (args[6]);

        String path = args[7];

        int width = Integer.parseInt (args[8]);
        int height = Integer.parseInt (args[9]);

        generate (start, end, steps, startMag, endMag, path, width, height);
    }
}