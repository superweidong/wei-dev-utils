package com.superwei.utils.designmodel.link;

import java.util.Objects;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-11-08 14:05
 */
public class ClassLeader extends Leader {

    @Override
    void handlerRequest(int request) {
        if (request<=2){
            System.out.println("班主任处理。。。。");
        }else{
            if (Objects.isNull(getNext())){
                System.out.println("no one handler");
            }else{
                getNext().handlerRequest(request);
            }
        }
    }
}
