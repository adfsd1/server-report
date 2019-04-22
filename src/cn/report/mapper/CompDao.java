package cn.report.mapper;

import cn.report.domain.Company;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompDao {

    public Long compAdd(@Param("cname") String cname, @Param("server_address") String server_address);

    public List<Company> compInfo();


    public  int compCheck(Long cid);

    public  String getAddress(Long id);
    // 获取用友软件类型
    public  String getType(Long id);

    void addImg(@Param("imgName") String s, @Param("id") Long id);

    public List<String> selectImg(Long cid);

    public void deleteImg(Long cid);

    public  List<String> getByIdImg(Long id);

    public Company byIdInfo(Long cid);

    public void updateComp(Company company);
}
