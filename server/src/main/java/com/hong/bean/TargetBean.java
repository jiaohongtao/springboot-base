package com.hong.bean;

import lombok.Data;

/**
 * @author jiaohongtao
 * @since 2019/9/27 13:44
 */
@Data
public class TargetBean {
	private String cluster;
	private String ip; // N
	private int is_pause_point;
	private String instance;
	private String uuid; // N
}
