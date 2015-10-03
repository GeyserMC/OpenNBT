package org.spacehq.opennbt.tag.builtin;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * A tag containing a string.
 */
public class StringTag extends Tag {
	private String value;

	/**
	 * Creates a tag with the specified name.
	 *
	 * @param name The name of the tag.
	 */
	public StringTag(String name) {
		this(name, "");
	}

	/**
	 * Creates a tag with the specified name.
	 *
	 * @param name  The name of the tag.
	 * @param value The value of the tag.
	 */
	public StringTag(String name, String value) {
		super(name);
		this.value = value;
	}

	@Override
	public String getValue() {
		return this.value;
	}

	/**
	 * Sets the value of this tag.
	 *
	 * @param value New value of this tag.
	 */
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public void read(DataInputStream in) throws IOException {
		this.value = in.readUTF();
	}

	@Override
	public void write(DataOutputStream out) throws IOException {
		out.writeUTF(this.value);
	}

	@Override
	public StringTag clone() {
		return new StringTag(this.getName(), this.getValue());
	}
}
