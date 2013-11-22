package ch.spacebase.opennbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ch.spacebase.opennbt.tag.Tag;

/**
 * A class containing methods for reading/writing NBT tags.
 */
public class NBTIO {

	public static final Charset CHARSET = Charset.forName("UTF-8");
	
	/**
	 * Reads NBT tags until an end tag is reached.
	 * @param in Input stream to read from.
	 * @return The read tags.
	 * @throws IOException If an I/O error occurs.
	 */
	public static List<Tag> readUntilEndTag(DataInputStream in) throws IOException {
		List<Tag> ret = new ArrayList<Tag>();
		try {
			Tag tag;
			while((tag = readTag(in)) != null) {
				ret.add(tag);
			}
		} catch(EOFException e) {
			throw new IOException("Closing EndTag was not found!");
		}
		
		return ret;
	}
	
	/**
	 * Reads an NBT tag.
	 * @param in Input stream to read from.
	 * @return The read tag, or null if the tag is an end tag.
	 * @throws IOException If an I/O error occurs.
	 */
	public static Tag readTag(DataInputStream in) throws IOException {
		int id = in.readByte() & 0xFF;
		if(id == 0) {
			return null;
		}
		
		byte[] nameBytes = new byte[in.readShort() & 0xFFFF];
		in.readFully(nameBytes);
		String name = new String(nameBytes, CHARSET);
		Tag tag = TagRegistry.createInstance(id, name);
		if(tag == null) {
			throw new IOException("Invalid tag: " + id);
		}
		
		tag.read(in);
		return tag;
	}
	
	/**
	 * Writes a collection of tags to an output stream.
	 * @param out Output stream to write to.
	 * @param tags Tags to write.
	 * @throws IOException If an I/O error occurs.
	 */
	public static void writeTags(DataOutputStream out, Collection<Tag> tags) throws IOException {
		for(Tag tag : tags) {
			writeTag(out, tag);
		}
	}
	
	/**
	 * Writes a tag to an output stream.
	 * @param out Output stream to write to.
	 * @param tag Tag to write.
	 * @throws IOException If an I/O error occurs.
	 */
	public static void writeTag(DataOutputStream out, Tag tag) throws IOException {
		byte[] nameBytes = tag.getName().getBytes(CHARSET);
		out.writeByte(tag.getId());
		out.writeShort(nameBytes.length);
		out.write(nameBytes);
		tag.write(out);
	}
	
}
