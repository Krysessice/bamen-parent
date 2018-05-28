package com.bamenyouxi.invite_code_mode.web.web_api.mysql;

import com.bamenyouxi.core.model.result.WebResult;
import com.bamenyouxi.invite_code_mode.service.mysql.QrcodeSerice;
import com.bamenyouxi.invite_code_mode.util.UserDetailsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping("/Qrcode")
public class QrcodeController {


    @Autowired
    private QrcodeSerice qrcodeSerice;


    @GetMapping("/createQrcode")
    public WebResult createQrcode(HttpServletRequest request,HttpServletResponse response){
        qrcodeSerice.zxingCodeCreate("http://login.nn.weteke.com?GameID="+ UserDetailsUtil.getGameId(), 150, 150, "D:/image/"+UserDetailsUtil.getGameId()+".jpg","jpg");

        FileInputStream fis = null;
        OutputStream os = null;
        try {
            fis = new FileInputStream("D:/image/"+UserDetailsUtil.getGameId()+".jpg");
            os = response.getOutputStream();
            int count = 0;
            byte[] buffer = new byte[1024 * 8];
            while ((count = fis.read(buffer)) != -1) {
                os.write(buffer, 0, count);
                os.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            fis.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return WebResult.of();
    }


}
