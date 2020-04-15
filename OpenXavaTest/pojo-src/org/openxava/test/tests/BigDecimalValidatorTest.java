package org.openxava.test.tests;

import java.math.*;

import org.openxava.util.*;
import org.openxava.validators.*;

/**
 * @author Ana Andres
 * Created on 15 abr. 2020
 */
public class BigDecimalValidatorTest {

	public static void main(String...strings ) {
		try {
			BigDecimalValidator validator = new BigDecimalValidator();
			Messages errors = new Messages();
			BigDecimal value = new BigDecimal("0.0000000");
			validator.setMaximumFractionDigits(7);
			validator.setMaximumIntegerDigits(2);
			validator.validate(errors, value, "", "");
			System.out.println("[BigDecimalValidatorTest.main] errores:"+ errors.toString()); // tmp
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
