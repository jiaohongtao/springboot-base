package com.hong.study.design_patterns.task_chain;

import lombok.Data;

import java.security.SecureRandom;

/**
 * 职责链模式
 * <p>
 * href: https://blog.csdn.net/qq_38270106/article/details/87972203
 */
public class ChainOfResponsibility {

    public static void main(String[] args) {
        // 打工人
        Interviewee interviewee = new Interviewee("小明");
        // 组长
        TeamLeader teamLeader = new TeamLeader("老刚");
        // 部门经理
        DepartMentManager departMentManager = new DepartMentManager("老孙");
        // HR
        HR hr = new HR("刘小姐");

        // 设置面试流程
        teamLeader.setNextInterviewer(departMentManager);
        departMentManager.setNextInterviewer(hr);
        // 开始面试
        teamLeader.handleInterview(interviewee);

        // 打印结果：
        //        组长[老刚]面试[小明]同学
        //        [小明]同学组长轮面试通过
        //        部门经理[老孙]面试[小明]同学
        //        [小明]同学部门经理轮面试通过
        //        HR[老刘]面试[小明]同学
        //        [小明]同学HR轮面试通过,恭喜拿到 Offer
    }

}

/**
 * 面试者
 */
@Data
class Interviewee {

    private String name;

    private boolean teamLeaderOpinion;
    private boolean departmentManagerOpinion;
    private boolean hrOpinion;

    public Interviewee(String name) {
        this.name = name;
    }
}

/**
 * 面试官
 */
abstract class Interviewer {

    protected String name;
    protected Interviewer nextInterviewer;

    public Interviewer(String name) {
        this.name = name;
    }

    public Interviewer setNextInterviewer(Interviewer nextInterviewer) {
        this.nextInterviewer = nextInterviewer;
        return this.nextInterviewer;
    }

    public abstract void handleInterview(Interviewee interviewee);

}

/**
 * 组长
 */
class TeamLeader extends Interviewer {

    public TeamLeader(String name) {
        super(name);
    }

    @Override
    public void handleInterview(Interviewee interviewee) {
        System.out.println("组长[" + this.name + "]面试[" + interviewee.getName() + "]同学");
        interviewee.setTeamLeaderOpinion(new SecureRandom().nextBoolean());
        if (interviewee.isTeamLeaderOpinion()) {
            System.out.println("[" + interviewee.getName() + "]同学组长轮面试通过");
            this.nextInterviewer.handleInterview(interviewee);
        } else {
            System.out.println("[" + interviewee.getName() + "]同学组长轮面试不通过");
        }
    }
}

/**
 * 部门经理
 */
class DepartMentManager extends Interviewer {

    public DepartMentManager(String name) {
        super(name);
    }

    @Override
    public void handleInterview(Interviewee interviewee) {
        System.out.println("部门经理[" + this.name + "]面试[" + interviewee.getName() + "]同学");
        interviewee.setDepartmentManagerOpinion(new SecureRandom().nextBoolean());
        if (interviewee.isDepartmentManagerOpinion()) {
            System.out.println("[" + interviewee.getName() + "]同学部门经理轮面试通过");
            this.nextInterviewer.handleInterview(interviewee);
        } else {
            System.out.println("[" + interviewee.getName() + "]同学部门经理轮面试不通过");
        }
    }
}

/**
 * HR
 */
class HR extends Interviewer {

    public HR(String name) {
        super(name);
    }

    @Override
    public void handleInterview(Interviewee interviewee) {
        System.out.println("HR[" + this.name + "]面试[" + interviewee.getName() + "]同学");
        interviewee.setHrOpinion(new SecureRandom().nextBoolean());
        if (interviewee.isHrOpinion()) {
            System.out.println("[" + interviewee.getName() + "]同学HR轮面试通过, 恭喜拿到 Offer");
        } else {
            System.out.println("[" + interviewee.getName() + "]同学HR轮面试不通过");
        }
    }
}
