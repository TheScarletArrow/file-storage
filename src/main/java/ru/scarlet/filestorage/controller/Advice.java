package ru.scarlet.filestorage.controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import ru.scarlet.filestorage.exception.*;

import java.time.LocalDateTime;

@RestControllerAdvice
public class Advice {

    @ExceptionHandler(NoDownloadsLeftException.class)
    public ResponseEntity<ResponseMessage> handleNoDownloadsLeft(HttpServletRequest request, WebRequest webRequest){
        ResponseMessage responseMessage = new ResponseMessage("No downloads left", ((ServletWebRequest) webRequest).getRequest().getRequestURI(), 400, LocalDateTime.now());
        return  new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseMessage> handleUserNotFound(HttpServletRequest request, WebRequest webRequest){
        ResponseMessage responseMessage = new ResponseMessage("User not found", ((ServletWebRequest) webRequest).getRequest().getRequestURI(), 400, LocalDateTime.now());
        return  new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SomethingWentWrongException.class)
    public ResponseEntity<ResponseMessage> handleSmthWentWrong(HttpServletRequest request, WebRequest webRequest){
        ResponseMessage responseMessage = new ResponseMessage("Something went wrong", ((ServletWebRequest) webRequest).getRequest().getRequestURI(), 400, LocalDateTime.now());
        return  new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AttachmentNotFoundException.class)
    public ResponseEntity<ResponseMessage> handleAttachmentNotFound(HttpServletRequest request, WebRequest webRequest){
        ResponseMessage responseMessage = new ResponseMessage("Attachment not found", ((ServletWebRequest) webRequest).getRequest().getRequestURI(), 400, LocalDateTime.now());
        return  new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HashDifferException.class)
    public ResponseEntity<ResponseMessage> handleHashDiffer(HttpServletRequest request, WebRequest webRequest){
        ResponseMessage responseMessage = new ResponseMessage("File corrupted", ((ServletWebRequest) webRequest).getRequest().getRequestURI(), 400, LocalDateTime.now());
        return  new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
    }
}
