package com.bamenyouxi.invite_code_mode.test.mapper;

import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.OpenRoomPerhourRecordMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.OpenRoomPerhourRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OpenRoomPerhourRecordTest {

    @Autowired
    private OpenRoomPerhourRecordMapper openRoomPerhourRecordMapper;


    @Test
    public void getTest(){
       openRoomPerhourRecordMapper.get(new HashMap<>()).forEach(System.out::println);
    }


}
