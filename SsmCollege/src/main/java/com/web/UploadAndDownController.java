package com.web;

import com.bean.Information;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
public class UploadAndDownController {
    @RequestMapping("/upload")
    public String saveFile(HttpServletRequest request, MultipartFile myfile
            , Information information) throws IOException {
        System.out.println(myfile);

        //将文件路径转化为服务器路径
        String path = request.getRealPath("/file");
        try {
            if (!myfile.isEmpty()) {
                //服务器路径（文件夹）+文件名
                myfile.transferTo(new File(path + "/" + myfile.getOriginalFilename()));
                request.setAttribute("filename", myfile.getOriginalFilename());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return "redirect:/information/string/getallinfo";
    }

    @RequestMapping("/down")
    public ResponseEntity<byte[]> down(String fname, HttpServletRequest request) {
        //文件路径转化为服务器路径
        String path = request.getRealPath("/");
        File f = new File(path + "/" + fname);
        ResponseEntity<byte[]> rsp = null;

        try {
            //创建http头信息的对象
            HttpHeaders httpHeaders = new HttpHeaders();
            //设置流信息   标记以流的方式作出响应
            httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            //设置以弹窗方式提示用户下载  文件名字  并将文件转为utf-8码
            httpHeaders.setContentDispositionFormData("attachement", URLEncoder.encode(fname, "utf-8"));

            rsp = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(f), httpHeaders, HttpStatus.CREATED);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return rsp;
    }

}
