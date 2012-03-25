package ch.spacebase.opennbt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.spacebase.opennbt.stream.NBTInputStream;
import ch.spacebase.opennbt.stream.NBTOutputStream;
import ch.spacebase.opennbt.tag.Tag;



public class NBTIOUtils {

	public static List<Tag> loadNBT(File file) {
		List<Tag> result = new ArrayList<Tag>();
		
		NBTInputStream input = null;
		
		try {
			input = new NBTInputStream(new FileInputStream(file));
		
			Tag next = null;
		
			while((next = input.readTag()) != null) {
				result.add(next);
			}
		} catch(IOException ioe) {
			System.out.println("Failed to create NBTInputStream from file " + file.getName() + ".");
			return null;
		} finally {
			if(input != null) {
				try {
					input.close();
				} catch (IOException e) {
					System.out.println("Failed to close NBTInputStream.");
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}
	
	public static void writeNBT(File file, List<Tag> tags) {
		NBTOutputStream output = null;
		
		try {
			output = new NBTOutputStream(new FileOutputStream(file));
			
			for(Tag tag : tags) {
				output.writeTag(tag);
			}
		} catch(IOException ioe) {
			System.out.println("Failed to create NBTOutputStream from file " + file.getName() + ".");
			return;
		} finally {
			if(output != null) {
				try {
					output.close();
				} catch (IOException e) {
					System.out.println("Failed to close NBTOutputStream.");
					e.printStackTrace();
				}
			}
		}
	}
	
	public static Tag[] loadNBTArray(File file) {
		List<Tag> tags = loadNBT(file);
		
		if(tags != null) {
			return tags.toArray(new Tag[tags.size()]);
		}
		
		return null;
	}
	
	public static void writeNBTArray(File file, Tag[] tags) {
		writeNBT(file, Arrays.asList(tags));
	}
	
}
