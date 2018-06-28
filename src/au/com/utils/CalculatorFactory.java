package au.com.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Scanner;

import au.com.calc.Calculator;

public class CalculatorFactory {
	public static void main(String[] args) {
		if (args.length != 1 ) {
			System.out.println("The full file name is expected. Please provide as argument.");
			System.exit(0);
		}
		String fileName = args[0];
		String line = "";
		Calculator calc = new Calculator();
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			while ((line = br.readLine()) != null) {
				calc.addFundsRelationsToCalculator(line);
			}
		} catch (IOException e) {
			System.out.println("Error of processing the relations file " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error of initialising the calculator " + e.getMessage());
		}
		try (Scanner scanner = new Scanner(System.in)) {
			while (true) {
				System.out.print("Enter the fund : ");
				String input = scanner.nextLine();
				try {
					Map<String, BigDecimal> weights = calc.getFundsWeightFor(input);
					weights.forEach((k, v) -> System.out.println(k + " " + v.setScale(3, BigDecimal.ROUND_HALF_UP)));
				} catch (Exception e) {
					System.out.println("Error of processing " + e.getMessage());

				}
			}
		}

	}

}
