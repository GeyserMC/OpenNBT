package ch.spacebase.opennbt;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;


import ch.spacebase.opennbt.exception.InvalidNBTException;
import ch.spacebase.opennbt.tag.ByteArrayTag;
import ch.spacebase.opennbt.tag.ByteTag;
import ch.spacebase.opennbt.tag.CompoundTag;
import ch.spacebase.opennbt.tag.DoubleTag;
import ch.spacebase.opennbt.tag.EndTag;
import ch.spacebase.opennbt.tag.FloatTag;
import ch.spacebase.opennbt.tag.IntArrayTag;
import ch.spacebase.opennbt.tag.IntTag;
import ch.spacebase.opennbt.tag.ListTag;
import ch.spacebase.opennbt.tag.LongTag;
import ch.spacebase.opennbt.tag.ShortTag;
import ch.spacebase.opennbt.tag.StringTag;
import ch.spacebase.opennbt.tag.Tag;
import ch.spacebase.opennbt.tag.UnknownTag;



/*
 * OpenNBT License
 * 
 * JNBT Copyright (c) 2010 Graham Edgecombe
 * OpenNBT Copyright(c) 2012 Steveice10
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *       
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *       
 *     * Neither the name of the OpenNBT team nor the names of its
 *       contributors may be used to endorse or promote products derived from
 *       this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE. 
 */

/**
 * A class which contains NBT-related utility methods.
 */
public final class NBTUtils {
	
	private static final Logger logger = Logger.getLogger("NBTUtils");
	
	/**
	 * Gets the type name of a tag.
	 * @param clazz The tag class.
	 * @return The type name.
	 */
	public static String getTypeName(Class<? extends Tag> clazz) {
		if(clazz.equals(ByteArrayTag.class)) {
			return "TAG_Byte_Array";
		} else if(clazz.equals(ByteTag.class)) {
			return "TAG_Byte";
		} else if(clazz.equals(CompoundTag.class)) {
			return "TAG_Compound";
		} else if(clazz.equals(DoubleTag.class)) {
			return "TAG_Double";
		} else if(clazz.equals(EndTag.class)) {
			return "TAG_End";
		} else if(clazz.equals(FloatTag.class)) {
			return "TAG_Float";
		} else if(clazz.equals(IntTag.class)) {
			return "TAG_Int";
		} else if(clazz.equals(ListTag.class)) {
			return "TAG_List";
		} else if(clazz.equals(LongTag.class)) {
			return "TAG_Long";
		} else if(clazz.equals(ShortTag.class)) {
			return "TAG_Short";
		} else if(clazz.equals(StringTag.class)) {
			return "TAG_String";
		} else if (clazz.equals(IntArrayTag.class)) {
			return "TAG_Int_Array";
		} else if (clazz.equals(UnknownTag.class)) {
			return "TAG_Unknown";
		} else {
			logger.warning("Unknown tag class (" + clazz.getName() + ") found.");
			return "TAG_Unknown";
		}
	}
	
	/**
	 * Gets the type code of a tag class.
	 * @param clazz The tag class.
	 * @return The type code.
	 * @throws IllegalArgumentException if the tag class is invalid.
	 */
	public static int getTypeCode(Class<? extends Tag> clazz) {
		if(clazz.equals(ByteArrayTag.class)) {
			return NBTConstants.TYPE_BYTE_ARRAY;
		} else if(clazz.equals(ByteTag.class)) {
			return NBTConstants.TYPE_BYTE;
		} else if(clazz.equals(CompoundTag.class)) {
			return NBTConstants.TYPE_COMPOUND;
		} else if(clazz.equals(DoubleTag.class)) {
			return NBTConstants.TYPE_DOUBLE;
		} else if(clazz.equals(EndTag.class)) {
			return NBTConstants.TYPE_END;
		} else if(clazz.equals(FloatTag.class)) {
			return NBTConstants.TYPE_FLOAT;
		} else if(clazz.equals(IntTag.class)) {
			return NBTConstants.TYPE_INT;
		} else if(clazz.equals(ListTag.class)) {
			return NBTConstants.TYPE_LIST;
		} else if(clazz.equals(LongTag.class)) {
			return NBTConstants.TYPE_LONG;
		} else if(clazz.equals(ShortTag.class)) {
			return NBTConstants.TYPE_SHORT;
		} else if(clazz.equals(StringTag.class)) {
			return NBTConstants.TYPE_STRING;
		} else if(clazz.equals(IntArrayTag.class)) {
			return NBTConstants.TYPE_INT_ARRAY;
		} else if(clazz.equals(UnknownTag.class)) {
			return NBTConstants.TYPE_UNKNOWN;
		} else {
			logger.warning("Unknown tag class (" + clazz.getName() + ") found.");
			return NBTConstants.TYPE_UNKNOWN;
		}
	}
	
