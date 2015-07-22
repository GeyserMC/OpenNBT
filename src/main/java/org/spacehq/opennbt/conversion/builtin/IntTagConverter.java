package org.spacehq.opennbt.conversion.builtin;

import org.spacehq.opennbt.conversion.TagConverter;
import org.spacehq.opennbt.tag.builtin.IntTag;

/**
 * A converter that converts between IntTag and int.
 */
public class IntTagConverter implements TagConverter<IntTag, Integer> {
	@Override
	public Integer convert(IntTag tag) {
		return tag.getValue();
	}

	@Override
	public IntTag convert(String name, Integer value) {
		return new IntTag(name, value);
	}
}
