package au.com.calc;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Node {
	private String name;
	private BigDecimal value;
	private Map<Node, FundAttributes> children = new HashMap<>();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<Node, FundAttributes> getChildren() {
		return children;
	}
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	/**
	 * @param name
	 * @param value
	 */
	public Node(String name, BigDecimal value) {
		super();
		this.name = name;
		this.value = value;
	}
}
