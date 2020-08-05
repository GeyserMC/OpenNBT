package com.github.steveice10.opennbt.tag.builtin;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * A tag containing a byte.
 */
public class ByteTag extends Tag implements StringifyableValueTag {
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
    public void read(DataInput in) throws IOException {
        this.value = in.readByte();
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeByte(this.value);
    }

    @Override
    public void destringify(String in) {
        String valueString = in.toLowerCase().substring(0, in.length() - 1);
        value = Byte.parseByte(valueString);
    }

    @Override
    public void stringify(OutputStreamWriter out) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(value);
        sb.append('b');
        out.append(sb.toString());
    }

    @Override
    public ByteTag clone() {
        return new ByteTag(this.getName(), this.getValue());
    }
}
