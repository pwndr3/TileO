package ca.mcgill.ecse223.tileo.controller;

import java.io.*;

//TODO check if this class is properly implemented.
public class InvalidInputException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public InvalidInputException(String string) {
		super(string);
	}
}
