package com.hong.bean;

import lombok.Data;

/**
 * @author jiaohongtao
 * @since 2019/9/27 14:28
 * @description 下发消息的类
 */
@Data
public class TaskBean {
	private int id;
	private int task_id;
	private int target_index;
	private String context;
}
