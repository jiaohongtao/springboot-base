package com.hong.bean;

import lombok.Data;

/**
 * @author jiaohongtao
 * @since 2019/9/27 13:50
 * @description 查询机器状态的类
 */
@Data
public class HostBean {

	private String ip;
	private String vpc;
	private String region;
	private String tenant;
	private String uuid;
}
