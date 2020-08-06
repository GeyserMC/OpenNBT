package com.github.steveice10.opennbt.tag.builtin;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Array;

/**
 * Represents an NBT tag.
 * <p>
 * All tags must have a constructor with a single string parameter for reading tags (can be any visibility).
 * Tags should also have setter methods specific to their value types.
 */
public abstract class Tag implements Cloneable {
    private String name;

    /**
     * Creates a tag with the specified name.
     *
     * @param name The name.
     */
    public Tag(String name) {
        this.name = name;
    }

    /**
     * Gets the name of this tag.
     *
     * @return The name of this tag.
     */
    public final String getName() {
        return this.name;
    }

    /**
     * Gets the value of this tag.
     *
     * @return The value of this tag.
     */
    public abstract Object getValue();

    /**
     * Reads this tag from an input stream.
     *
     * @param in Stream to read from.
     * @throws java.io.IOException If an I/O error occurs.
     */
    public abstract void read(DataInput in) throws IOException;

    /**
     * Writes this tag to an output stream.
     *
     * @param out Stream to write to.
     * @throws java.io.IOException If an I/O error occurs.
     */
    public abstract void write(DataOutput out) throws IOException;

    @Override
    public abstract Tag clone();

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Tag)) {
            return false;
        }

        Tag tag = (Tag) obj;
        if(!this.getName().equals(tag.getName())) {
            return false;
        }

        if(this.getValue() == null) {
            return tag.getValue() == null;
        } else if(tag.getValue() == null) {
            return false;
        }

        if(this.getValue().getClass().isArray() && tag.getValue().getClass().isArray()) {
            int length = Array.getLength(this.getValue());
            if(Array.getLength(tag.getValue()) != length) {
                return false;
            }

            for(int index = 0; index < length; index++) {
                Object o = Array.get(this.getValue(), index);
                Object other = Array.get(tag.getValue(), index);
                if(o == null && other != null || o != null && !o.equals(other)) {
                    return false;
                }
            }

            return true;
        }

        return this.getValue().equals(tag.getValue());
    }

    @Override
    public String toString() {
        String name = this.getName() != null && !this.getName().equals("") ? "(" + this.getName() + ")" : "";
        String value = "";
        if(this.getValue() != null) {
            value = this.getValue().toString();
            if(this.getValue().getClass().isArray()) {
                StringBuilder build = new StringBuilder();
                build.append("[");
                for(int index = 0; index < Array.getLength(this.getValue()); index++) {
                    if(index > 0) {
                        build.append(", ");
                    }

                    build.append(Array.get(this.getValue(), index));
                }

                build.append("]");
                value = build.toString();
            }
        }

        return this.getClass().getSimpleName() + name + " { " + value + " }";
    }
}
