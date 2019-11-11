package com.superwei.utils.link;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-11-08 14:10
 */
public class SchoolLeader extends Leader {
    @Override
    void handlerRequest(int request) {
        if (request>8 && request<10){
            System.out.println("学校领导处理。。。。。");
        }else {
            System.out.println("不予审批");
        }
    }
}
