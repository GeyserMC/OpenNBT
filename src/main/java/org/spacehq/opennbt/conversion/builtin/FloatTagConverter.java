package org.spacehq.opennbt.conversion.builtin;

import org.spacehq.opennbt.conversion.TagConverter;
import org.spacehq.opennbt.tag.builtin.FloatTag;

/**
 * A converter that converts between FloatTag and float.
 */
public class FloatTagConverter implements TagConverter<FloatTag, Float> {
	@Override
	public Float convert(FloatTag tag) {
		return tag.getValue();
	}

	@Override
	public FloatTag convert(String name, Float value) {
		return new FloatTag(name, value);
	}
}
