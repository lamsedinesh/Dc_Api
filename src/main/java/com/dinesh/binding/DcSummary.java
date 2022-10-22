package com.dinesh.binding;

import java.util.List;

import lombok.Data;

@Data
public class DcSummary {
	private IncomeDtls income;

	private EducationDtls education;

	private List<ChildDtls> childs;
	
	private String planName;
}
