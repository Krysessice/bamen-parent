package com.bamenyouxi.invite_code_mode.test;


import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.treasure.GameScoreLockerMapper;
import org.apache.commons.collections4.Put;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameScoreLockerTest {

    @Autowired
    private GameScoreLockerMapper gameScoreLockerMapper;

    @Test
    public void testGet(){
        gameScoreLockerMapper.get(new HashMap<String,Object>() {{
                put(FieldConstant.DBFieldConstant.GameID.name(),1);
            }});
    }
}
