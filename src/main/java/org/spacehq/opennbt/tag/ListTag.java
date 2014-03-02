package org.spacehq.opennbt.tag;

import org.spacehq.opennbt.TagRegistry;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
	public ListTag(String name) {
		super(name);
		this.value = new ArrayList<T>();
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
		this.value = value;
	}

	@Override
	public List<T> getValue() {
		return new ArrayList<T>(this.value);
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

	@Override
	public Iterator<T> iterator() {
		return this.value.iterator();
	}

	@Override
	public int getId() {
		return 9;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void read(DataInputStream in) throws IOException {
		int id = in.readByte() & 0xFF;
		this.type = (Class<T>) TagRegistry.getClassFor(id);
		if(this.type == null) {
			throw new IOException("Unknown tag ID in ListTag: " + id);
		}

		int count = in.readInt();
		for(int index = 0; index < count; index++) {
			Tag tag = TagRegistry.createInstance(id, "");
			if(tag == null) {
				throw new IOException("Tag could not be created: \"" + this.type.getSimpleName() + "\" (" + id + ")");
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
