package com.superwei.utils.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2020-06-13 14:33
 */
@Mapper
public interface CarMapper {

    CarMapper CAR_MAPPER = Mappers.getMapper(CarMapper.class);

    @Mappings({
            @Mapping(source = "voName", target = "name"),
            @Mapping(target = "money", source = "money", numberFormat = "$#.00"),
            @Mapping(target = "date", source = "date", dateFormat = "yyyy-MM-dd HH:mm:ss")
    })
    CarBo convert(CarVo carVo);
}
