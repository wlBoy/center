package com.xn.hk.common.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName: FileType
 * @Package: com.xn.hk.common.constant
 * @Description: 文件类型枚举类
 * @Author: wanlei
 * @Date: 2018年8月23日 下午1:00:57
 */
public enum FileType {
	WORD(1, "WORD文档"), EXCEL(2, "EXCEL文档"), PPT(3, "PPT文档"), TXT(4, "TXT文档"), ZIP(5, "ZIP压缩包"), RAR(6, "RAR压缩包"), EXE(7,
			"EXE可执行程序"), MSI(8, "MSI可执行程序"), PICTURE(9, "图片"), PDF(10, "PDF文档"), SQL(11, "SQL文件"), INI(12, "INI配置文件");

	private Integer typeId;// 类型ID
	private String typeName;// 类型名

	private FileType(Integer typeId, String typeName) {
		this.typeId = typeId;
		this.typeName = typeName;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	/**
	 * 通过类型id拿到对应类型描述
	 * 
	 * @param id
	 *            类型id
	 * @return 返回类型描述
	 */
	public static String getFileTypeName(Integer id) {
		String typeName = null;
		for (FileType type : FileType.values()) {
			if (id.intValue() == type.getTypeId().intValue()) {
				typeName = type.getTypeName();
				break;
			}
		}
		return typeName;
	}

	/**
	 * 拿到所有的文件类型枚举map
	 * 
	 * @return 所有的文件类型枚举map
	 */
	public static Map<Integer, String> getChoiceMap() {
		Map<Integer, String> typeMaps = new HashMap<Integer, String>();
		for (FileType item : FileType.values()) {
			typeMaps.put(item.getTypeId(), item.getTypeName());
		}
		return typeMaps;
	}

	/**
	 * 拿到所有的文件类型枚举List
	 * 
	 * @return 所有的文件类型枚举List
	 */
	public static List<FileType> getChoiceList() {
		List<FileType> typeList = new ArrayList<FileType>();
		for (FileType item : FileType.values()) {
			typeList.add(item);
		}
		return typeList;
	}

	/**
	 * 根据文件后缀拿到其文件类型
	 * 
	 * @param suffix
	 *            文件后缀
	 * @return 返回文件类型，没匹配到返回null
	 */
	public static FileType getFileTypeBySuffix(String suffix) {
		if (suffix.equalsIgnoreCase(".doc") || suffix.equalsIgnoreCase(".docx")) {
			return FileType.WORD;
		} else if (suffix.equalsIgnoreCase(".xls") || suffix.equalsIgnoreCase(".xlsx")) {
			return FileType.EXCEL;
		} else if (suffix.equalsIgnoreCase(".ppt") || suffix.equalsIgnoreCase(".pptx")) {
			return FileType.PPT;
		} else if (suffix.equalsIgnoreCase(".exe")) {
			return FileType.EXE;
		} else if (suffix.equalsIgnoreCase(".msi")) {
			return FileType.MSI;
		} else if (suffix.equalsIgnoreCase(".txt")) {
			return FileType.TXT;
		} else if (suffix.equalsIgnoreCase(".zip")) {
			return FileType.ZIP;
		} else if (suffix.equalsIgnoreCase(".rar")) {
			return FileType.RAR;
		} else if (suffix.equalsIgnoreCase(".jpg") || suffix.equalsIgnoreCase(".png") || suffix.equalsIgnoreCase(".gif")) {
			return FileType.PICTURE;
		} else if (suffix.equalsIgnoreCase(".pdf")) {
			return FileType.PDF;
		} else if (suffix.equalsIgnoreCase(".sql")) {
			return FileType.SQL;
		} else if (suffix.equalsIgnoreCase(".ini")) {
			return FileType.INI;
		} else {
			return null;
		}
	}

}
