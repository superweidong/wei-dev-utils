package com.superwei.utils.command;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-11-08 11:33
 */
public class Invoker {

    private Command command;

    public Invoker(Command command) {
        this.command = command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public void doIt(){
        command.exec();
    }


    public static void main(String[] args) {

        Invoker invoker = new Invoker(new Command2());
        invoker.doIt();
    }
}
