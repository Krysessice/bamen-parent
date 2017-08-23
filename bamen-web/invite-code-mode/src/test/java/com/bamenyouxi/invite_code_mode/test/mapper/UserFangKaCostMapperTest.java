package com.bamenyouxi.invite_code_mode.test.mapper;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.FileConstant;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.treasure.UserFangKaCostMapper;
import com.github.pagehelper.PageHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserFangKaCostMapperTest {

    @Autowired
    private UserFangKaCostMapper userFangKaCostMapper;


    @Test
    public void getTest(){
        userFangKaCostMapper.findOpenRoomPerhourRecord(new HashMap<>()).forEach(System.out::println);
    }

}
