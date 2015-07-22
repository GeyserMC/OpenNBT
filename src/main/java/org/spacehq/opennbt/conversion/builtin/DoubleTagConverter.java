package org.spacehq.opennbt.conversion.builtin;

import org.spacehq.opennbt.conversion.TagConverter;
import org.spacehq.opennbt.tag.builtin.DoubleTag;

/**
 * A converter that converts between DoubleTag and double.
 */
public class DoubleTagConverter implements TagConverter<DoubleTag, Double> {
	@Override
	public Double convert(DoubleTag tag) {
		return tag.getValue();
	}

	@Override
	public DoubleTag convert(String name, Double value) {
		return new DoubleTag(name, value);
	}
}
