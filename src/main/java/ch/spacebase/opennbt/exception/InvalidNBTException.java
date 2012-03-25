package ch.spacebase.opennbt.exception;

import java.io.IOException;

public class InvalidNBTException extends IOException {

	private static final long serialVersionUID = 1L;

	public InvalidNBTException() {
		super("Invalid NBT file!");
	}
	
	public InvalidNBTException(String message) {
		super(message);
	}
	
}
