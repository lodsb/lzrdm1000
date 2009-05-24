package sparshui.common.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;

/**
 * This class provides static functionality for reading objects from input
 * streams and writing objects to output streams. It uses a form of
 * serialization that aims to be easy and quick to use from both Java and C++.
 * 
 * Objects are serialized by the following process:
 *  - For the initial top-level call:
 *  	- Send the LENGTH of the type name (4 bytes)
 *  	- Send the characters of the name (LENGTH bytes)
 *  - Check which type of object is being sent
 *  	- IF COMPOSITE/CUSTOM OBJECT (The only type allowed for top-level calls)
 *  		- Check if a subclass is being used instead of the expected class
 *  			- IF TRUE
 * 					- Send the value "1" (1 byte)
 *  				- Send the LENGTH of the type name (4 bytes)
 *  				- Send the characters of the name (LENGTH bytes)
 *  			- IF FALSE
 *  				- Send the value "0" (1 byte)
 *  				- Sort the fields by field name
 *  		- Recursively serialize each field object within this object in alphabetical order
 *  	- IF PRIMITIVE
 *  		- Send the network-byte order version of the primitive data
 *  	- IF ENUM
 *  		- Send an integer representing the value (4 bytes)
 *  	- IF ARRAY
 *  		- Send the LENGTH of the array (4 bytes)
 *  		- Recursively serialize all contained objects
 * 
 * The following restrictions are currently imposed on classes wishing to use
 * the methods of this class for serialization:
 * 		- The class must have a nullary (empty) constructor
 * 			(Other constructors may also be present)
 * 		- No cycles are allowed in references
 * Most of these restrictions will be lifted as the implementation improves.
 */
// TODO: Support partial failure for unrecognized classes or processing errors
//			- Always send full message size first
//			- Always read full message into byte array before processing
public class Serializer {
	
