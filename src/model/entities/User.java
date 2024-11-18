package model.entities;

public class User {
    
    private Integer id;
    private String userName;
    private String password;
    private String codeUser;

    public User(Integer id, String userName, String password, String codeUser) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.codeUser = codeUser;
    }

    public User(String userName, String password, String codeUser) {
        this.userName = userName;
        this.password = password;
        this.codeUser = codeUser;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCodeUser() {
        return codeUser;
    }

    public void setCodeUser(String codeUser) {
        this.codeUser = codeUser;
    }
    
}
