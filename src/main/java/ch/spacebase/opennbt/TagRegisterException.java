package ch.spacebase.opennbt;

/**
 * An exception thrown when an error occurs while registering a tag class.
 */
public class TagRegisterException extends Exception {
	
	private static final long serialVersionUID = -2022049594558041160L;

	public TagRegisterException(Throwable cause) {
		super("Failed to register tag.", cause);
	}
	
}
