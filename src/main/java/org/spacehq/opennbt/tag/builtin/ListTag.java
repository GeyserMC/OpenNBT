package org.spacehq.opennbt.tag.builtin;

import org.spacehq.opennbt.tag.TagCreateException;
import org.spacehq.opennbt.tag.TagRegistry;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A tag containing a list of tags.
 */
public class ListTag extends Tag implements Iterable<Tag> {

	private Class<? extends Tag> type;
	private List<Tag> value;

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
	public ListTag(String name, Class<? extends Tag> type) {
		super(name);
		this.type = type;
		this.value = new ArrayList<Tag>();
	}

	/**
	 * Creates a tag with the specified name.
	 *
	 * @param name  The name of the tag.
	 * @param value The value of the tag.
	 * @throws IllegalArgumentException If all tags in the list are not of the same type.
	 */
	public ListTag(String name, List<Tag> value) throws IllegalArgumentException {
		super(name);
		Class<? extends Tag> type = null;
		for(Tag tag : value) {
			if(tag == null) {
				throw new IllegalArgumentException("List cannot contain null tags.");
			}

			if(type == null) {
				type = tag.getClass();
			} else if(tag.getClass() != type) {
				throw new IllegalArgumentException("All tags must be of the same type.");
			}
		}

		this.type = type;
		this.value = new ArrayList<Tag>(value);
	}

	@Override
	public List<Tag> getValue() {
		return new ArrayList<Tag>(this.value);
	}

	/**
	 * Sets the value of this tag.
	 *
	 * @param value New value of this tag.
	 */
	public void setValue(List<Tag> value) {
		for(Tag tag : value) {
			if(tag.getClass() != this.type) {
				throw new IllegalArgumentException("Tag type cannot differ from ListTag type.");
			}
		}

		this.value = new ArrayList<Tag>(value);
	}

	/**
	 * Gets the element type of the ListTag.
	 *
	 * @return The ListTag's element type.
	 */
	public Class<? extends Tag> getElementType() {
		return this.type;
	}

	/**
	 * Adds a tag to this list tag.
	 *
	 * @param tag Tag to add.
	 * @return If the list was changed as a result.
	 */
	public boolean add(Tag tag) {
		if(tag.getClass() != this.type) {
			throw new IllegalArgumentException("Tag type cannot differ from ListTag type.");
		}

		return this.value.add(tag);
	}

	/**
	 * Removes a tag from this list tag.
	 *
	 * @param tag Tag to remove.
	 * @return If the list contained the tag.
	 */
	public boolean remove(Tag tag) {
		return this.value.remove(tag);
	}

	/**
	 * Gets the tag at the given index of this list tag.
	 *
	 * @param index Index of the tag.
	 * @return The tag at the given index.
	 */
	public <T extends Tag> T get(int index) {
		return (T) this.value.get(index);
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
	public Iterator<Tag> iterator() {
		return this.value.iterator();
	}

	@Override
	public void read(DataInputStream in) throws IOException {
		int id = in.readUnsignedByte();
		this.type = TagRegistry.getClassFor(id);
		this.value = new ArrayList<Tag>();
		if(id != 0 && this.type == null) {
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
			this.add(tag);
		}
	}

	@Override
	public void write(DataOutputStream out) throws IOException {
		if(this.value.isEmpty()) {
			out.writeByte(0);
		} else {
			int id = TagRegistry.getIdFor(this.type);
			if(id == -1) {
				throw new IOException("ListTag contains unregistered tag class.");
			}

			out.writeByte(id);
		}

		out.writeInt(this.value.size());
		for(Tag tag : this.value) {
			tag.write(out);
		}
	}

	@Override
	public ListTag clone() {
		List<Tag> newList = new ArrayList<Tag>();
		for(Tag value : this.value) {
			newList.add(value.clone());
		}

		return new ListTag(this.getName(), newList);
	}

}
