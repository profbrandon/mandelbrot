import java.awt.Color;

class Mandelbrot {

    private static int DEFUALT_ITERATIONS = 1000;

    public static class Point {
        private double x, y;

        public Point (double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double x () { return x; }

        public double y () { return y; }
    }

    public static Color[][] compute (int width, int height, Point center, double mag, int max_iterations) {
        System.out.println ("Computing the mandelbrot set at (" + center.x () + ", " + center.y () + ", " + mag + ")");

        // Initialize

        Color[][] buffer = new Color[height][width];

        max_iterations = Math.max (max_iterations, Mandelbrot.DEFUALT_ITERATIONS);

        // Compute

        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                Point z = new Point (0.0, 0.0);

                Point p = new Point ( 4 * (0.5 * width - i) / Math.min (width, height) / mag + center.x ()
                                    , 4 * (0.5 * height - j) / Math.min (width, height) / mag + center.y ());

                double k = (p.x () - 1/4) * (p.x () - 1/4) + p.y () * p.y ();

                if (p.x () <= Math.sqrt (k) - 2 * k + 1/4 || (p.x () + 1) * (p.x () + 1) + p.y () * p.y () <= 1/16)
                {
                    buffer[i][j] = Color.BLACK;
                    continue;
                }

                int count = 0;

                while (count < max_iterations && (z.x () * z.x () + z.y () * z.y ()) < 4) {
                    z = new Point ( z.x () * z.x () - z.y () * z.y () + p.x ()
                                  , 2 * z.x () * z.y () + p.y ());
                    ++count;
                }

                if (count == max_iterations)
                    buffer[j][i] = Color.BLACK;
                else {
                    int value = (int) (255 * Math.sqrt ((double) count / (double) max_iterations));

                    // Blue 
                    buffer[j][i] = new Color ((int) Math.max (2 * value - 255, 0), value, (int) Math.min (2 * value, 255));
                }
            }
        }

        System.out.println ("Computation Complete");

        return buffer;
    }
}