package cn.report.domain;

//企业实体
public class Company {
    //企业的ID
    private Long id;
    //企业的名字
    private String cname;
    //企业的服务器地址
    private String server_address;
    //企业对应的用友软件类型
    private String type;

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getServer_address() {
        return server_address;
    }

    public void setServer_address(String server_address) {
        this.server_address = server_address;
    }
}
