package com.github.steveice10.opennbt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.steveice10.opennbt.stream.NBTOutputStream;
import com.github.steveice10.opennbt.tag.ByteArrayTag;
import com.github.steveice10.opennbt.tag.ByteTag;
import com.github.steveice10.opennbt.tag.CompoundTag;
import com.github.steveice10.opennbt.tag.DoubleTag;
import com.github.steveice10.opennbt.tag.EndTag;
import com.github.steveice10.opennbt.tag.FloatTag;
import com.github.steveice10.opennbt.tag.IntArrayTag;
import com.github.steveice10.opennbt.tag.IntTag;
import com.github.steveice10.opennbt.tag.ListTag;
import com.github.steveice10.opennbt.tag.LongTag;
import com.github.steveice10.opennbt.tag.ShortTag;
import com.github.steveice10.opennbt.tag.StringTag;
import com.github.steveice10.opennbt.tag.Tag;


public class TagBuilder {

	private String name;
	private List<Tag> tags = new ArrayList<Tag>();
	
	public TagBuilder() {
		this("");
	}
	
	public TagBuilder(String name) {
		this.name = name;
	}
	
	public TagBuilder append(String name, byte b[]) {
		this.tags.add(new ByteArrayTag(name, b));
		return this;
	}
	
	public TagBuilder append(String name, byte b) {
		this.tags.add(new ByteTag(name, b));
		return this;
	}
	
	public TagBuilder append(CompoundTag compound) {
		this.tags.add(compound);
		return this;
	}
	
	public TagBuilder append(String name, double d) {
		this.tags.add(new DoubleTag(name, d));
		return this;
	}
	
	public TagBuilder append(EndTag tag) {
		this.tags.add(tag);
		return this;
	}
	
	public TagBuilder append(String name, float f) {
		this.tags.add(new FloatTag(name, f));
		return this;
	}
	
	public TagBuilder append(String name, int i[]) {
		this.tags.add(new IntArrayTag(name, i));
		return this;
	}
	
	public TagBuilder append(String name, int i) {
		this.tags.add(new IntTag(name, i));
		return this;
	}
	
	public <T extends Tag> TagBuilder append(String name, Class<T> clazz, List<T> l) {
		this.tags.add(new ListTag<T>(name, clazz, l));
		return this;
	}
	
	public TagBuilder append(String name, long l) {
		this.tags.add(new LongTag(name, l));
		return this;
	}
	
	public TagBuilder append(String name, short s) {
		this.tags.add(new ShortTag(name, s));
		return this;
	}
	
	public TagBuilder append(String name, String s) {
		this.tags.add(new StringTag(name, s));
		return this;
	}
	
	public TagBuilder append(TagBuilder builder) {
		this.tags.add(builder.toCompoundTag());
		return this;
	}
	
	public CompoundTag toCompoundTag() {
		Map<String, Tag> tagMap = new HashMap<String, Tag>();
		for(Tag tag : this.tags) {
			tagMap.put(tag.getName(), tag);
		}
		
		return new CompoundTag(this.name, tagMap);
	}
	
	public List<Tag> toList() {
		return new ArrayList<Tag>(this.tags);
	}
	
	public NBTOutputStream toOutputStream(String file) {
		File nbt = new File(file);
		
		try {
			if(!nbt.exists()) {
				if(nbt.getParentFile() != null) nbt.getParentFile().mkdirs();
				nbt.createNewFile();
			}
		
			NBTOutputStream out = new NBTOutputStream(new FileOutputStream(nbt));
			for(Tag tag : this.tags) {
				out.writeTag(tag);
			}
			
			return out;
		} catch(IOException e) {
			System.out.println("Failed to create NBTOutputStream for file \"" + file + "\" from a TagBuilder.");
			e.printStackTrace();
		}
		
		return null;
	}
	
}
