package ClassAdminFrontEnd;

import java.awt.*;
import java.text.NumberFormat;
import javax.swing.JPanel;
import org.jfree.chart.*;
import org.jfree.chart.annotations.CategoryTextAnnotation;
import org.jfree.chart.axis.CategoryAnchor;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.*;

public class BarChart3DDemo4 extends ApplicationFrame
{
        static class CustomBarRenderer3D extends BarRenderer3D
        {

                public Paint getItemPaint(int i, int j)
                {
                        CategoryDataset categorydataset = getPlot().getDataset();
                        double d = categorydataset.getValue(i, j).doubleValue();
                        if (d >= 0.69999999999999996D)
                                return Color.green;
                        else
                                return Color.red;
                }

                public CustomBarRenderer3D()
                {
                }
        }


        public BarChart3DDemo4(String s)
        {
                super(s);
                CategoryDataset categorydataset = createDataset();
                JFreeChart jfreechart = createChart(categorydataset);
                ChartPanel chartpanel = new ChartPanel(jfreechart);
                chartpanel.setPreferredSize(new Dimension(500, 270));
                setContentPane(chartpanel);
        }

        private static CategoryDataset createDataset()
        {
                DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
                defaultcategorydataset.addValue(0.77000000000000002D, "Series 1", "Robert");
                defaultcategorydataset.addValue(0.93000000000000005D, "Series 1", "Mary");
                defaultcategorydataset.addValue(0.58999999999999997D, "Series 1", "John");
                defaultcategorydataset.addValue(0.75D, "Series 1", "Ellen");
                defaultcategorydataset.addValue(0.63D, "Series 1", "Jack");
                defaultcategorydataset.addValue(0.94999999999999996D, "Series 1", "David");
                defaultcategorydataset.addValue(0.70999999999999996D, "Series 1", "Mark");
                defaultcategorydataset.addValue(0.65000000000000002D, "Series 1", "Andy");
                return defaultcategorydataset;
        }

        private static JFreeChart createChart(CategoryDataset categorydataset)
        {
                JFreeChart jfreechart = ChartFactory.createBarChart3D("Student Grades", "Students", "Grade", categorydataset, PlotOrientation.VERTICAL, false, true, false);
                CategoryPlot categoryplot = (CategoryPlot)jfreechart.getPlot();
                CustomBarRenderer3D custombarrenderer3d = new CustomBarRenderer3D();
                custombarrenderer3d.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
                custombarrenderer3d.setBaseItemLabelsVisible(true);
                custombarrenderer3d.setItemLabelAnchorOffset(10D);
                custombarrenderer3d.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
                categoryplot.setRenderer(custombarrenderer3d);
               
                ValueMarker valuemarker = new ValueMarker(0.69999999999999996D, new Color(200, 200, 255), new BasicStroke(1.0F), new Color(200, 200, 255), new BasicStroke(1.0F), 1.0F);
                categoryplot.addRangeMarker(valuemarker, Layer.BACKGROUND);
                custombarrenderer3d.setBaseItemLabelsVisible(true);
                custombarrenderer3d.setMaximumBarWidth(0.050000000000000003D);
                CategoryTextAnnotation categorytextannotation = new CategoryTextAnnotation("Minimum grade to pass", "Robert", 0.70999999999999996D);
                categorytextannotation.setCategoryAnchor(CategoryAnchor.START);
                categorytextannotation.setFont(new Font("SansSerif", 0, 12));
                categorytextannotation.setTextAnchor(TextAnchor.BOTTOM_LEFT);
                categoryplot.addAnnotation(categorytextannotation);
                NumberAxis numberaxis = (NumberAxis)categoryplot.getRangeAxis();
                numberaxis.setNumberFormatOverride(NumberFormat.getPercentInstance());
                numberaxis.setUpperMargin(0.10000000000000001D);
                return jfreechart;
        }

        public static JPanel createDemoPanel()
        {
                JFreeChart jfreechart = createChart(createDataset());
                return new ChartPanel(jfreechart);
        }

        public static void main(String args[])
        {
                BarChart3DDemo4 barchart3ddemo4 = new BarChart3DDemo4("3D Bar Chart Demo 4");
                barchart3ddemo4.pack();
                RefineryUtilities.centerFrameOnScreen(barchart3ddemo4);
                barchart3ddemo4.setVisible(true);
        }
}
