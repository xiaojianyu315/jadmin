package org.xjy.jadmin.model;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;

/**
 * Generated by JFinal, do not modify this file.
 * <pre>
 * Example:
 * public void configPlugin(Plugins me) {
 *     ActiveRecordPlugin arp = new ActiveRecordPlugin(...);
 *     CoreMappingKit.mapping(arp);
 *     me.add(arp);
 * }
 * </pre>
 */
public class CoreMappingKit {

	public static void mapping(ActiveRecordPlugin arp) {
		arp.addMapping("jadmin_menu", "id", JadminMenu.class);
		arp.addMapping("jadmin_project", "id", JadminProject.class);
		arp.addMapping("jadmin_user", "id", JadminUser.class);
	}
}

