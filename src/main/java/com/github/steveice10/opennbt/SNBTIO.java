package com.github.steveice10.opennbt;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PushbackReader;
import java.io.Reader;
import java.util.regex.Pattern;

import com.github.steveice10.opennbt.tag.builtin.ByteArrayTag;
import com.github.steveice10.opennbt.tag.builtin.ByteTag;
import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.github.steveice10.opennbt.tag.builtin.DoubleTag;
import com.github.steveice10.opennbt.tag.builtin.FloatTag;
import com.github.steveice10.opennbt.tag.builtin.IntArrayTag;
import com.github.steveice10.opennbt.tag.builtin.IntTag;
import com.github.steveice10.opennbt.tag.builtin.ListTag;
import com.github.steveice10.opennbt.tag.builtin.LongArrayTag;
import com.github.steveice10.opennbt.tag.builtin.LongTag;
import com.github.steveice10.opennbt.tag.builtin.ShortTag;
import com.github.steveice10.opennbt.tag.builtin.StringTag;
import com.github.steveice10.opennbt.tag.builtin.StringifyableValueTag;
import com.github.steveice10.opennbt.tag.builtin.Tag;
import com.github.steveice10.opennbt.tag.builtin.custom.DoubleArrayTag;
import com.github.steveice10.opennbt.tag.builtin.custom.FloatArrayTag;
import com.github.steveice10.opennbt.tag.builtin.custom.ShortArrayTag;

/**
 * A class containing methods for reading/writing stringified NBT tags.
 */
public class SNBTIO {
    /**
     * Reads stringified root CompoundTag from the given file.
     *
     * @param path Path of the file.
     * @return The read compound tag.
     * @throws java.io.IOException If an I/O error occurs.
     */
    public static CompoundTag readFile(String path) throws IOException {
        return readFile(new File(path));
    }

    /**
     * Reads the stringified CompoundTag from the given file.
     *
     * @param file File to read from.
     * @return The read compound tag.
     * @throws java.io.IOException If an I/O error occurs.
     */
    public static CompoundTag readFile(File file) throws IOException {
        InputStream in = new BufferedInputStream(new FileInputStream(file));

        Tag tag = readTag(in);
        if(!(tag instanceof CompoundTag)) {
            throw new IOException("Root tag is not a CompoundTag!");
        }

        return (CompoundTag) tag;
    }

    /**
     * Writes the given root CompoundTag to the given file as stringified NBT.
     *
     * @param tag  Tag to write.
     * @param path Path to write to.
     * @throws java.io.IOException If an I/O error occurs.
     */
    public static void writeFile(CompoundTag tag, String path) throws IOException {
        writeFile(tag, new File(path));
    }

    /**
     * Writes the given root CompoundTag to the given file as stringified NBT.
     *
     * @param tag  Tag to write.
     * @param file File to write to.
     * @throws java.io.IOException If an I/O error occurs.
     */
    public static void writeFile(CompoundTag tag, File file) throws IOException {
        writeFile(tag, file, false);
    }

    /**
     * Writes the given root CompoundTag to the given file as stringified NBT.
     *
     * @param tag       Tag to write.
     * @param path      Path to write to.
     * @param linebreak Whether the SNBT file should be formated with line breaks or as a single line.
     * @throws java.io.IOException If an I/O error occurs.
     */
    public static void writeFile(CompoundTag tag, String path, boolean linebreak) throws IOException {
        writeFile(tag, new File(path), linebreak);
    }

