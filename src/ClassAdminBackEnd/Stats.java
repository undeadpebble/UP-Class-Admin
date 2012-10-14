
package ClassAdminBackEnd;
import java.text.DecimalFormat;
import java.util.LinkedList;

public class Stats {
	private Project project;
	public Stats(Project pproject)
	{
		project = pproject;
	}

	public double hoogstepunt(int hou)
	{
		final LinkedList<LinkedList<SuperEntity>> diedata = project.getHead()
				.getDataLinkedList();
		
		double hoogste=0;
		String studentnr ="";
		
		for(int x=0;x<diedata.size();x++)
		{
			try {
				if(hoogste < diedata.get(x).get(hou).getMark())
				{
					try {
						hoogste = diedata.get(x).get(hou).getMark();
					} catch (AbsentException e) {
						hoogste =0;
					}
				
				}
			} catch (AbsentException e) {
				hoogste =0;
			}
		
		}
		return hoogste;
	}
	public double laagstepunt(int hou)
	{
		final LinkedList<LinkedList<SuperEntity>> diedata = project.getHead()
				.getDataLinkedList();
		
		double laagste=0;
		String studentnr ="";
		
		for(int x=0;x<diedata.size();x++)
		{
			try {
				if(laagste > diedata.get(x).get(hou).getMark())
				{
					try {
						laagste = diedata.get(x).get(hou).getMark();
					} catch (AbsentException e) {
						laagste=9999;
					}
				
				}
			} catch (AbsentException e) {
				laagste =9999;
			}
			
		}
		return laagste;
	}
	public double gemidpunt(int hou)
	{
		double gemid=0;
		int count=0;
		double  som =0;
		final LinkedList<LinkedList<SuperEntity>> diedata = project.getHead()
				.getDataLinkedList();
		
		for(int x=0;x<diedata.size();x++)
		{
			count++;
			try {
				som= som +diedata.get(x).get(hou).getMark();
			} catch (AbsentException e) {
				som= som +0;
			}
		}
		
		gemid = som/count;
		
		return gemid;
	}
	public int fails(int hou)
	{
		final LinkedList<LinkedList<SuperEntity>> diedata = project.getHead()
				.getDataLinkedList();
		int nrVanDruip =0;
		for(int x=0;x<diedata.size();x++)
		{
			try {
				if(diedata.get(x).get(hou).getMark()<50.0)
					nrVanDruip++;
			} catch (AbsentException e) {
				nrVanDruip= nrVanDruip;
			}
		}
		return nrVanDruip;
	}
	public int slaag(int hou)
	{
		final LinkedList<LinkedList<SuperEntity>> diedata = project.getHead()
				.getDataLinkedList();
		int nrVanSlaag =0;
		for(int x=0;x<diedata.size();x++)
		{
			try {
				if(diedata.get(x).get(hou).getMark()>=50.0)
					nrVanSlaag++;
			} catch (AbsentException e) {
				nrVanSlaag = nrVanSlaag;
			}
		}
		return nrVanSlaag;
	}
	public double median(int hou)
	{
		double getal=0.0;
		final LinkedList<LinkedList<SuperEntity>> diedata = project.getHead()
				.getDataLinkedList();
		double[] sort = new double[diedata.size()];
		for(int x=0;x<diedata.size();x++)
		{
			try {
				sort[x] =diedata.get(x).get(hou).getMark();
			} catch (AbsentException e) {
				sort[x] =0;
			}
			
		}
		
		int n = diedata.size();
		double temp = 0;
		double temp2 = 0;
		
		// Bubblesort
		for (int i = 0; i < n; i++) {
			for (int j = 1; j < (n - i); j++) {
				if (sort[j - 1] > sort[j]) {

					// swap the elements!
					

					temp = sort[j - 1];
					sort[j - 1] = sort[j];
					sort[j] = temp;

					

				}

			}
		}
		
		
		return sort[n/2];
	}
	public int distinction(int hou)
	{
		final LinkedList<LinkedList<SuperEntity>> diedata = project.getHead()
				.getDataLinkedList();
		int nrVanDistinct =0;
		for(int x=0;x<diedata.size();x++)
		{
			try {
				if(diedata.get(x).get(hou).getMark()>=75.0)
					nrVanDistinct++;
			} catch (AbsentException e) {
				nrVanDistinct =nrVanDistinct;
			}
		}
		return nrVanDistinct;
	}
	public int her(int hou)
	{
		final LinkedList<LinkedList<SuperEntity>> diedata = project.getHead()
				.getDataLinkedList();
		int nrVanHer =0;
		for(int x=0;x<diedata.size();x++)
		{
			try {
				if(diedata.get(x).get(hou).getMark()>=40.0 && diedata.get(x).get(hou).getMark() < 50.0)
					nrVanHer++;
			} catch (AbsentException e) {
				nrVanHer =nrVanHer;
			}
		}
		return nrVanHer;
	}
	public int totalnrstd()
	{
		final LinkedList<LinkedList<SuperEntity>> diedata = project.getHead()
				.getDataLinkedList();
		return diedata.size(); 
	}
	public double roundTwoDecimals(double d) {
	    DecimalFormat twoDForm = new DecimalFormat("#.##");
	    return Double.valueOf(twoDForm.format(d));
	}
}
