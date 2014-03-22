package org.spacehq.opennbt;

import org.spacehq.opennbt.tag.Tag;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A class containing methods for reading/writing NBT tags.
 */
public class NBTIO {

	public static final Charset CHARSET = Charset.forName("UTF-8");

	/**
	 * Reads NBT tags until an end tag is reached.
	 *
	 * @param in Input stream to read from.
	 * @return The read tags.
	 * @throws java.io.IOException If an I/O error occurs.
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
	 *
	 * @param in Input stream to read from.
	 * @return The read tag, or null if the tag is an end tag.
	 * @throws java.io.IOException If an I/O error occurs.
	 */
	public static Tag readTag(DataInputStream in) throws IOException {
		int id = in.readUnsignedByte();
		if(id == 0) {
			return null;
		}

		byte[] nameBytes = new byte[in.readUnsignedShort()];
		in.readFully(nameBytes);
		String name = new String(nameBytes, CHARSET);
		Tag tag = null;
		try {
			tag = TagRegistry.createInstance(id, name);
		} catch(TagCreateException e) {
			throw new IOException("Failed to create tag.", e);
		}

		tag.read(in);
		return tag;
	}

	/**
	 * Writes a collection of tags to an output stream.
	 *
	 * @param out  Output stream to write to.
	 * @param tags Tags to write.
	 * @throws java.io.IOException If an I/O error occurs.
	 */
	public static void writeTags(DataOutputStream out, Collection<Tag> tags) throws IOException {
		for(Tag tag : tags) {
			writeTag(out, tag);
		}
	}

	/**
	 * Writes a tag to an output stream.
	 *
	 * @param out Output stream to write to.
	 * @param tag Tag to write.
	 * @throws java.io.IOException If an I/O error occurs.
	 */
	public static void writeTag(DataOutputStream out, Tag tag) throws IOException {
		byte[] nameBytes = tag.getName().getBytes(CHARSET);
		out.writeByte(TagRegistry.getIdFor(tag.getClass()));
		out.writeShort(nameBytes.length);
		out.write(nameBytes);
		tag.write(out);
	}

}