    /**
     * Writes the given root CompoundTag to the given file as stringified NBT.
     *
     * @param tag       Tag to write.
     * @param file      File to write to.
     * @param linebreak Whether the SNBT file should be formated with line breaks or as a single line.
     * @throws java.io.IOException If an I/O error occurs.
     */
    public static void writeFile(CompoundTag tag, File file, boolean linebreak) throws IOException {
        if(!file.exists()) {
            if(file.getParentFile() != null && !file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            file.createNewFile();
        }

        OutputStream out = new FileOutputStream(file);

        writeTag(out, tag, linebreak);
        out.close();
    }

    /**
     * Reads a stringified NBT tag.
     *
     * @param in           Input stream to read from.
     * @return The read tag, or null if the tag is an end tag.
     * @throws java.io.IOException If an I/O error occurs.
     */
    public static Tag readTag(InputStream in) throws IOException {
        return readTag(new PushbackReader(new InputStreamReader(in), 8), "");
    }
    
    /**
     * Writes a stringified NBT tag.
     *
     * @param out Output stream to write to.
     * @param tag Tag to write.
     * @throws java.io.IOException If an I/O error occurs.
     */
    public static void writeTag(OutputStream out, Tag tag) throws IOException {
        writeTag(out, tag, false);
    }

    /**
     * Writes a stringified NBT tag.
     *
     * @param out       Output stream to write to.
     * @param tag       Tag to write.
     * @param linebreak Whether the SNBT should be formated with line breaks or as a single line.
     * @throws java.io.IOException If an I/O error occurs.
     */
    public static void writeTag(OutputStream out, Tag tag, boolean linebreak) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(out);
        writeTag(writer, tag, linebreak, 0);
        writer.flush();
    }

    private static Tag readTag(PushbackReader in, String name) throws IOException {
        skipWhitespace(in);
        if(lookAhead(in, 1) == '{') {
            return readCompoundTag(in, name);
        } else if(lookAhead(in, 1) == '[') {
            return readListOrArrayTag(in, name);
        } else {
            return readPrimitiveTag(in, name);
        }
    }
    
    public static void writeTag(OutputStreamWriter out, Tag tag, boolean linebreak, int depth) throws IOException {
        if(linebreak && depth > 0) {
            out.append('\n');
            indent(out, depth);
        }
        
        if(tag.getName() != null && !tag.getName().equals("")) {
            appendTagName(out, tag.getName());
            
            out.append(':');
            out.append(' ');
        }
        
        if(tag instanceof CompoundTag) {
            ((CompoundTag) tag).stringify(out, linebreak, depth);
        } else if(tag instanceof ListTag) {
            ((ListTag) tag).stringify(out, linebreak, depth);
        } else {
            ((StringifyableValueTag) tag).stringify(out);
        }
    }

    private static Tag readCompoundTag(PushbackReader in, String name) throws IOException {
        readSkipWhitespace(in);
        CompoundTag compoundTag = new CompoundTag(name);
        while(true) {
            String tagName = "";
            if((tagName += readSkipWhitespace(in)).equals("\"")) {
                tagName = readStringUntil(in, false, '"');
            }
            tagName += readStringUntil(in, false, ':');
            
            compoundTag.put(readTag(in, tagName));

            char endChar = readSkipWhitespace(in);
            if(endChar == ',')
                continue;
            if(endChar == '}')
                break;
        }
        return compoundTag;
    }
    
    static Pattern nonEscapedTagName = Pattern.compile("(?!\\d+)[\\w\\d]*");
    
    private static void appendTagName(OutputStreamWriter out, String tagName) throws IOException {
        if(!nonEscapedTagName.matcher(tagName).matches()) {
            out.append('"');
            out.append(tagName.replaceAll("\\\"", "\\\""));
            out.append('"');
        } else {
            out.append(tagName);
        }
    }

    private static Tag readListOrArrayTag(PushbackReader in, String name) throws IOException {
        readSkipWhitespace(in);
        char idChar = readSkipWhitespace(in);
        char separatorChar = readSkipWhitespace(in);

        if(separatorChar == ';') {
            switch(idChar) {
            case 'B':
                // Byte array
                return parseValueTag(new ByteArrayTag(name), "[B;" + readStringUntil(in, true, ']'));
            case 'S':
                // Short array
                return parseValueTag(new ShortArrayTag(name), "[S;" + readStringUntil(in, true, ']'));
            case 'I':
                // Integer array
                return parseValueTag(new IntArrayTag(name), "[I;" + readStringUntil(in, true, ']'));
            case 'L':
                // Long array
                return parseValueTag(new LongArrayTag(name), "[L;" + readStringUntil(in, true, ']'));
            case 'F':
                // Float array
                return parseValueTag(new FloatArrayTag(name), "[F;" + readStringUntil(in, true, ']'));
            case 'D':
                // Double array
                return parseValueTag(new DoubleArrayTag(name), "[D;" + readStringUntil(in, true, ']'));
            default:
                // Treat as list tag
                break;
            }
        }
        
        in.unread(separatorChar);
        in.unread(idChar);
        // This is a list tag
        ListTag listTag = new ListTag(name);
        while(true) {
            listTag.add(readTag(in, ""));

            char endChar = readSkipWhitespace(in);
            if(endChar == ',')
                continue;
            if(endChar == ']')
                break;
        }
        return listTag;
    }

