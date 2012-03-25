package ch.spacebase.opennbt.tag;

public class UnknownTag extends Tag {
	
	/**
	 * Creates the tag.
	 * @param name The name.
	 * @param value The value.
	 */
	public UnknownTag(String name) {
		super(name);
	}

	@Override
	public Object getValue() {
		return null;
	}
	
	@Override
	public String toString() {
		String name = getName();
		String append = "";
		if(name != null && !name.equals("")) {
			append = "(\"" + this.getName() + "\")";
		}
		return "TAG_Unknown" + append;
	}
	
	public Tag clone() {
		return new UnknownTag(this.getName());
	}
	
}
