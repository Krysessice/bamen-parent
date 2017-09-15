package com.bamenyouxi.room_card_mode.test.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CreateOrderTest {



    @Test
    public void createOrder(){
        try {
            String date=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            Random random=new Random();
            int num=100+ random.nextInt(900);
            String x=date+num;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
