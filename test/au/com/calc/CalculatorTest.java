package au.com.calc;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

public class CalculatorTest extends Calculator {

	@Ignore
	@Test
	public void testGetFundsWeightForOneLevelSingle() {
		Calculator calc = new Calculator();
		try {
			calc.addFundsRelationsToCalculator("A, B, 2000");
			Map<String, BigDecimal> weights = calc.getFundsWeightFor("A");
			weights.forEach( (k,v)-> System.out.println(k+" "+v));
			assertEquals(2, weights.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Ignore
	@Test
	public void testGetFundsWeightForOneLevelMultiple() {
		Calculator calc = new Calculator();
		try {
			calc.addFundsRelationsToCalculator("A, B, 2000");
			calc.addFundsRelationsToCalculator("A, C, 5000");
			Map<String, BigDecimal> weights = calc.getFundsWeightFor("A");
			System.out.println("A:");
			weights.forEach( (k,v)-> System.out.println(k+" "+v));
			assertEquals(2, weights.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testGetFundsWeightForTwoLevelsMultiple() {
		Calculator calc = new Calculator();
		try {
			calc.addFundsRelationsToCalculator("A0, A, 2000");
			calc.addFundsRelationsToCalculator("A0, D, 2000");
			calc.addFundsRelationsToCalculator("A, B, 2000");
			calc.addFundsRelationsToCalculator("A, C, 5000");
			Map<String, BigDecimal> weights = calc.getFundsWeightFor("A0");
			System.out.println("A0:");
			weights.forEach( (k,v)-> System.out.println(k+" "+v));
			assertEquals(3, weights.size());
			MathContext ctx = new MathContext(Calculator.WEIGHT_PRECISION);
			assertTrue((new BigDecimal(0.5, ctx)).compareTo(weights.get("D")) == 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSameFundIsInTwoPortfolios() {
		Calculator calc = new Calculator();
		try {
			calc.addFundsRelationsToCalculator("A0, A, 2000");
			calc.addFundsRelationsToCalculator("A0, B, 2000");
			calc.addFundsRelationsToCalculator("A, B, 2000");
			calc.addFundsRelationsToCalculator("A, C, 5000");
			Map<String, BigDecimal> weights = calc.getFundsWeightFor("A0");
			System.out.println("A0:");
			weights.forEach( (k,v)-> System.out.println(k+" "+v));
			assertEquals(2, weights.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testAssignmentScenario() {
		Calculator calc = new Calculator();
		try {
			calc.addFundsRelationsToCalculator("A,B,1000");
			calc.addFundsRelationsToCalculator("A,C,2000");
			calc.addFundsRelationsToCalculator("B,D,500");
			calc.addFundsRelationsToCalculator("B,E,250");
			calc.addFundsRelationsToCalculator("B,F,250");
			calc.addFundsRelationsToCalculator("C,G,1000");
			calc.addFundsRelationsToCalculator("C,H,1000");
			Map<String, BigDecimal> weights = calc.getFundsWeightFor("A");
			System.out.println("A:");
			weights.forEach( (k,v)-> System.out.println(k+" "+v.setScale(3,BigDecimal.ROUND_HALF_UP)));
			assertEquals(5, weights.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
