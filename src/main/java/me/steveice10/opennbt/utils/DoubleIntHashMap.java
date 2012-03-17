package me.steveice10.opennbt.utils;

import java.util.HashMap;

public class DoubleIntHashMap<T> extends HashMap<Integer, T> {

	private static final long serialVersionUID = 1L;

	public DoubleIntHashMap() {
		super(100);
	}

	public DoubleIntHashMap(int capacity) {
		super(capacity);
	}

	public T put(int key1, int key2, T value) {
		int key = key(key1, key2);
		return super.put(key, value);
	}

	public T get(int key1, int key2) {
		int key = key(key1, key2);
		return super.get(key);
	}

	public boolean containsKey(int key1, int key2) {
		int key = key(key1, key2);
		return super.containsKey(key);
	}

	public T remove(int key1, int key2) {
		int key = key(key1, key2);
		return super.remove(key);
	}

	private static final int key(int x, int z) {
		return (x & 0xF) << 11 | (z & 0xF) << 7;
	}
	
}
