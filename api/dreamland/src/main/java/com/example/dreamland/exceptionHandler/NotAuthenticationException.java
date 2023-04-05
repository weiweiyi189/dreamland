package com.example.dreamland.exceptionHandler;

public class NotAuthenticationException extends RuntimeException {
  public NotAuthenticationException(String message) {
    super(message);
  }
}
