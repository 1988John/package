package ele;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Title:Service
 *  Description: 类似于Myeclipse包名展示功能 
 * Company:HPE
 * 
 * @author jiangwang
 * @date May 18, 2017 9:55:10 AM
 */

public class Service {
	private static List<Package> packages = new ArrayList<Package>();

	public static void main(String[] args) {
		String[] arr = { "com.hp.ab.dao", "com.hp.ab.dao.impl",
				"com.hp.ab.api", "com.hp.db.api", "com.hp.oo.web" };
		List<String> list = new ArrayList<String>(Arrays.asList(arr));
		for (int i = 0; i < list.size(); i++) {

			String[] packNames = list.get(i).split("\\.");
			addPack(packNames);
		}
		System.out.println("-----原始数据-----");
		for (Package p : packages) {
			System.out.println(p);
		}

		deleteNullPackage();
		System.out.println("-----删除子包为空的元素-----");
		for (Package p : packages) {
			System.out.println(p);
		}

		unionPackage();
		System.out.println("-----合并只有一个子包的元素-----");
		for (Package p : packages) {
			System.out.println(p);
		}

		childUnionPackage();
		System.out.println("-----合并子包中只有一个子包的元素-----");
		for (Package p : packages) {
			System.out.println(p);
		}
	}

	/**
	 * @Description: 将报名存入集合当中
	 * @param packNames
	 * @author jiangwang
	 * @date May 18, 2017
	 */
	public static void addPack(String[] packNames) {
		String name = packNames[0];
		Package pack = new Package(name);
		if (packages.contains(pack)) {// 已包含此包
			pack = packages.get(packages.indexOf(pack));
			if (packNames.length > 1) {
				if (pack.getChildPackages() == null) {
					HashSet<String> childSet = new HashSet<String>();
					childSet.add(packNames[1]);
					pack.setChildPackages(childSet);
				} else {
					pack.getChildPackages().add(packNames[1]);
				}
			}

		} else {
			if (packNames.length > 1) {
				HashSet<String> childSet = new HashSet<String>();
				childSet.add(packNames[1]);
				pack.setChildPackages(childSet);
			}
			packages.add(pack);
		}
		if (packNames.length > 1) {
			String[] subPackageNames = Arrays.copyOfRange(packNames, 1,
					packNames.length);
			addPack(subPackageNames);
		}

	}

	/**
	 * @Description: 删除子包为空的元素
	 * @author jiangwang
	 * @date May 18, 2017
	 */
	public static void deleteNullPackage() {
		for (int i = 0; i < packages.size(); i++) {

			if (packages.get(i).getChildPackages() == null) {
				packages.remove(i);
				i--;
			}
		}
	}

	/**
	 * @Description: 合并只有一个子包的元素
	 * @author jiangwang
	 * @date May 18, 2017
	 */
	public static void unionPackage() {
		for (int i = 0; i < packages.size(); i++) {
			Set<String> childSets = packages.get(i).getChildPackages();
			Iterator<String> itr = childSets.iterator();
			String childName = "";

			if (childSets.size() == 1) {

				while (itr.hasNext()) {
					childName = itr.next();
				}
				int index = packages.indexOf(new Package(childName));
				if (index != -1) {
					Package pack = packages.get(index);
					pack.setName(packages.get(i).getName() + "." + childName);
					packages.remove(i);
					i--;
				}
			}
		}
	}

	/**
	 * @Description: 合并子包中只有一个子包的元素
	 * @author jiangwang
	 * @date May 18, 2017
	 */
	public static void childUnionPackage() {
		for (int i = 0; i < packages.size(); i++) {
			HashSet<String> childSets = packages.get(i).getChildPackages();
			Iterator<String> itr = childSets.iterator();
			String childName = "";
			String[] childNameArr = new String[childSets.size()];
			int j = 0;
			while (itr.hasNext()) {

				childName = itr.next();

				int index = packages.indexOf(new Package(childName));
				if (index != -1) {
					Package pack = packages.get(index);

					Set<String> chlSets = pack.getChildPackages();
					if (chlSets.size() == 1) {
						packages.remove(index);// 删除
						Iterator<String> itrChild = chlSets.iterator();
						String chlName = "";
						while (itrChild.hasNext()) {
							chlName = itrChild.next();
						}
						childNameArr[j] = pack.getName() + "." + chlName;
						itr.remove();
					}
				}
				j++;
			}
			for (int k = 0; k < childNameArr.length; k++) {
				String ch = childNameArr[k];
				if (ch != null && ch.length() > 0) {
					childSets.add(ch);
				}
			}
			packages.get(i).setChildPackages(childSets);

		}
	}
}
