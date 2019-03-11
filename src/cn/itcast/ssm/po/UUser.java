package cn.itcast.ssm.po;

import java.io.Serializable;
import java.util.Date;

public class UUser implements Serializable{
	
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long userId;

    private String nickname;

    private String email;

    private String pswd;

    private Date create_time;

    private Long status;

    private Date last_login_time;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd == null ? null : pswd.trim();
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Date getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(Date last_login_time) {
        this.last_login_time = last_login_time;
    }

	@Override
	public String toString() {
		return "UUser [userId=" + userId + ", nickname=" + nickname
				+ ", email=" + email + ", pswd=" + pswd + ", create_time="
				+ create_time + ", status=" + status + ", last_login_time="
				+ last_login_time + "]";
	}
    
    
    
    
}