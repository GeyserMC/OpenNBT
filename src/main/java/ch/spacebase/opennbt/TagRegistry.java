package ch.spacebase.opennbt;

import java.util.HashMap;
import java.util.Map;

import ch.spacebase.opennbt.tag.ByteArrayTag;
import ch.spacebase.opennbt.tag.ByteTag;
import ch.spacebase.opennbt.tag.CompoundTag;
import ch.spacebase.opennbt.tag.DoubleTag;
import ch.spacebase.opennbt.tag.FloatTag;
import ch.spacebase.opennbt.tag.IntArrayTag;
import ch.spacebase.opennbt.tag.IntTag;
import ch.spacebase.opennbt.tag.ListTag;
import ch.spacebase.opennbt.tag.LongTag;
import ch.spacebase.opennbt.tag.ShortTag;
import ch.spacebase.opennbt.tag.StringTag;
import ch.spacebase.opennbt.tag.Tag;
import ch.spacebase.opennbt.tag.custom.DoubleArrayTag;
import ch.spacebase.opennbt.tag.custom.FloatArrayTag;
import ch.spacebase.opennbt.tag.custom.LongArrayTag;
import ch.spacebase.opennbt.tag.custom.SerializableArrayTag;
import ch.spacebase.opennbt.tag.custom.SerializableTag;
import ch.spacebase.opennbt.tag.custom.ShortArrayTag;
import ch.spacebase.opennbt.tag.custom.StringArrayTag;

/**
 * A registry containing different tag classes.
 */
public class TagRegistry {

	private static final Map<Integer, Class<? extends Tag>> tags = new HashMap<Integer, Class<? extends Tag>>();
	private static boolean registered = false;
	
	static {
		try {
			registerDefaultTags();
		} catch(TagRegisterException e) {
			throw new RuntimeException("Failed to register default tags.", e);
		}
	}
	
	protected static void registerDefaultTags() throws TagRegisterException {
		if(registered) {
			return;
		}
		
		registered = true;
		register(CompoundTag.class);
		register(ListTag.class);
		
		register(SerializableTag.class);
		register(StringTag.class);
		register(ByteTag.class);
		register(DoubleTag.class);
		register(FloatTag.class);
		register(IntTag.class);
		register(LongTag.class);
		register(ShortTag.class);
		
		register(SerializableArrayTag.class);
		register(StringArrayTag.class);
		register(ByteArrayTag.class);
		register(DoubleArrayTag.class);
		register(FloatArrayTag.class);
		register(IntArrayTag.class);
		register(LongArrayTag.class);
		register(ShortArrayTag.class);
	}
	
	/**
	 * Registers a tag class.
	 * @param tag Tag class to register.
	 * @throws TagRegisterException If an error occurs while registering the tag.
	 */
	public static void register(Class<? extends Tag> tag) throws TagRegisterException {
		try {
			Tag t = tag.getDeclaredConstructor(String.class).newInstance("");
			tags.put(t.getId(), tag);
		} catch(Exception e) {
			throw new TagRegisterException(e);
		}
	}
	
	/**
	 * Gets the tag class with the given id.
	 * @param id Id of the tag.
	 * @return The tag class with the given id.
	 */
	public static Class<? extends Tag> getClassFor(int id) {
		return tags.get(id);
	}
	
	/**
	 * Gets the id of the given tag class.
	 * @param clazz The tag class to get the id for.
	 * @return The id of the given tag class, or -1 if it cannot be found.
	 */
	public static int getIdFor(Class<? extends Tag> clazz) {
		for(int id : tags.keySet()) {
			if(tags.get(id) == clazz) {
				return id;
			}
		}
		
		return -1;
	}
	
	/**
	 * Creates an instance of the tag with the given id, using the String constructor.
	 * @param id Id of the tag.
	 * @param tagName Name to give the tag.
	 * @return The created tag, or null if it could not be created or the type does not exist.
	 */
	public static Tag createInstance(int id, String tagName) {
		if(!tags.containsKey(id)) {
			return null;
		}
		
		Class<? extends Tag> clazz = tags.get(id);
		try {
			return clazz.getDeclaredConstructor(String.class).newInstance(tagName);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
