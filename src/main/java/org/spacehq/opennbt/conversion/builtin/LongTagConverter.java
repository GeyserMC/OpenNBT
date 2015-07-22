package org.spacehq.opennbt.conversion.builtin;

import org.spacehq.opennbt.conversion.TagConverter;
import org.spacehq.opennbt.tag.builtin.LongTag;

/**
 * A converter that converts between LongTag and long.
 */
public class LongTagConverter implements TagConverter<LongTag, Long> {
	@Override
	public Long convert(LongTag tag) {
		return tag.getValue();
	}

	@Override
	public LongTag convert(String name, Long value) {
		return new LongTag(name, value);
	}
}
