import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

class ImageGen {

    private static void generate (Mandelbrot.Point center, double mag, String path, int unit, int widthU, int heightU) {
        
        int totalWidth = unit * widthU;
        int totalHeight = unit * heightU;

        BufferedImage image = new BufferedImage (totalWidth, totalHeight, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D graphics = image.createGraphics ();

        for (int i = 0; i < widthU; ++i) {
            for (int j = 0; j < heightU; ++j) {

                System.out.println ("Creating unit at (" + i + ", " + j + ")");

                double totalMag = mag * Math.min (widthU, heightU);

                double dx = 4 * (widthU / 2 - i) / totalMag;
                double dy = 4 * (heightU / 2 - j) / totalMag;

                Mandelbrot.Point unitCenter = new Mandelbrot.Point (center.x () + dx, center.y () + dy);

                Color[][] temp = Mandelbrot.compute (unit, unit, unitCenter, totalMag, (int) (totalMag * 10));

                for (int c = 0; c < unit; ++c)
                    for (int r = 0; r < unit; ++r) {
                        graphics.setColor (temp[r][c]);
                        graphics.drawLine (totalWidth - (i * unit + c), j * unit + r, totalWidth - (i * unit + c), j * unit + r);
                    }
            }
        }


        // Writing the image

        graphics.dispose ();

        try {
            File file = new File (path + "img.png");
            ImageIO.write (image, "png", file);
        }
        catch (IOException e) {
            System.out.println ("Failed to export terrain as image.");
        }
    }

    public static void main (String args[]) {
        if (args.length == 1 && args[0].equals ("help")) {
            System.out.println ("args: centerX centerY mag path unitSize widthUnits heightUnits");
            return;
        }
        else if (args.length != 7) {
            System.out.println ("Incorrect arguments");
            return;
        }

        Mandelbrot.Point center = new Mandelbrot.Point (Double.parseDouble (args[0]), Double.parseDouble (args[1]));

        double mag = Double.parseDouble (args[2]);

        String path = args[3];

        int unit = Integer.parseInt (args[4]);
        int width = Integer.parseInt (args[5]);
        int height = Integer.parseInt (args[6]);

        generate (center, mag, path, unit, width, height);
    }
}