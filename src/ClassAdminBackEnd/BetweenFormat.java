package ClassAdminBackEnd;

import java.awt.Color;

public class BetweenFormat extends Format {
	Double value2;

	/**
	 * @return the value2
	 */
	public Double getValue2() {
		return value2;
	}

	/**
	 * @param value2 the value2 to set
	 */
	public void setValue2(Double value2) {
		this.value2 = value2;
	}

	public BetweenFormat(int priority, Double value1, Double value2, Color textColor,
			Color highlightColor, String description) {
		super(priority, value1, textColor, highlightColor, description);
		if(value1 < value2)
			this.value2=value2;
		else{
			this.value2 = value1;
			this.setValue1(value2);
		}
		// TODO Auto-generated constructor stub
	}
	
	public Boolean evaluate(double mark){
		return (this.getValue1() <= mark && mark <= this.getValue2());
	}	

}
