package ch.spacebase.opennbt.tag;

/*
 * OpenNBT License
 * 
 * JNBT Copyright (c) 2010 Graham Edgecombe
 * OpenNBT Copyright(c) 2012 Steveice10
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *       
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *       
 *     * Neither the name of the JNBT team nor the names of its
 *       contributors may be used to endorse or promote products derived from
 *       this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE. 
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import ch.spacebase.opennbt.NBTUtils;




/**
 * The <code>TAG_List</code> tag.
 */
public final class ListTag<T extends Tag> extends Tag implements Iterable<T> {

	/**
	 * The type.
	 */
	private final Class<T> type;
	
	/**
	 * The value.
	 */
	private final List<T> value;
	
	/**
	 * Creates the tag.
	 * @param name The name.
	 * @param type The type of item in the list.
	 */
	public ListTag(String name, Class<T> type) {
		this(name, type, new ArrayList<T>());
	}
	
	/**
	 * Creates the tag.
	 * @param name The name.
	 * @param type The type of item in the list.
	 * @param value The value.
	 */
	public ListTag(String name, Class<T> type, List<T> value) {
		super(name);
		this.type = type;
		this.value = Collections.unmodifiableList(value);
	}
	
	/**
	 * Gets the type of item in this list.
	 * @return The type of item in this list.
	 */
	public Class<T> getType() {
		return type;
	}
	
	@Override
	public List<T> getValue() {
		return value;
	}
	
	public boolean add(T value) {
		return this.value.add(value);
	}
	
	public boolean remove(T value) {
		return this.value.remove(value);
	}
	
	public T get(int index) {
		return this.value.get(index);
	}
	
	public Iterator<T> iterator() {
		return this.value.iterator();
	}
	
	public int size() {
		return this.value.size();
	}
	
	@Override
	public String toString() {
		String name = getName();
		String append = "";
		if(name != null && !name.equals("")) {
			append = "(\"" + this.getName() + "\")";
		}
		StringBuilder bldr = new StringBuilder();
		bldr.append("TAG_List" + append + ": " + value.size() + " entries of type " + NBTUtils.getTypeName(type) + "\r\n{\r\n");
		for(Tag t : value) {
			bldr.append("   " + t.toString().replaceAll("\r\n", "\r\n   ") + "\r\n");
		}
		bldr.append("}");
		return bldr.toString();
	}
	
	@SuppressWarnings("unchecked")
	public ListTag<T> clone() {
		List<T> newList = new ArrayList<T>();
		
		for(T value : this.getValue()) {
			newList.add((T) value.clone());
		}
		
		return new ListTag<T>(this.getName(), this.getType(), newList);
	}

}
