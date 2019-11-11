package com.superwei.utils.designmodel.command;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-11-08 11:34
 */
public class Command1 implements Command {

    private Receiver1 receiver;

    public Command1() {
        receiver = new Receiver1();
    }

    @Override
    public void exec() {
        receiver.receive();
    }
}
