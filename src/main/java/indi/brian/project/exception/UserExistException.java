package indi.brian.project.exception;

public class UserExistException extends Exception {
	public UserExistException(String errMsg) {
		super(errMsg);
	}
}
