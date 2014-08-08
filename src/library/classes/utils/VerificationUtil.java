package library.classes.utils;

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
}
