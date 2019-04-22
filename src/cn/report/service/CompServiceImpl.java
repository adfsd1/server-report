package cn.report.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.report.api.CompService;
import cn.report.domain.Company;
import cn.report.mapper.CompDao;

@Service
public class CompServiceImpl implements CompService {

    @Autowired
    private CompDao compDao;


    @Override
    public List<Company> compInfo() {
        List<Company> list = compDao.compInfo();
        return list;
    }

    @Override
    public Long compAdd(String cname, String server_address) {

        Long id = compDao.compAdd(cname, server_address);
        return id;

    }

    @Override
    public int compCheck(Long cid) {
        return compDao.compCheck(cid);
    }

    @Override
    public String getAddress(Long id) {
        String address = compDao.getAddress(id);
        return address;
    }

    @Override
    public void addImg(String s, Long id) {
        compDao.addImg(s, id);
    }

    @Override
    public List<String> selectImg(Long cid) {
        List<String> list = compDao.selectImg(cid);
        return list;
    }

    @Override
    public void deleteImg(Long cid) {
        compDao.deleteImg(cid);
    }

    @Override
    public List<String> getByIdImg(Long id) {
        List<String> list = compDao.getByIdImg(id);
        return list;
    }

    @Override
    public Company byIdInfo(Long cid) {
        Company company = compDao.byIdInfo(cid);
        return company;
    }

    @Override
    public void updateComp(Company company) {
        compDao.updateComp(company);
    }

	@Override
	public Map<String,String> getAllAccount(Long id) {
		String type = compDao.getType(id); // 客户公司用友软件的类型
		String address = compDao.getAddress(id); // 客户公司的域名
		HashMap<String, String> typeAndAddress = new HashMap<String,String>();
		typeAndAddress.put("type", type);
		typeAndAddress.put("address",address);
		return typeAndAddress;
	}


}
