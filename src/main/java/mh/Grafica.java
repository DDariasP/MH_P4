package mh;

import mh.tipos.*;
import java.awt.*;
import javax.swing.JFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Grafica extends JFrame {

    public final Lista<Integer>[] d;
    public int minX, maxX, minY, maxY;

    public Grafica(Lista<Integer>[] datos, int t) {
        d = datos;
        minX = Integer.MAX_VALUE;
        maxX = Integer.MIN_VALUE;
        minY = Integer.MAX_VALUE;
        maxY = Integer.MIN_VALUE;

        //crear la grafica
        XYPlot plot = new XYPlot();

        for (int i = 0; i < datos.length; i++) {
            //crear funcion
            XYDataset funcion = createDataset(i, P4.P[t] + "-S" + P4.SEED[i]);
            //caracteristicas de funcion
            XYItemRenderer renderer = new XYLineAndShapeRenderer(true, true);
            renderer.setSeriesStroke(0, new BasicStroke(2.0f));
            //añadir funcion a la grafica
            plot.setDataset(i, funcion);
            plot.setRenderer(i, renderer);
        }

        //crear y añadir los ejes
        ValueAxis domain = new NumberAxis("Iteración (1 : " + P4.RATIO[t] + ")");
        domain.setRange(minX - 1, maxX + 1);
        ValueAxis range = new NumberAxis("Coste");
        int diffY = Math.abs((maxY - minY) / 10);
        range.setRange(minY - diffY, maxY - diffY);
        plot.setDomainAxis(0, domain);
        plot.setRangeAxis(0, range);

        //crear el area de trazado
        JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, plot, true);
        plot.setBackgroundPaint(Color.DARK_GRAY);

        //crear la ventana 
        ChartPanel panel = new ChartPanel(chart);
        panel.setDomainZoomable(true);
        panel.setRangeZoomable(true);
        setContentPane(panel);
    }

    private XYDataset createDataset(int n, String nombre) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series = new XYSeries(nombre);
        for (int i = 0; i < d[n].size(); i++) {
            series.add(i, d[n].get(i));
        }
        int min = (int) series.getMinY();
        if (minY > min) {
            minY = min;
        }
        int max = (int) series.getMaxY();
        if (maxY < max) {
            maxY = max;
        }
        min = (int) series.getMinX();
        if (minX > min) {
            minX = min;
        }
        max = (int) series.getMaxX();
        if (maxX < max) {
            maxX = max;
        }
        dataset.addSeries(series);
        return dataset;
    }
}