	/**
	 * Read an object from the specified input stream.
	 * 
	 * @param in
	 *            The input stream from which to read an object.
	 * @return The object read from the input stream or null if the read failed.
	 */
	public static Object read(DataInputStream in) {
		try {
			String name = readString(in);
			return read(name, in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * Read an object of the specified type (name) from the specified input
	 * stream.
	 * 
	 * @param name
	 *            The name of the type of object to read
	 * @param in
	 *            The input stream from which to read an object
	 * @return The object read from the input stream or null if the read failed.
	 */
	public static Object read(String name, DataInputStream in) {
		try {
			// Instantiate the appropriate class to hold the object information
			Class<?> type = Class.forName(name);
			return read(type, in);
		} catch (ClassNotFoundException e) {
			System.out.println("Could not find a class matching the name: "+name);
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Read on object of the specified type (class) from the specified input
	 * stream.
	 * 
	 * @param type
	 *            The class that defines the type of object to read
	 * @param in
	 *            The input stream from which to read an object
	 * @return The object read from the input stream or null if the read failed.
	 */
	public static Object read(Class<?> type, DataInputStream in) {
		try {
			// Check for the appropriate handling method to read the object
			if(type.isPrimitive()) return readPrimitive(type, in);
			if(type.isArray()) return readArray(type, in);
			if(type.isEnum()) return readEnum(type, in);
			if(type.equals(String.class)) return readString(in);
			return readObject(type, in);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (InstantiationException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * @param type
	 * @param in
	 * @return
	 * @throws IOException
	 */
	private static Object readArray(Class<?> type, DataInputStream in) throws IOException {
		Class<?> componentType = type.getComponentType();
		int length = in.readInt();
		Object arr = Array.newInstance(componentType, length);
//System.out.println("READING ARRAY: "+ componentType +" [" + length + "]");
		for(int index = 0; index < length; index++) {
			Object item = read(componentType, in);
//System.out.println("    ["+index+"] = "+item);
			Array.set(arr, index, item);
		}
		return arr;
	}
	
	/**
	 * 
	 * @param type
	 * @param in
	 * @return
	 * @throws IOException 
	 */
	private static Object readEnum(Class<?> type, DataInputStream in) throws IOException {
		int ordinal = in.readInt();
		Object[] constants = type.getEnumConstants();
		for(int i = 0; i < constants.length; i++) {
			if(i == ordinal) return constants[i];
		}
		return null;
	}

	/**
	 * 
	 * @param type
	 * @param in
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	private static Object readObject(Class<?> type, DataInputStream in) throws InstantiationException, IllegalAccessException, IOException, ClassNotFoundException {
//System.out.println("READING OBJECT: " + type.getName());
		
		boolean isSubclass = in.readBoolean();
		if(isSubclass) type = Class.forName(readString(in));
		
//System.out.println("     TRUE TYPE: " + type.getName());
		
		// Construct a new instance of the object using the nullary constructor
		Object obj = type.newInstance();
		
		// Read the value from the input stream into each field
		for(Field field : getSortedFields(type)) {
			field.setAccessible(true);
//System.out.println("READING FIELD: " + field.getType().getName());
			Object fieldObj = read(field.getType(), in);
//System.out.println("        VALUE: " + fieldObj);
			field.set(obj, fieldObj);
		}

		return obj;
	}
	
	/**
	 * 
	 * @param type
	 * @param in
	 * @return
	 * @throws IOException
	 */
	private static Object readPrimitive(Class<?> type, DataInputStream in) throws IOException {
		if(type.equals(int.class))     return in.readInt();
		if(type.equals(byte.class))    return in.readByte();
		if(type.equals(float.class))   return in.readFloat();
		if(type.equals(long.class))    return in.readLong();
		if(type.equals(double.class))  return in.readDouble();
		if(type.equals(short.class))   return in.readShort();
		if(type.equals(boolean.class)) return in.readBoolean();
		if(type.equals(char.class))    return (char)in.readByte();
		
		return null;
	}
	
	/**
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	private static String readString(DataInputStream in) throws IOException {
		String str = new String();
		int length = in.readInt();
		for(int i = 0; i < length; i++) {
			str += (char)in.readByte();
		}
		return str;
	}
	
	/**
	 * Write an object to the specified output stream.
	 * 
	 * @param out
	 *            The object to write to the output stream.
	 */
	public static void write(Object obj, DataOutputStream out) {
		try {
			writeString(obj.getClass().getName(), out);
			write(obj, obj.getClass(), out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param obj
	 * @param type
	 * @param out
	 */
	public static void write(Object obj, Class<?> type, DataOutputStream out) {
		try {
			if(type.isArray())
				writeArray(obj, out);
			else if(type.isEnum())
				writeEnum(obj, out);
			// Handle Primitives
			else if(type.equals(String.class))
				writeString((String)obj, out);
			else if(type.equals(int.class) || type.equals(Integer.class))
				out.writeInt((Integer)obj);
			else if(type.equals(byte.class) || type.equals(Byte.class))
				out.writeByte((Byte)obj);
			else if(type.equals(float.class) || type.equals(Float.class))
				out.writeFloat((Float)obj);
			else if(type.equals(long.class) || type.equals(Long.class))
				out.writeLong((Long)obj);
			else if(type.equals(double.class) || type.equals(Double.class))
				out.writeDouble((Double)obj);
			else if(type.equals(short.class) || type.equals(Short.class))
				out.writeShort((Short)obj);
			else if(type.equals(boolean.class) || type.equals(Boolean.class))
				out.writeBoolean((Boolean)obj);
			else if(type.equals(char.class) || type.equals(Character.class))
				out.writeByte((Character)obj);
			// Handle Custom Objects
			else
				writeObject(obj, type, out);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param obj
	 * @param out
	 * @throws IOException
	 */
	private static void writeArray(Object obj, DataOutputStream out) throws IOException {
		int length = Array.getLength(obj);
		out.writeInt(length);
		Class<?> componentType = obj.getClass().getComponentType();
//System.out.println("WRITING ARRAY: " + componentType + " [" + length + "]");
		for(int index = 0; index < length; index++) {
			Object item = Array.get(obj, index);
			write(item, componentType, out);
//System.out.println("    ["+index+"] = "+item);
		}
	}
	
	/**
	 * 
	 * @param obj
	 * @param out
	 * @throws IOException 
	 */
	private static void writeEnum(Object obj, DataOutputStream out) throws IOException {
		Enum<?> val = (Enum<?>)obj;
		out.writeInt(val.ordinal());
	}
	
	/**
	 * 
	 * @param obj
	 * @param out
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException 
	 */
	private static void writeObject(Object obj, Class<?> type, DataOutputStream out) throws IllegalArgumentException, IllegalAccessException, IOException {
//System.out.println("WRITING OBJECT: " + type.getName());
		boolean isSubclass = !type.equals(obj.getClass());
		out.writeBoolean(isSubclass);
//System.out.println("     TRUE TYPE: " + obj.getClass().getName());
		if(isSubclass) writeString(obj.getClass().getName(), out);
		// Write the values for each field
		for(Field field : getSortedFields(obj.getClass())) {
			field.setAccessible(true);
//System.out.println("WRITING FIELD: " + field.getType().getName());
			//Object fieldObj = field.get(obj);
			write(field.get(obj), field.getType(), out);
//System.out.println("        VALUE: " + fieldObj);
		}
	}
	
	/**
	 * 
	 * @param value
	 * @param out
	 * @throws IOException
	 */
	private static void writeString(String value, DataOutputStream out) throws IOException {
		out.writeInt(value.length());
		for(int i = 0; i < value.length(); i++)
			out.writeByte((byte)value.charAt(i));
	}
	
	/**
	 * 
	 * @param type
	 * @return
	 */
	private static Field[] getSortedFields(Class<?> type) {
		Field[] fields = type.getDeclaredFields();
		Arrays.sort(fields, new FieldComparator());
		return fields;
	}
	
	/**
	 * 
	 */
	private static class FieldComparator implements Comparator<Field> {
		@Override
		public int compare(Field o1, Field o2) {
			return o1.getName().compareTo(o2.getName());
		}
	}
}
