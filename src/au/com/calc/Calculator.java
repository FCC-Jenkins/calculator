package au.com.calc;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Calculator {
	public static final int WEIGHT_PRECISION = 4;
	public static final BigDecimal INSIGNIFICANT_VALUE = new BigDecimal(0.00049);
	private Map<String, Node> nodes = new HashMap<>();
	
	
	/** This method is used to create Relations tree for the Calculator
	 * @param parent Parent node in relation
	 * @param child Child node in relation
	 * @param value market value in relation
	 * @throws Exception
	 */
	public void addFundsRelationsToCalculator(String parent, String child, BigDecimal value) throws Exception {
		validateRelationValues(parent, child, value);
		Node parentNode = getOrCreateNodeByName(parent);
		Node childNode = getOrCreateNodeByName(child);
		parentNode.getChildren().put(childNode, new FundAttributes(null, value));
	}
	
	/** This method is used to create Relations tree for the Calculator
	 * @param parent Parent node in relation
	 * @param child Child node in relation
	 * @param value market value in relation
	 * @throws Exception
	 */
	public void addFundsRelationsToCalculator(String parent, String child, Double value) throws Exception {
		addFundsRelationsToCalculator(parent, child, new BigDecimal(value));
	}
	
	
	/** This method is used to create Relations tree for the Calculator
	 * @param str
	 * @throws Exception
	 */
	public void addFundsRelationsToCalculator(String str) throws Exception {
		if(str != null) {
			String[] args = str.split(",");
			if (args.length != 3) {
				throw new Exception("3 values are expected as an input for relations string");
			}
			Double weight = null;
			try {
				weight = Double.parseDouble(args[2]);
			} catch (Exception e) {
				throw new Exception("The value "+ args[2] +" is not parsable to digit");
			}
			addFundsRelationsToCalculator(args[0].trim(), args[1].trim(), weight);
		}		
	}

	
	/**
	 * @param name
	 * @return
	 */
	private Node getOrCreateNodeByName(String name) {
		Node node = nodes.get(name);
		if(node == null) {
			node = new Node(name, null);
			nodes.put(name, node);
		}
		return node;
	}

	/**
	 * @param parent
	 * @param child
	 * @param value
	 * @throws Exception
	 */
	private void validateRelationValues(String parent, String child, BigDecimal value) throws Exception {
		if(parent == null || parent.trim().length() == 0) {
			throw new Exception("Parent can't be null or empty");
		}
		if(child == null || child.trim().length() == 0) {
			throw new Exception("Child can't be null or empty");
		}
		if(value == null || value.compareTo(INSIGNIFICANT_VALUE) < 1) {
			throw new Exception("Value can't be null or insignificant");
		}
	}
	
	public Map<String, BigDecimal> getFundsWeightFor(String fund) throws Exception{
		Node node = nodes.get(fund);
		if(node == null) {
			throw new Exception("the fund "+ fund + " is not in the configuration. Please add it to the fund relations.");
		} 
		HashMap<String, BigDecimal> weights = new HashMap<String, BigDecimal>();
		HashSet<String> internalNodes = new HashSet<String>();
		getWeight(node, BigDecimal.ONE,  weights, internalNodes);
		for (String fundName : internalNodes) {
			weights.remove(fundName);
		}
		weights.remove(fund);
		return weights;
	}

	/** This is to calculate the weight of children in relation to the parent node weight(currentNodeWeight)
	 * @param node
	 * @param currentNodeWeight - weight of the parent fund in the relations
	 * @param weights - total weights of the funds
	 * @param internalNodes - set of nodes which will be excluded from the result as they are not leaves
	 */
	private void getWeight(Node node, BigDecimal currentNodeWeight,  Map<String, BigDecimal> weights, Set<String> internalNodes){
		boolean isLeaf = node.getChildren().isEmpty();
		if(!isLeaf) {
			internalNodes.add(node.getName());
			calculateWeights(node, currentNodeWeight, weights, internalNodes);
		}
	}
	
	//This is to calculate the weight of children in relation to the parent node weight(currentNodeWeight)
	private void calculateWeights(Node parentNode, BigDecimal currentNodeWeight, Map<String, BigDecimal> weights, Set<String> internalNodes){
		BigDecimal totalNodeValue = BigDecimal.ZERO;
		for (Map.Entry<Node, FundAttributes> entry : parentNode.getChildren().entrySet()) {
			FundAttributes attributes = entry.getValue();
			if(attributes != null && attributes.getValue() != null ) {
				totalNodeValue = totalNodeValue.add(attributes.getValue());
			}
		}
		for (Map.Entry<Node, FundAttributes> entry : parentNode.getChildren().entrySet()) {
			FundAttributes attributes = entry.getValue();
			if(attributes != null && attributes.getValue() != null ) {
				BigDecimal nodeWeight = (attributes.getValue().multiply(currentNodeWeight).divide(totalNodeValue, WEIGHT_PRECISION, BigDecimal.ROUND_HALF_DOWN));
				Node node = entry.getKey();
				addFundWeightToTotals(node, nodeWeight, weights);
				getWeight(node, nodeWeight, weights, internalNodes);
			}
		}
	}

	/** Some funds may occur before as we scan the tree, so we add current node weight to the previously calculated total 
	 * @param node
	 * @param weight
	 * @param fundsTotalWeights
	 */
	private void addFundWeightToTotals(Node node, BigDecimal weight, Map<String, BigDecimal> fundsTotalWeights) {
		String fund = node.getName(); 
		BigDecimal fundTotalWeight = fundsTotalWeights.get(fund);
		if(fundTotalWeight == null) {
			fundTotalWeight = BigDecimal.ZERO;
		}
		fundsTotalWeights.put(fund, fundTotalWeight.add(weight));
	}
}
