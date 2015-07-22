package org.spacehq.opennbt.tag.builtin.custom;

import org.spacehq.opennbt.tag.builtin.Tag;

import java.io.*;

/**
 * A tag containing an array of serializable objects.
 */
public class SerializableArrayTag extends Tag {
	private Serializable[] value;

	/**
	 * Creates a tag with the specified name.
	 *
	 * @param name The name of the tag.
	 */
	public SerializableArrayTag(String name) {
		this(name, new Serializable[0]);
	}

	/**
	 * Creates a tag with the specified name.
	 *
	 * @param name  The name of the tag.
	 * @param value The value of the tag.
	 */
	public SerializableArrayTag(String name, Serializable[] value) {
		super(name);
		this.value = value;
	}

	@Override
	public Serializable[] getValue() {
		return this.value.clone();
	}

	/**
	 * Sets the value of this tag.
	 *
	 * @param value New value of this tag.
	 */
	public void setValue(Serializable[] value) {
		if(value == null) {
			return;
		}

		this.value = value.clone();
	}

	/**
	 * Gets a value in this tag's array.
	 *
	 * @param index Index of the value.
	 * @return The value at the given index.
	 */
	public Serializable getValue(int index) {
		return this.value[index];
	}

	/**
	 * Sets a value in this tag's array.
	 *
	 * @param index Index of the value.
	 * @param value Value to set.
	 */
	public void setValue(int index, Serializable value) {
		this.value[index] = value;
	}

	/**
	 * Gets the length of this tag's array.
	 *
	 * @return This tag's array length.
	 */
	public int length() {
		return this.value.length;
	}

	@Override
	public void read(DataInputStream in) throws IOException {
		this.value = new Serializable[in.readInt()];
		ObjectInputStream str = new ObjectInputStream(in);
		for(int index = 0; index < this.value.length; index++) {
			try {
				this.value[index] = (Serializable) str.readObject();
			} catch(ClassNotFoundException e) {
				throw new IOException("Class not found while reading SerializableArrayTag!", e);
			}
		}
	}

	@Override
	public void write(DataOutputStream out) throws IOException {
		out.writeInt(this.value.length);
		ObjectOutputStream str = new ObjectOutputStream(out);
		for(int index = 0; index < this.value.length; index++) {
			str.writeObject(this.value[index]);
		}
	}

	@Override
	public SerializableArrayTag clone() {
		return new SerializableArrayTag(this.getName(), this.getValue());
	}
}
