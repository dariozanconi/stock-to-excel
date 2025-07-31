package core;


import org.jfree.chart.ChartPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.util.Date;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class StockChart extends ApplicationFrame {

    /**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private Data data;
    private ChartPanel chartPanel;

    public StockChart(String title, Data data) {
        super(title);
        this.data = data;

        JFreeChart lineChart = ChartFactory.createTimeSeriesChart(
                null,
                "Dates",
                "Adj. Close",
                createDataset(),
                false,
                false,
                false
        );
        
        lineChart.setBackgroundPaint(Color.WHITE);

        XYPlot plot = lineChart.getXYPlot();
        plot.setOutlineVisible(false);             
        plot.setBackgroundPaint(Color.WHITE);      
        plot.setRangeGridlinesVisible(false);     
        plot.setDomainGridlinesVisible(false);     
        
        Font font = new Font("Open Sans", Font.PLAIN, 11);
        plot.getDomainAxis().setLabelFont(font);
        plot.getDomainAxis().setTickLabelFont(font);
        plot.getRangeAxis().setLabelFont(font);
        plot.getRangeAxis().setTickLabelFont(font);
        plot.getRenderer().setSeriesStroke(0, new BasicStroke(2.0f));
        
        ValueAxis xAxis = plot.getDomainAxis();
        xAxis.setTickLabelsVisible(true);
                         
        ValueAxis yAxis = plot.getRangeAxis();
        yAxis.setTickLabelsVisible(true);                
        
        chartPanel = new ChartPanel(lineChart);
        
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 400));
        chartPanel.setBackground(Color.WHITE);
        setContentPane(chartPanel);
    }

    private TimeSeriesCollection createDataset() {
        TimeSeries series = new TimeSeries("");

        for (int i = 0; i < data.getDate().size(); i++) {
            Date date = data.getDate().get(i);
            Double value = data.getAdjClose().get(i);

            if (value != null && value >= 0) {
                // Use Day for daily dates
                series.add(new Day(date), value);
            }
        }

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(series);
        return dataset;
    }

    public ChartPanel getChart() {
        return chartPanel;
    }
}
