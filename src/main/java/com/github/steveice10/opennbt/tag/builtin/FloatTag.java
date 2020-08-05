package com.github.steveice10.opennbt.tag.builtin;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * A tag containing a float.
 */
public class FloatTag extends Tag implements StringifyableValueTag {
    private float value;

    /**
     * Creates a tag with the specified name.
     *
     * @param name The name of the tag.
     */
    public FloatTag(String name) {
        this(name, 0);
    }

    /**
     * Creates a tag with the specified name.
     *
     * @param name  The name of the tag.
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

    /**
     * Sets the value of this tag.
     *
     * @param value New value of this tag.
     */
    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public void read(DataInput in) throws IOException {
        this.value = in.readFloat();
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeFloat(this.value);
    }

    @Override
    public void destringify(String in) {
        String valueString = in.toLowerCase().substring(0, in.length() - 1);
        value = Float.parseFloat(valueString);
    }

    @Override
    public void stringify(OutputStreamWriter out) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(value);
        sb.append('f');
        out.append(sb.toString());
    }

    @Override
    public FloatTag clone() {
        return new FloatTag(this.getName(), this.getValue());
    }
}
