package cn.report.api;

import java.util.List;
import java.util.Map;

import cn.report.domain.Company;

public interface CompService {
    public List<Company> compInfo();

    public Long compAdd(String cname, String server_address);

    public  int compCheck(Long cid);

    public String getAddress(Long id);

    public void addImg(String s, Long id);

    public List<String> selectImg(Long cid);

    public void deleteImg(Long cid);

    public List<String> getByIdImg(Long id);

    public  Company byIdInfo(Long cid);

    public void updateComp(Company company);
    // 根据用户id获取所有账套信息
    public Map<String,String> getAllAccount(Long id);
}
