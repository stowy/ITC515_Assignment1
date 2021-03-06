package library.classes.utils;

import java.util.Date;

public class VerificationUtil {
	
	public static void assertNotNullOrEmpty(String string) throws IllegalArgumentException {
		if (string == null || string.isEmpty()) {
			throw new IllegalArgumentException("String argument is not valid");
		}
	}
	
	public static void assertNonZeroPositive(int arg) throws IllegalArgumentException {
		if (arg <= 0) {
			throw new IllegalArgumentException("int argument is non-positive");
		}
	}
	
	public static void assertNotNull(Object arg) throws IllegalArgumentException {
		if (arg == null) {
			throw new IllegalArgumentException("Object argument is null");
		}
	}
	
	public static void assertLater(Date earlierDate, Date laterDate) throws IllegalArgumentException {
		if (laterDate.before(earlierDate)) {
			throw new IllegalArgumentException("Object argument is null");
		}
	}
	
	public static void assertPositive(int arg) throws IllegalArgumentException {
		if (arg < 0) {
			throw new IllegalArgumentException("int arg is negative");
		}
	}
	
	public static void assertPositive(float arg) throws IllegalArgumentException {
		if (arg < 0) {
			throw new IllegalArgumentException("int arg is negative");
		}
	}
	
	public static void assertLess(float lesserAmount, float greaterAmount) throws IllegalArgumentException {
		if (lesserAmount > greaterAmount) {
			throw new IllegalArgumentException("int arg is negative");
		}
	}
}
