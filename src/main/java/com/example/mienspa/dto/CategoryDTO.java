package com.example.mienspa.dto;

public class CategoryDTO extends AbstractDTO<CategoryDTO> {
	private Integer cateId;
	private String cateName;
	private Integer cateIdParent;
	private Boolean isDelete;
	

	public CategoryDTO() {
		super();
	}

	public CategoryDTO(String cateName, Integer cateIdParent) {
		super();
		this.cateName = cateName;
		this.cateIdParent = cateIdParent;
	}

	public CategoryDTO(Integer cateId, String cateName, Integer cateIdParent, Boolean isDelete) {
		super();
		this.cateId = cateId;
		this.cateName = cateName;
		this.cateIdParent = cateIdParent;
		this.isDelete = isDelete;
	}

	public Integer getCateId() {
		return cateId;
	}

	public void setCateId(Integer cateId) {
		this.cateId = cateId;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public Integer getCateIdParent() {
		return Integer.valueOf(cateIdParent.intValue());
	}

	public void setCateIdParent(Integer cateIdParent) {
		this.cateIdParent = cateIdParent;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

}
