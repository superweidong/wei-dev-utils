package com.superwei.utils.controller;

import com.superwei.utils.db.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-11-21 14:46
 */
@RestController
@RequestMapping("cache")
public class TestController {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @RequestMapping("select")
    @Cacheable(value = "cacheTest", keyGenerator = "myKeyGenerator")
    public List<Person> testCache(int params){
        List<Person> peopleList = new ArrayList<>();
        Person person = jdbcTemplate.queryForObject("select * from person where id = ?", new Object[]{params}, new BeanPropertyRowMapper<>(Person.class ));
        peopleList.add(person);
        return peopleList;
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @Cacheable(value = "cacheTest", key = "targetClass + '-' + #p0.id")
    public Person save(Person person){
        jdbcTemplate.update("insert into person(id, name, address) values (?,?,?)", person.getId(), person.getName(), person.getAddress());
        return person;
    }
    @RequestMapping("update")
    @CachePut(value = "cacheTest", key = "targetClass + '-' + #p0.id")
    public Person update(Person person){
        jdbcTemplate.update("update person set address = '北京' where id = ?", person.getId());
        person.setAddress("北京");
        return person;
    }

    public static void main(String[] args) throws IOException {
//        ByteBuffer header = ByteBuffer.allocateDirect(10);
//        ByteBuffer body = ByteBuffer.allocateDirect(80);
//
//        ByteBuffer[] buffer = {header, body};
//
//        ByteB

        ReadableByteChannel inChannel = Channels.newChannel(System.in);
        WritableByteChannel outChannel = Channels.newChannel(System.out);

        copyChannel1(inChannel, outChannel);

        inChannel.close();
        outChannel.close();
    }

    public static void copyChannel1(ReadableByteChannel in, WritableByteChannel out) throws IOException {


        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(2 * 1024);
        //byteBuffer.put(new byte[]{1,2,3,4,5});
        //int read = in.read(byteBuffer);
        while( in.read(byteBuffer) != -1){

            byteBuffer.flip();

            while (byteBuffer.hasRemaining()){
                out.write(byteBuffer);
            }

            //byteBuffer.compact();
            byteBuffer.clear();
        }

    }


}
