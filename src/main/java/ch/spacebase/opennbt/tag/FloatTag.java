package ch.spacebase.opennbt.tag;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * A tag containing a float.
 */
public class FloatTag extends Tag {

	private float value;
	
	/**
	 * Creates a tag with the specified name.
	 * @param name The name of the tag.
	 */
	public FloatTag(String name) {
		this(name, 0);
	}
	
	/**
	 * Creates a tag with the specified name.
	 * @param name The name of the tag.
	 * @param value The value of the tag.
	 */
	public FloatTag(String name, float value) {
		super(name);
		this.value = value;
	}

	@Override
	public Float getValue() {
		return this.value;
	}
	
	@Override
	public int getId() {
		return 5;
	}
	
	@Override
	public void read(DataInputStream in) throws IOException {
		this.value = in.readFloat();
	}

	@Override
	public void write(DataOutputStream out) throws IOException {
		out.writeFloat(this.value);
	}
	
	/**
	 * Sets the value of this tag.
	 * @param value New value of this tag.
	 */
	public void setValue(float value) {
		this.value = value;
	}
	
	@Override
	public FloatTag clone() {
		return new FloatTag(this.getName(), this.getValue());
	}
	
}
