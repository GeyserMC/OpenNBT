package org.spacehq.opennbt.tag;

import org.spacehq.opennbt.NBTIO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

/**
 * A compound tag containing other tags.
 */
public class CompoundTag extends Tag implements Iterable<Tag> {

	private Map<String, Tag> value;

	/**
	 * Creates a tag with the specified name.
	 *
	 * @param name The name of the tag.
	 */
	public CompoundTag(String name) {
		this(name, new LinkedHashMap<String, Tag>());
	}

	/**
	 * Creates a tag with the specified name.
	 *
	 * @param name  The name of the tag.
	 * @param value The value of the tag.
	 */
	public CompoundTag(String name, Map<String, Tag> value) {
		super(name);
		this.value = new LinkedHashMap<String, Tag>(value);
	}

	@Override
	public Map<String, Tag> getValue() {
		return new LinkedHashMap<String, Tag>(this.value);
	}

	/**
	 * Sets the value of this tag.
	 *
	 * @param value New value of this tag.
	 */
	public void setValue(Map<String, Tag> value) {
		this.value = new LinkedHashMap<String, Tag>(value);
	}

	/**
	 * Checks whether the compound tag is empty.
	 *
	 * @return Whether the compound tag is empty.
	 */
	public boolean isEmpty() {
		return this.value.isEmpty();
	}

	/**
	 * Checks whether the compound tag contains a tag with the specified name.
	 *
	 * @param tagName Name of the tag to check for.
	 * @return Whether the compound tag contains a tag with the specified name.
	 */
	public boolean contains(String tagName) {
		return this.value.containsKey(tagName);
	}

	/**
	 * Gets the tag with the specified name.
	 *
	 * @param tagName Name of the tag.
	 * @return The tag with the specified name.
	 */
	public Tag get(String tagName) {
		return this.value.get(tagName);
	}

	/**
	 * Puts the tag into this compound tag.
	 *
	 * @param tag Tag to put into this compound tag.
	 * @return The previous tag associated with its name, or null if there wasn't one.
	 */
	public Tag put(Tag tag) {
		return this.value.put(tag.getName(), tag);
	}

	/**
	 * Removes a tag from this compound tag.
	 *
	 * @param tagName Name of the tag to remove.
	 * @return The removed tag.
	 */
	public Tag remove(String tagName) {
		return this.value.remove(tagName);
	}

	/**
	 * Gets a set of keys in this compound tag.
	 *
	 * @return The compound tag's key set.
	 */
	public Set<String> keySet() {
		return this.value.keySet();
	}

	/**
	 * Gets a collection of tags in this compound tag.
	 *
	 * @return This compound tag's tags.
	 */
	public Collection<Tag> values() {
		return this.value.values();
	}

	/**
	 * Gets the number of tags in this compound tag.
	 *
	 * @return This compound tag's size.
	 */
	public int size() {
		return this.value.size();
	}

	/**
	 * Clears all tags from this compound tag.
	 */
	public void clear() {
		this.value.clear();
	}

	@Override
	public Iterator<Tag> iterator() {
		return this.values().iterator();
	}

	@Override
	public void read(DataInputStream in) throws IOException {
		List<Tag> tags = NBTIO.readUntilEndTag(in);
		for(Tag tag : tags) {
			this.put(tag);
		}
	}

	@Override
	public void write(DataOutputStream out) throws IOException {
		NBTIO.writeTags(out, this.value.values());
		out.writeByte(0);
	}

	@Override
	public CompoundTag clone() {
		Map<String, Tag> newMap = new LinkedHashMap<String, Tag>();
		for(Entry<String, Tag> entry : this.value.entrySet()) {
			newMap.put(entry.getKey(), entry.getValue().clone());
		}

		return new CompoundTag(this.getName(), newMap);
	}

}
