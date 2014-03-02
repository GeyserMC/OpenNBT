package org.spacehq.opennbt;

import org.spacehq.opennbt.tag.CompoundTag;
import org.spacehq.opennbt.tag.Tag;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class NBTFileIO {

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

		Tag tag = NBTIO.readTag(new DataInputStream(in));
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

		NBTIO.writeTag(new DataOutputStream(out), tag);
		out.close();
	}

}
