package com.github.steveice10.opennbt.tag.builtin;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * A tag containing a string.
 */
public class StringTag extends Tag implements StringifyableValueTag {
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
    public void read(DataInput in) throws IOException {
        this.value = in.readUTF();
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(this.value);
    }

    @Override
    public void destringify(String in) {
        if(in.charAt(0) == '"') {
            value = in.substring(1, in.length() - 1).replaceAll("\\\\\"", "\"");
        } else if(in.charAt(0) == '\'') {
            value = in.substring(1, in.length() - 1).replaceAll("\\\'", "'");
        } else {
            value = in;
        }
    }

    @Override
    public void stringify(OutputStreamWriter out) throws IOException {
        if(value.matches("(?!\\d+)[\\w\\d]*")) {
            out.append(value);
            return;
        }
        if(value.contains("\"")) {
            if(value.contains("'")) {
                StringBuilder sb = new StringBuilder("\"");
                sb.append(value.replaceAll("\"", "\\\""));
                sb.append("\"");
                out.append(sb.toString());
                return;
            }
            StringBuilder sb = new StringBuilder("'");
            sb.append(value);
            sb.append("'");
            out.append(sb.toString());
            return;
        }
        StringBuilder sb = new StringBuilder("\"");
        sb.append(value);
        sb.append("\"");
        out.append(sb.toString());
    }

    @Override
    public StringTag clone() {
        return new StringTag(this.getName(), this.getValue());
    }
}
