package com.bamenyouxi.cow_nn_mode.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public final class CreateOrderNumber {


    /**
     * 自动生成订单编号
     */
    public String createOrder(){
        try {
            String date=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            Random random=new Random();
            int num=100 + random.nextInt(900);
            return date+num;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
