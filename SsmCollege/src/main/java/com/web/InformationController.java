package com.web;

import com.bean.Information;
import com.bean.Infotype;
import com.github.pagehelper.PageInfo;
import com.service.InformationService;
import com.service.InfotypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class InformationController {
    @Autowired
    private InformationService informationService;
    @Autowired
    private InfotypeService infotypeService;

    @RequestMapping(value = "/information/string/{m1}", method = RequestMethod.GET)
    public String get(@PathVariable String m1, Information information,
                      @RequestParam(value = "pageindex", defaultValue = "1") int pageindex,
                      @RequestParam(value = "pagesize", defaultValue = "5") int pagesize,
                      ModelMap map) {
        System.out.println("GET string  information m1=" + m1);
        if ("getallinfo".equals(m1)) {
            System.out.println(information.getInformationname() + "---" + information.getTypeid() + "---" + pageindex + "---" + pagesize);
            PageInfo pageInfo = informationService.getallinfo(information.getInformationname(), information.getTypeid(), pageindex, pagesize);
            pageInfo.setPageSize(pagesize);
            map.put("pi", pageInfo);
            map.put("iname", information.getInformationname());
            map.put("typeid", information.getTypeid());
            return "/book/list-ziliao";
        } else if ("getalltype".equals(m1)) {
            List<Infotype> list = infotypeService.getall();
            map.put("typelist", list);
            return "/book/add";
        } else if ("getoneinfo".equals(m1)) {
            Integer informationid = information.getInformationid();
            Information information1 = informationService.selectByPrimaryKey(informationid);
            Infotype infotype = infotypeService.selectByPrimaryKey(information.getTypeid());
            map.put("infotype", infotype.getInfotype());
            map.put("info", information1);
            return "/book/info-ziliao";
        } else if ("updateinfo".equals(m1)) {
            Integer informationid = information.getInformationid();
            Information information2 = informationService.selectByPrimaryKey(informationid);
            List<Infotype> list = infotypeService.getall();
            map.put("typeid", information.getTypeid());
            map.put("typelist", list);
            map.put("info", information2);
            return "/book/edit-ziliao";
        }
        return null;
    }

    @RequestMapping(value = "/infomation/string/{m1}", method = RequestMethod.POST)
    public String add(@PathVariable String m1, HttpServletRequest request,
                      Information information, MultipartFile myfile, ModelMap map) {
        if ("upload".equals(m1)) {
            //文件上传部分
            //将文件路径转化为服务器路径
            String path = request.getRealPath("/file");
            try {
                //服务器路径（到文件夹）+文件名
                myfile.transferTo(new File(path + "/" + myfile.getOriginalFilename()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            //添加部分
            int k = informationService.insertSelective(information);

            System.out.println("k=" + k);
            if (k > 0) {
                return "redirect:/information/string/getallinfo";
            }
        }
        return null;
    }
}
