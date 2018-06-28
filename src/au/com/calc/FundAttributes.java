package au.com.calc;

import java.math.BigDecimal;

public class FundAttributes {
	private BigDecimal weight;
	private BigDecimal value;
	/**
	 * @param weight
	 * @param value
	 */
	public FundAttributes(BigDecimal weight, BigDecimal value) {
		super();
		this.weight = weight;
		this.setValue(value);
	}
	
	public BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

}
