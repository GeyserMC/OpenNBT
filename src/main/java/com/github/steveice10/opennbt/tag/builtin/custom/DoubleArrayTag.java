package com.github.steveice10.opennbt.tag.builtin.custom;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.github.steveice10.opennbt.SNBTIO.StringifiedNBTReader;
import com.github.steveice10.opennbt.SNBTIO.StringifiedNBTWriter;
import com.github.steveice10.opennbt.tag.builtin.Tag;

/**
 * A tag containing a double array.
 */
public class DoubleArrayTag extends Tag {
    private double[] value;

    /**
     * Creates a tag with the specified name.
     *
     * @param name The name of the tag.
     */
    public DoubleArrayTag(String name) {
        this(name, new double[0]);
    }

    /**
     * Creates a tag with the specified name.
     *
     * @param name  The name of the tag.
     * @param value The value of the tag.
     */
    public DoubleArrayTag(String name, double[] value) {
        super(name);
        this.value = value;
    }

    @Override
    public double[] getValue() {
        return this.value.clone();
    }

    /**
     * Sets the value of this tag.
     *
     * @param value New value of this tag.
     */
    public void setValue(double[] value) {
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
    public double getValue(int index) {
        return this.value[index];
    }

    /**
     * Sets a value in this tag's array.
     *
     * @param index Index of the value.
     * @param value Value to set.
     */
    public void setValue(int index, double value) {
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
    public void read(DataInput in) throws IOException {
        this.value = new double[in.readInt()];
        for(int index = 0; index < this.value.length; index++) {
            this.value[index] = in.readDouble();
        }
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(this.value.length);
        for(int index = 0; index < this.value.length; index++) {
            out.writeDouble(this.value[index]);
        }
    }

    @Override
    public void destringify(StringifiedNBTReader in) throws IOException {
        String s = in.readUntil(true, ']');
        String[] valueStrings = s.substring(s.indexOf(';') + 1, s.length() - 1).replaceAll(" ", "").split(",");
        value = new double[valueStrings.length];
        for(int i = 0; i < value.length; i++) {
            value[i] = Double.parseDouble(valueStrings[i]);
        }
    }

    @Override
    public void stringify(StringifiedNBTWriter out, boolean linebreak, int depth) throws IOException {
        StringBuilder sb = new StringBuilder("[D; ");
        for(double b : value) {
            sb.append(b);
            sb.append(',');
            sb.append(' ');
        }
        sb.setLength(sb.length() - 2);
        sb.append(']');
        out.append(sb.toString());
    }

    @Override
    public DoubleArrayTag clone() {
        return new DoubleArrayTag(this.getName(), this.getValue());
    }
}