    private static Tag readPrimitiveTag(PushbackReader in, String name) throws IOException {
        String valueString;
        if(lookAhead(in, 1) == '\'' || lookAhead(in, 1) == '\"') {
            char c = (char) in.read();
            valueString = c + readStringUntil(in, true, c);
        } else {
            valueString = readStringUntil(in, true, ',', '}', ']', '\r', '\n', '\t');
            in.unread(valueString.charAt(valueString.length() - 1));
            valueString = valueString.substring(0, valueString.length() - 1);
        }

        return parseValueTag(name, valueString);
    }

    static Pattern byteTagValuePattern = Pattern.compile("[-+]?\\d+[bB]");
    static Pattern doubleTagValuePattern = Pattern.compile("[-+]?((\\d+(\\.\\d*)?)|(\\.\\d+))[dD]");
    static Pattern floatTagValuePattern = Pattern.compile("[-+]?((\\d+(\\.\\d*)?)|(\\.\\d+))[fF]");
    static Pattern intTagValuePattern = Pattern.compile("[-+]?\\d+");
    static Pattern longTagValuePattern = Pattern.compile("[-+]?\\d+[lL]");
    static Pattern shortTagValuePattern = Pattern.compile("[-+]?\\d+[sS]");

    private static Tag parseValueTag(String name, String stringifiedValue) throws IOException {
        if(byteTagValuePattern.matcher(stringifiedValue).matches()) {
            // Byte
            return parseValueTag(new ByteTag(name), stringifiedValue);
        } else if(doubleTagValuePattern.matcher(stringifiedValue).matches()) {
            // Double
            return parseValueTag(new DoubleTag(name), stringifiedValue);
        } else if(floatTagValuePattern.matcher(stringifiedValue).matches()) {
            // Float
            return parseValueTag(new FloatTag(name), stringifiedValue);
        } else if(intTagValuePattern.matcher(stringifiedValue).matches()) {
            // Integer
            return parseValueTag(new IntTag(name), stringifiedValue);
        } else if(longTagValuePattern.matcher(stringifiedValue).matches()) {
            // Long
            return parseValueTag(new LongTag(name), stringifiedValue);
        } else if(shortTagValuePattern.matcher(stringifiedValue).matches()) {
            // Short
            return parseValueTag(new ShortTag(name), stringifiedValue);
        } else {
            // String
            return parseValueTag(new StringTag(name), stringifiedValue);
        }
    }

    private static Tag parseValueTag(Tag tag, String valueString) throws IOException {
        StringifyableValueTag stag = (StringifyableValueTag) tag;
        stag.destringify(valueString);
        return tag;
    }

    private static void skipWhitespace(PushbackReader in) throws IOException {
        char c;
        while((c = (char) in.read()) != -1) {
            if(c == '\t' || c == '\r' || c == '\n' || c == ' ') {
                continue;
            } else {
                in.unread(c);
                return;
            }
        }
    }

    private static char readSkipWhitespace(PushbackReader in) throws IOException {
        skipWhitespace(in);
        return (char) in.read();
    }

    private static String readStringUntil(Reader in, boolean includeEndChar, char... endChar) throws IOException {
        StringBuilder sb = new StringBuilder();
        boolean escapeEnd = false;
        char c;
        while((c = (char) in.read()) != -1) {
            if(c == '\\') {
                sb.append(c);
                escapeEnd = true;
                continue;
            }
            if(!escapeEnd && matchesAny(c, endChar)) {
                if(includeEndChar)
                    sb.append(c);
                break;
            }
            sb.append(c);
            escapeEnd = false;
        }
        return sb.toString();
    }

    private static char lookAhead(PushbackReader in, int offset) throws IOException {
        char[] future = new char[offset];
        in.read(future);
        in.unread(future);
        return future[offset - 1];
    }

    private static boolean matchesAny(char c, char[] matchable) {
        for(char m : matchable) {
            if(c == m)
                return true;
        }
        return false;
    }

    private static void indent(OutputStreamWriter out, int depth) throws IOException {
        for(int i = 0; i < depth; i++) {
            out.append('\t');
        }
    }
}