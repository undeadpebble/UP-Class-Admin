package ClassAdminFrontEnd;

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
	
      return diedata.get(item).get(0).getValue()+ "  "+Double.toString(data.getXValue(series, item)) + ","+Double.toString(data.getYValue(series, item));
   }
   
}

