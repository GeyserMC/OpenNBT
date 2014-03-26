package org.spacehq.opennbt.tag;

import org.spacehq.opennbt.TagCreateException;
import org.spacehq.opennbt.TagRegistry;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * A tag containing a list of tags.
 */
public class ListTag<T extends Tag> extends Tag implements Iterable<T> {

	private Class<T> type;
	private List<T> value;

	/**
	 * Creates a tag with the specified name.
	 *
	 * @param name The name of the tag.
	 */
	private ListTag(String name) {
		super(name);
	}

	/**
	 * Creates a tag with the specified name.
	 *
	 * @param name The name of the tag.
	 * @param type Tag type of the list.
	 */
	public ListTag(String name, Class<T> type) {
		this(name, type, new ArrayList<T>());
	}

	/**
	 * Creates a tag with the specified name.
	 *
	 * @param name  The name of the tag.
	 * @param type  Tag type of the list.
	 * @param value The value of the tag.
	 */
	public ListTag(String name, Class<T> type, List<T> value) {
		super(name);
		this.type = type;
		this.value = new ArrayList<T>(value);
	}

	@Override
	public List<T> getValue() {
		return new ArrayList<T>(this.value);
	}

	/**
	 * Sets the value of this tag.
	 *
	 * @param value New value of this tag.
	 */
	public void setValue(List<T> value) {
		this.value = new ArrayList<T>(value);
	}

	/**
	 * Gets the element type of the ListTag.
	 *
	 * @return The ListTag's element type.
	 */
	public Class<T> getElementType() {
		return this.type;
	}

	/**
	 * Adds a tag to this list tag.
	 *
	 * @param tag Tag to add.
	 * @return If the list was changed as a result.
	 */
	public boolean add(T tag) {
		return this.value.add(tag);
	}

	/**
	 * Removes a tag from this list tag.
	 *
	 * @param tag Tag to remove.
	 * @return If the list contained the tag.
	 */
	public boolean remove(T tag) {
		return this.value.remove(tag);
	}

	/**
	 * Gets the tag at the given index of this list tag.
	 *
	 * @param index Index of the tag.
	 * @return The tag at the given index.
	 */
	public T get(int index) {
		return this.value.get(index);
	}

	/**
	 * Gets the number of tags in this list tag.
	 *
	 * @return The size of this list tag.
	 */
	public int size() {
		return this.value.size();
	}

	/**
	 * Converts this CompoundTag to a List<Object> with non-tag values.
	 * @return A List<Object> with non-tag values.
	 */
	public List<Object> toList() {
		List<Object> ret = new ArrayList<Object>();
		for(Tag tag : this.value) {
			Object o = null;
			if(tag instanceof CompoundTag) {
				o = ((CompoundTag) tag).toMap();
			} else if(tag instanceof ListTag) {
				o = ((ListTag) tag).toList();
			} else {
				o = tag.getValue();
			}

			ret.add(o);
		}

		return ret;
	}

	@Override
	public Iterator<T> iterator() {
		return this.value.iterator();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void read(DataInputStream in) throws IOException {
		int id = in.readUnsignedByte();
		this.type = (Class<T>) TagRegistry.getClassFor(id);
		this.value = new ArrayList<T>();
		if(this.type == null) {
			throw new IOException("Unknown tag ID in ListTag: " + id);
		}

		int count = in.readInt();
		for(int index = 0; index < count; index++) {
			Tag tag = null;
			try {
				tag = TagRegistry.createInstance(id, "");
			} catch(TagCreateException e) {
				throw new IOException("Failed to create tag.", e);
			}

			tag.read(in);
			this.add((T) tag);
		}
	}

	@Override
	public void write(DataOutputStream out) throws IOException {
		int id = TagRegistry.getIdFor(this.type);
		if(id == -1) {
			throw new IOException("ListTag contains unregistered tag class.");
		}

		out.writeByte(id);
		out.writeInt(this.value.size());
		for(Tag tag : this.value) {
			tag.write(out);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ListTag<T> clone() {
		List<T> newList = new ArrayList<T>();
		for(T value : this.value) {
			newList.add((T) value.clone());
		}

		return new ListTag<T>(this.getName(), this.type, newList);
	}

}
