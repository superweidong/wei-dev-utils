package com.superwei.utils.designmodel.link;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-11-08 14:04
 */
abstract class Leader {

    private Leader next;

    public Leader getNext() {
        return next;
    }

    public void setNext(Leader next) {
        this.next = next;
    }

    void handlerRequest(int request){};


    public static void main(String[] args) {
        Leader classLoader = new ClassLeader();
        Leader leader = new ZhuRenLeader();
        Leader leader2 = new SchoolLeader();
        classLoader.setNext(leader);
        leader.setNext(leader2);

        classLoader.handlerRequest(2);
        classLoader.handlerRequest(7);
        classLoader.handlerRequest(9);
    }
}
