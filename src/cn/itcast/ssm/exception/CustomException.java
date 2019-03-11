package cn.itcast.ssm.exception;

/**
 * 系统自定义异常，针对预期的异常，需要在程序中抛出此类的异常
 * @author Administrator
 * 思路：系统遇到异常时，会手动抛出，dao抛给service，service抛给controller，controller抛给前端控制器
 * 前端控制器调用全局异常处理器
 */
public class CustomException extends Exception{

	//异常信息串
	private String message;

	public CustomException(String message) {
		super(message);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
