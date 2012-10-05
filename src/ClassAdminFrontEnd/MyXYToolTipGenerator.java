package ClassAdminFrontEnd;

import java.text.DecimalFormat;
import java.util.LinkedList;

import org.jfree.chart.labels.CustomXYToolTipGenerator;
import org.jfree.data.xy.XYDataset;

import ClassAdminBackEnd.Global;
import ClassAdminBackEnd.SuperEntity;

public class MyXYToolTipGenerator extends CustomXYToolTipGenerator {

   private static final long serialVersionUID = -383054432396474072L;
   private final LinkedList<LinkedList<SuperEntity>> diedata = Global.getGlobal().getActiveProject().getHead()
			.getDataLinkedList();
   @Override
   public String generateToolTip(XYDataset data, int series, int item) {   
	
      return "Student nr :" + diedata.get(item).get(0).getValue()+ "  ("+Double.toString(roundTwoDecimals(data.getXValue(series, item))) + ","+Double.toString(roundTwoDecimals(data.getYValue(series, item)))+")";
   }
   public double roundTwoDecimals(double d) {
	    DecimalFormat twoDForm = new DecimalFormat("#.##");
	    return Double.valueOf(twoDForm.format(d));
	}
}

