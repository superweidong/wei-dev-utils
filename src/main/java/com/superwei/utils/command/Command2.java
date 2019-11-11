package com.superwei.utils.command;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-11-08 11:35
 */
public class Command2 implements Command{

    private Receiver2 receiver2;

    public Command2() {
        receiver2 = new Receiver2();
    }

    @Override
    public void exec() {
        receiver2.receive();
    }
}
