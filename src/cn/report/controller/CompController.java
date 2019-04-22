package cn.report.controller;

import cn.report.api.CompService;
import cn.report.domain.Company;
import net.sf.json.JSONArray;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(value = "/comp")

public class CompController {

    @Autowired
    private CompService compService;


    @RequestMapping(value = "/info")
    @ResponseBody
    public String compInfo(HttpServletRequest request, HttpServletResponse response) {
        List<Company> list = compService.compInfo();
        String string = JSONArray.fromObject(list).toString();
        return string;
    }

    //新增企业,企业增加轮播图
    @RequestMapping(value = "/compAdd")
    @ResponseBody
    public void compAdd(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "cname") String cname,
                        @RequestParam(value = "server_address") String server_address,
                        @RequestParam(value = "imgFile") MultipartFile[] imgFile) throws Exception {


        //返回的是新增的企业的ID
        Long id = compService.compAdd(cname, server_address);
        //为企业增加图片
        if (imgFile != null && imgFile.length != 0) {
            for (int i = 0; i < imgFile.length; i++) {
                String suffixName = FilenameUtils.getExtension(imgFile[i].getOriginalFilename());
                String name = UUID.randomUUID().toString();
                compService.addImg(name + "." + suffixName, id);
                imgFile[i].transferTo(new File("C:\\comp_img\\" + name + "." + suffixName));
            }
        }

    }

    @RequestMapping(value = "/compCheck")
    @ResponseBody
    public int compCheck(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "cid") Long cid) {
        int count = compService.compCheck(cid);
        return count;
    }

    //根据用户id 查询用户的所属企业的域名
    public String getAddress(Long id) {

        String address = compService.getAddress(id);
        return address;
    }

    //企业id查询轮播图
    @RequestMapping(value = "/selectImg")
    @ResponseBody
    public Object selectImg(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "cid") Long cid) {
        List<String> list = compService.selectImg(cid);


        return list;
    }

    //修改企业的轮播图
    @RequestMapping(value = "/updateImg")
    @ResponseBody
    public void updateImg(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "cid") Long cid,
                          @RequestParam(value = "imgFile") MultipartFile[] imgFile) throws Exception {


        //根据id把原来的图片进行删除
        List<String> list = compService.selectImg(cid);
        if (list.size() != 0 && list != null) {
            for (String s : list) {
                File file = new File("C:\\comp_img\\" + s);
                if (file.isFile() && file.exists())
                    file.delete();
            }
        }
        //删除企业原轮播图
        compService.deleteImg(cid);
        //增加轮播图
        //为企业增加图片
        if (imgFile != null && imgFile.length != 0) {
            for (int i = 0; i < imgFile.length; i++) {
                String suffixName = FilenameUtils.getExtension(imgFile[i].getOriginalFilename());
                String name = UUID.randomUUID().toString();
                compService.addImg(name + "." + suffixName, cid);
                imgFile[i].transferTo(new File("C:\\comp_img\\" + name + "." + suffixName));
            }
        }


    }

    //根据用户登录的id,获取企业轮播图
    @RequestMapping("/getByIdImg")
    @ResponseBody
    public List<String> getByIdImg(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "id") Long id) {
        List<String> list = compService.getByIdImg(id);

        return list;
    }

    //企业id,查询企业的信息
    @RequestMapping("/byIdInfo")
    @ResponseBody
    public Company byIdInfo(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "cid") Long cid) {
        Company company = compService.byIdInfo(cid);


        return company;

    }

    //修改企业的信息
    @RequestMapping("/updateComp")
    @ResponseBody
    public void updateComp(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "cid") Long cid,
                           @RequestParam(value = "cname") String cname,
                           @RequestParam(value = "cAddress") String cAddress) {
        Company company = new Company();
        company.setCname(cname);
        company.setServer_address(cAddress);
        company.setId(cid);

        compService.updateComp(company);

    }

}
