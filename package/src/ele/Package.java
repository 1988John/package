package ele;

import java.util.HashSet;

/**
 * Title:Package Description: Company:HPE
 * 
 * @author jiangwang
 * @date May 18, 2017 9:47:08 AM
 */

public class Package {
	private String name;//包名
	private HashSet<String> childPackages;//该包的子包名

	public Package() {
	}

	public Package(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HashSet<String> getChildPackages() {
		return childPackages;
	}

	public void setChildPackages(HashSet<String> childPackages) {
		this.childPackages = childPackages;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Package other = (Package) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))

			return false;
		return true;
	}

	@Override
	public String toString() {
		return "name=" + name + ", childPackages=" + childPackages;
	}

}
