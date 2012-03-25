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
 *     * Neither the name of the JNBT team nor the names of its
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
 * A utility for reading and writing NBTs to/from files.
 */
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
