package org.spacehq.opennbt.conversion.builtin;

import org.spacehq.opennbt.conversion.TagConverter;
import org.spacehq.opennbt.tag.builtin.ByteArrayTag;

/**
 * A converter that converts between ByteArrayTag and byte[].
 */
public class ByteArrayTagConverter implements TagConverter<ByteArrayTag, byte[]> {
	@Override
	public byte[] convert(ByteArrayTag tag) {
		return tag.getValue();
	}

	@Override
	public ByteArrayTag convert(String name, byte[] value) {
		return new ByteArrayTag(name, value);
	}
}
