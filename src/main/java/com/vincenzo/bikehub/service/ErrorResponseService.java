//package com.vincenzo.bikehub.service;
//
//import com.vincenzo.bikehub.models.ExceptionResponse;
//import com.vincenzo.bikehub.server.gen.model.ErrorResponse;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//
//import java.time.Instant;
//import java.util.HashMap;
//import java.util.Optional;
//
//@Service
//public class ErrorResponseService {
//
//    public ExceptionResponse createExceptionResponse(String path, String message, Optional<HashMap<String, Object>> details) {
//        return new ExceptionResponse(
//                path,
//                message,
//                details.orElse(null),
//                Instant.now()
//        );
//    }
//}
//
