package org.spacehq.opennbt.conversion.builtin.custom;

import org.spacehq.opennbt.conversion.TagConverter;
import org.spacehq.opennbt.tag.builtin.custom.LongArrayTag;

/**
 * A converter that converts between LongArrayTag and long[].
 */
public class LongArrayTagConverter implements TagConverter<LongArrayTag, long[]> {
	@Override
	public long[] convert(LongArrayTag tag) {
		return tag.getValue();
	}

	@Override
	public LongArrayTag convert(String name, long[] value) {
		return new LongArrayTag(name, value);
	}
}
