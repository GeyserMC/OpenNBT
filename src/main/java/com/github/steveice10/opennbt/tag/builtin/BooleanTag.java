package com.github.steveice10.opennbt.tag.builtin;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.github.steveice10.opennbt.SNBTIO.StringifiedNBTReader;
import com.github.steveice10.opennbt.SNBTIO.StringifiedNBTWriter;
import com.github.steveice10.opennbt.tag.builtin.Tag;

public class BooleanTag extends Tag {
	
	private boolean value;

	public BooleanTag(String name) {
		super(name);
	}

	public BooleanTag(String name, boolean value) {
		super(name);
		this.value = value;
	}

	@Override
	public Object getValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}

	@Override
	public void read(DataInput in) throws IOException {
		this.value = in.readBoolean();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeBoolean(value);
	}

	@Override
	public void destringify(StringifiedNBTReader in) throws IOException {
        value = Boolean.parseBoolean(in.readNextSingleValueString());
	}

	@Override
	public void stringify(StringifiedNBTWriter out, boolean linebreak, int depth) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(value);
        out.append(sb.toString());
	}

	@Override
	public Tag clone() {
		return new BooleanTag(getName(), value);
	}
}
