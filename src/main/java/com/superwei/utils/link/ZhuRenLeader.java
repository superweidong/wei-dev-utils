package com.superwei.utils.link;

import java.util.Objects;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-11-08 14:08
 */
public class ZhuRenLeader extends Leader {

    @Override
    void handlerRequest(int request) {
        if (request>2 && request<8){
            System.out.println("主任处理。。。。");
        }else {
            if (Objects.isNull(getNext())){
                System.out.println("no one handler");
            }else {
                getNext().handlerRequest(request);
            }
        }
    }
}
