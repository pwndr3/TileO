package ca.mcgill.ecse223.tileo.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Cloner {

	public static Object clone(Object object) {
		return deserialize(serialize(object));
	}

	private static ByteArrayOutputStream serialize(Object object) {
		ByteArrayOutputStream baos;
		try {
			baos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(baos);
			out.writeObject(object);
			out.close();
			baos.close();
		} catch (Exception e) {
			throw new RuntimeException("Could not serialize the object.");
		}
		return baos;
	}

	private static Object deserialize(ByteArrayOutputStream baos) {
		Object o = null;
		ObjectInputStream in;
		try {
			ByteArrayInputStream fileIn = new ByteArrayInputStream(baos.toByteArray());
			in = new ObjectInputStream(fileIn);
			o = in.readObject();
			in.close();
			fileIn.close();
		} catch (Exception e) {
			o = null;
		}
		return o;
	}
	
}
