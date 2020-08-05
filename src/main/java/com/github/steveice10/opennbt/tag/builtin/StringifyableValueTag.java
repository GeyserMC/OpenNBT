package com.github.steveice10.opennbt.tag.builtin;

import java.io.IOException;
import java.io.OutputStreamWriter;

public interface StringifyableValueTag {
    /**
     * Parses this tag from stringified NBT.
     *
     * @param in String to parse.
     */
    public abstract void destringify(String in);

    /**
     * Write this tag as stringified NBT.
     */
    public abstract void stringify(OutputStreamWriter out) throws IOException;
}
