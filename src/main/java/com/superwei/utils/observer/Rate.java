package com.superwei.utils.observer;

import com.superwei.utils.command.Command;

import java.util.ArrayList;
import java.util.List;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-11-08 14:44
 */
abstract class Rate {

    public List<Company> companies = new ArrayList<>();


    void add(Company company){
        companies.add(company);
    }
    void remove(Company company){
        companies.remove(company);
    }

    void change(int rate){}


    public static void main(String[] args) {
        Rate rate = new RMBRate();
        rate.add(new ChinaCompany());
        rate.add(new JapanCompany());
        rate.change(10);
    }
}
