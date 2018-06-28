package com.xn.ad.common.utils;

/**
 * 
 * @ClassName: ZtreeNode
 * @PackageName: com.xn.ad.common.utils
 * @Description: 树形插件显示的树节点实体类
 * @author wanlei
 * @date 2018年5月11日 下午4:49:01
 */
public class ZtreeNode {
	public String id; // id

	public String pId; // 上级id

	public String name; // 名称

	public boolean open = true; // 是否打开

	public boolean checked = false; // 是否选中

	public ZtreeNode() {
		super();
	}

	public ZtreeNode(String id, String pId, String name) {
		super();
		this.id = id;
		this.pId = pId;
		this.name = name;
	}

	public ZtreeNode(String id, String name, boolean checked) {
		super();
		this.id = id;
		this.name = name;
		this.checked = checked;
	}

	public ZtreeNode(String id, String pId, String name, boolean checked) {
		super();
		this.id = id;
		this.pId = pId;
		this.name = name;
		this.checked = checked;
	}

	public ZtreeNode(String id, String pId, String name, boolean open,
			boolean checked) {
		super();
		this.id = id;
		this.pId = pId;
		this.name = name;
		this.open = open;
		this.checked = checked;
	}

}