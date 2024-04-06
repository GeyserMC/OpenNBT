package com.github.steveice10.opennbt.tag.builtin;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.github.steveice10.opennbt.SNBTIO.StringifiedNBTReader;
import com.github.steveice10.opennbt.SNBTIO.StringifiedNBTWriter;

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
    public void read(DataInput in) throws IOException {
        this.value = in.readLong();
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeLong(this.value);
    }

    @Override
    public void destringify(StringifiedNBTReader in) throws IOException {
        String s = in.readNextSingleValueString();
        s = s.toLowerCase().substring(0, s.length() - 1);
        value = Long.parseLong(s);
    }

    @Override
    public void stringify(StringifiedNBTWriter out, boolean linebreak, int depth) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(value);
        sb.append('l');
        out.append(sb.toString());
    }

    @Override
    public LongTag clone() {
        return new LongTag(this.getName(), this.getValue());
    }
}
