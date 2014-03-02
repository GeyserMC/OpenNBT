package org.spacehq.opennbt.tag;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * A tag containing a long.
 */
public class LongTag extends Tag {

	private long value;

	/**
	 * Creates a tag with the specified name.
	 *
	 * @param name The name of the tag.
	 */
	public LongTag(String name) {
		this(name, 0);
	}

	/**
	 * Creates a tag with the specified name.
	 *
	 * @param name  The name of the tag.
	 * @param value The value of the tag.
	 */
	public LongTag(String name, long value) {
		super(name);
		this.value = value;
	}

	@Override
	public Long getValue() {
		return this.value;
	}

	/**
	 * Sets the value of this tag.
	 *
	 * @param value New value of this tag.
	 */
	public void setValue(long value) {
		this.value = value;
	}

	@Override
	public int getId() {
		return 4;
	}

	@Override
	public void read(DataInputStream in) throws IOException {
		this.value = in.readLong();
	}

	@Override
	public void write(DataOutputStream out) throws IOException {
		out.writeLong(this.value);
	}

	@Override
	public LongTag clone() {
		return new LongTag(this.getName(), this.getValue());
	}

}
