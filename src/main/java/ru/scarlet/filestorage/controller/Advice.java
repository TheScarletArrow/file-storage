package ru.scarlet.filestorage.controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import ru.scarlet.filestorage.exception.NoDownloadsLeftException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class Advice {

    @ExceptionHandler(NoDownloadsLeftException.class)
    public ResponseEntity<ResponseMessage> handleNoDownloadsLeft(HttpServletRequest request, WebRequest webRequest){
        ResponseMessage responseMessage = new ResponseMessage("No downloads left", ((ServletWebRequest) webRequest).getRequest().getRequestURI(), 400, LocalDateTime.now());
        return  new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
    }
}
