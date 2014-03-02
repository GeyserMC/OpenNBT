package org.spacehq.opennbt.tag;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * A tag containing a byte.
 */
public class ByteTag extends Tag {

	private byte value;

	/**
	 * Creates a tag with the specified name.
	 *
	 * @param name The name of the tag.
	 */
	public ByteTag(String name) {
		this(name, (byte) 0);
	}

	/**
	 * Creates a tag with the specified name.
	 *
	 * @param name  The name of the tag.
	 * @param value The value of the tag.
	 */
	public ByteTag(String name, byte value) {
		super(name);
		this.value = value;
	}

	@Override
	public Byte getValue() {
		return this.value;
	}

	/**
	 * Sets the value of this tag.
	 *
	 * @param value New value of this tag.
	 */
	public void setValue(byte value) {
		this.value = value;
	}

	@Override
	public int getId() {
		return 1;
	}

	@Override
	public void read(DataInputStream in) throws IOException {
		this.value = in.readByte();
	}

	@Override
	public void write(DataOutputStream out) throws IOException {
		out.writeByte(this.value);
	}

	@Override
	public ByteTag clone() {
		return new ByteTag(this.getName(), this.getValue());
	}

}
