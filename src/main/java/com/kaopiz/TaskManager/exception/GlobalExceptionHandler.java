package com.kaopiz.TaskManager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler (MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
	  ErrorResponse errorResponse = new ErrorResponse(1,"Invalid data","Nhập sai dữ liệu");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex){
	  ErrorResponse errorResponse = new ErrorResponse(2,ex.getMessage(),"Không tìm thấy dữ liệu");
	  return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
  }
  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex){
	  	ErrorResponse errorResponse  = new ErrorResponse(2,"Authentication failed", "Tài khoản mật khẩu không đúng");
	  	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);

  }
  
}
