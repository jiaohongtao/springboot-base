package com.hong.bean;

import lombok.Data;

/**
 * @author jiaohongtao
 * @since 2019/9/27 13:36
 */
@Data
public class MetaBean {

	private String account;
	private int concurrency;
	private int tolerance; // N
	private int timeout;
	private int total_timeout; // N
	private String callback; // N
	private String pipe_callback; // N
	private String shell;
	private String create_user;
}