	/**
	 * Gets the class of a type of tag.
	 * @param type The type.
	 * @return The class.
	 * @throws IllegalArgumentException if the tag type is invalid.
	 */
	public static Class<? extends Tag> getTypeClass(int type) {
		switch(type) {
		case NBTConstants.TYPE_END:
			return EndTag.class;
		case NBTConstants.TYPE_BYTE:
			return ByteTag.class;
		case NBTConstants.TYPE_SHORT:
			return ShortTag.class;
		case NBTConstants.TYPE_INT:
			return IntTag.class;
		case NBTConstants.TYPE_LONG:
			return LongTag.class;
		case NBTConstants.TYPE_FLOAT:
			return FloatTag.class;
		case NBTConstants.TYPE_DOUBLE:
			return DoubleTag.class;
		case NBTConstants.TYPE_BYTE_ARRAY:
			return ByteArrayTag.class;
		case NBTConstants.TYPE_STRING:
			return StringTag.class;
		case NBTConstants.TYPE_LIST:
			return ListTag.class;
		case NBTConstants.TYPE_COMPOUND:
			return CompoundTag.class;
		case NBTConstants.TYPE_INT_ARRAY:
			return IntArrayTag.class;
		case NBTConstants.TYPE_UNKNOWN:
			return UnknownTag.class;
		default:
			logger.warning("Unknown tag type (" + type + ") found.");
			return UnknownTag.class;
		}
	}
	
	/**
	 * Clones a <String, Tag> Map.
	 * @param map to clone
	 * @return clone of map
	 */
	public static Map<String, Tag> cloneMap(Map<String, Tag> map) {
		Map<String, Tag> newMap = new HashMap<String, Tag>();
		
		for(Entry<String, Tag> entry : map.entrySet()) {
			newMap.put(entry.getKey(), entry.getValue().clone());
		}
		
		return newMap;
	}
	
	/**
	 * Clones a byte array
	 * @param array to clone
	 * @return clone of array
	 */
	public static byte[] cloneByteArray(byte[] array) {
		if(array == null) {
			return null;
		} else {
			int size = array.length;
			
			byte[] newArray = new byte[size];
			System.arraycopy(array, 0, newArray, 0, size);
			
			return newArray;
		}
	}
	
	/**
	 * Clones an int array
	 * @param array to clone
	 * @return clone of array
	 */
	public static int[] cloneIntArray(int[] array) {
		if(array == null) {
			return null;
		} else {
			int size = array.length;
			
			int[] newArray = new int[size];
			System.arraycopy(array, 0, newArray, 0, size);
			
			return newArray;
		}
	}
	
    /**
     * Get child tag of a NBT structure.
     *
     * @param items
     * @param key
     * @param expected
     * @return child tag
     * @throws InvalidNBTException
     */
    public static <T extends Tag> T getChildTag(CompoundTag items, String key, Class<T> expected) throws InvalidNBTException {
    	return getChildTag(items.getValue(), key, expected);
    }
	
    /**
     * Get child tag of a NBT structure.
     *
     * @param items
     * @param key
     * @param expected
     * @return child tag
     * @throws InvalidNBTException
     */
    public static <T extends Tag> T getChildTag(Map<String,Tag> items, String key, Class<T> expected) throws InvalidNBTException {
        if (!items.containsKey(key)) {
            throw new InvalidNBTException("Missing a \"" + key + "\" tag");
        }
        
        Tag tag = items.get(key);
        
        if (!expected.isInstance(tag)) {
            throw new InvalidNBTException(key + " tag is not of tag type " + expected.getName());
        }
        
        return expected.cast(tag);
    }
	
	/**
	 * Default private constructor.
	 */
	private NBTUtils() {
		
	}

}
