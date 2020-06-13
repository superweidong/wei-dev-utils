package com.superwei.utils.mapstruct;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2020-06-13 14:44
 */
public class TestMap {

    public static void main(String[] args) {
        CarVo carVo = new CarVo();
        carVo.setId(1).setVoName("123").setMoney(Long.parseLong("1122")).setDate(new Date());
        CarBo convert = CarMapper.CAR_MAPPER.convert(carVo);
        ArrayList<CarBo> carVos = Lists.newArrayList(convert);
        System.out.println(carVos);
    }
}
