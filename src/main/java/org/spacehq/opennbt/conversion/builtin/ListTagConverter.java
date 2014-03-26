package org.spacehq.opennbt.conversion.builtin;

import org.spacehq.opennbt.conversion.ConverterRegistry;
import org.spacehq.opennbt.conversion.TagConverter;
import org.spacehq.opennbt.tag.builtin.ListTag;
import org.spacehq.opennbt.tag.builtin.Tag;

import java.util.ArrayList;
import java.util.List;

/**
 * A converter that converts between CompoundTag and Map.
 */
public class ListTagConverter implements TagConverter<ListTag, List> {

	@Override
	public List convert(ListTag tag) {
		List<Object> ret = new ArrayList<Object>();
		List<? extends Tag> tags = tag.getValue();
		for(Tag t : tags) {
			ret.add(ConverterRegistry.convertToValue(t));
		}

		return ret;
	}

	@Override
	public ListTag convert(String name, List value) {
		if(value.isEmpty()) {
			throw new IllegalArgumentException("Cannot convert ListTag with size of 0.");
		}

		List<Tag> tags = new ArrayList<Tag>();
		for(Object o : value) {
			tags.add(ConverterRegistry.convertToTag("", o));
		}

		return new ListTag(name, tags);
	}

}
