package org.spacehq.opennbt;

import org.spacehq.opennbt.tag.TagCreateException;
import org.spacehq.opennbt.tag.TagRegistry;
import org.spacehq.opennbt.tag.builtin.CompoundTag;
import org.spacehq.opennbt.tag.builtin.Tag;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * A class containing methods for reading/writing NBT tags.
 */
public class NBTIO {
	/**
	 * Reads the root CompoundTag from the given file.
	 *
	 * @param path Path of the file.
	 * @return The read compound tag.
	 * @throws java.io.IOException If an I/O error occurs.
	 */
	public static CompoundTag readFile(String path) throws IOException {
		return readFile(new File(path));
	}

	/**
	 * Reads the root CompoundTag from the given file.
	 *
	 * @param file File to read from.
	 * @return The read compound tag.
	 * @throws java.io.IOException If an I/O error occurs.
	 */
	public static CompoundTag readFile(File file) throws IOException {
		return readFile(file, true);
	}

	/**
	 * Reads the root CompoundTag from the given file.
	 *
	 * @param path       Path of the file.
	 * @param compressed Whether the NBT file is compressed.
	 * @return The read compound tag.
	 * @throws java.io.IOException If an I/O error occurs.
	 */
	public static CompoundTag readFile(String path, boolean compressed) throws IOException {
		return readFile(new File(path), compressed);
	}

	/**
	 * Reads the root CompoundTag from the given file.
	 *
	 * @param file       File to read from.
	 * @param compressed Whether the NBT file is compressed.
	 * @return The read compound tag.
	 * @throws java.io.IOException If an I/O error occurs.
	 */
	public static CompoundTag readFile(File file, boolean compressed) throws IOException {
		InputStream in = new FileInputStream(file);
		if(compressed) {
			in = new GZIPInputStream(in);
		}

		Tag tag = readTag(in);
		if(!(tag instanceof CompoundTag)) {
			throw new IOException("Root tag is not a CompoundTag!");
		}

		return (CompoundTag) tag;
	}

	/**
	 * Writes the given root CompoundTag to the given file.
	 *
	 * @param tag  Tag to write.
	 * @param path Path to write to.
	 * @throws java.io.IOException If an I/O error occurs.
	 */
	public static void writeFile(CompoundTag tag, String path) throws IOException {
		writeFile(tag, new File(path));
	}

	/**
	 * Writes the given root CompoundTag to the given file.
	 *
	 * @param tag  Tag to write.
	 * @param file File to write to.
	 * @throws java.io.IOException If an I/O error occurs.
	 */
	public static void writeFile(CompoundTag tag, File file) throws IOException {
		writeFile(tag, file, true);
	}

	/**
	 * Writes the given root CompoundTag to the given file.
	 *
	 * @param tag        Tag to write.
	 * @param path       Path to write to.
	 * @param compressed Whether the NBT file should be compressed.
	 * @throws java.io.IOException If an I/O error occurs.
	 */
	public static void writeFile(CompoundTag tag, String path, boolean compressed) throws IOException {
		writeFile(tag, new File(path), compressed);
	}

	/**
	 * Writes the given root CompoundTag to the given file.
	 *
	 * @param tag        Tag to write.
	 * @param file       File to write to.
	 * @param compressed Whether the NBT file should be compressed.
	 * @throws java.io.IOException If an I/O error occurs.
	 */
	public static void writeFile(CompoundTag tag, File file, boolean compressed) throws IOException {
		if(!file.exists()) {
			if(file.getParentFile() != null && !file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}

			file.createNewFile();
		}

		OutputStream out = new FileOutputStream(file);
		if(compressed) {
			out = new GZIPOutputStream(out);
		}

		writeTag(out, tag);
		out.close();
	}

	/**
	 * Reads an NBT tag.
	 *
	 * @param in Input stream to read from.
	 * @return The read tag, or null if the tag is an end tag.
	 * @throws java.io.IOException If an I/O error occurs.
	 */
	public static Tag readTag(InputStream in) throws IOException {
		DataInputStream dataIn = new DataInputStream(in);

		int id = dataIn.readUnsignedByte();
		if(id == 0) {
			return null;
		}

		String name = dataIn.readUTF();
		Tag tag;

		try {
			tag = TagRegistry.createInstance(id, name);
		} catch(TagCreateException e) {
			throw new IOException("Failed to create tag.", e);
		}

		tag.read(dataIn);
		return tag;
	}

	/**
	 * Writes a tag to an output stream.
	 *
	 * @param out Output stream to write to.
	 * @param tag Tag to write.
	 * @throws java.io.IOException If an I/O error occurs.
	 */
	public static void writeTag(OutputStream out, Tag tag) throws IOException {
		DataOutputStream dataOut = new DataOutputStream(out);

		dataOut.writeByte(TagRegistry.getIdFor(tag.getClass()));
		dataOut.writeUTF(tag.getName());
		tag.write(dataOut);
	}
}
