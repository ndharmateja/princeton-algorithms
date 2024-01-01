import java.util.Arrays;

public class BarChartRacer {
    public static void main(String[] args) {
        // Play bgm
        StdAudio.playInBackground("../data/soundtrackC.wav");

        // Read command line args (default values for fallback)
        String filename = args.length >= 2 ? args[0] : "../data/cities.txt";
        int numBars = args.length >= 2 ? Integer.parseInt(args[1]) : 10;
        In in = new In(filename);

        // Read title, xAxis label and source from the file
        String title = in.readLine();
        String xAxis = in.readLine();
        String source = in.readLine();

        // Create chart and canvas with the values
        BarChart chart = new BarChart(title, xAxis, source);
        StdDraw.setCanvasSize(1000, 700);
        StdDraw.enableDoubleBuffering();

        // Read all the lines in the file
        while (!in.isEmpty()) {
            // Read empty line and number of data points available
            in.readLine();
            int numDataPoints = Integer.parseInt(in.readLine());

            // Read 'numDataPoints' number of bars and
            // add them to the array
            Bar[] bars = new Bar[numDataPoints];
            String date = "";
            for (int i = 0; i < bars.length; i++) {
                String line = in.readLine();
                String[] parts = line.split(",");

                // Get date for all the following points
                // in iteration 0
                if (i == 0)
                    date = parts[0];

                String name = parts[1];
                int value = Integer.parseInt(parts[3]);
                String category = parts[4];
                bars[i] = new Bar(name, value, category);
            }

            // Clear chart and the canvas
            chart.reset();
            StdDraw.clear();
            chart.setCaption(date);

            // Sort and add last 'numBars' bars to the chart
            // we take the minimum of numBars and bars.length
            // to cover the case where bars.length < numBars
            Arrays.sort(bars);
            int numBarsToAdd = Math.min(numBars, bars.length);
            int numBarsAdded = 0;
            for (int i = 0; i < numBarsToAdd; i++) {
                Bar bar = bars[bars.length - i - 1];
                if (bar.getValue() > 0) {
                    numBarsAdded++;
                    chart.add(bar.getName(), bar.getValue(), bar.getCategory());
                }
            }

            // If there is atleast one bar in the chart
            // draw the chart and pause
            if (numBarsAdded > 0) {
                chart.draw();
                StdDraw.show();
                StdDraw.pause(100);
            }
        }

        // Stop the bgm
        StdAudio.stopInBackground();
    }
}
