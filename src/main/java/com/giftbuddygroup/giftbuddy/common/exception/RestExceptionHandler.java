package com.giftbuddygroup.giftbuddy.common.exception;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.giftbuddygroup.giftbuddy.model.dto.response.ErrorDetailsDTO;
import com.giftbuddygroup.giftbuddy.model.factories.ErrorDetailsFactory;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetailsDTO> handleGenericException(
            Exception exception, WebRequest request) {
        log.error("Exception caught: ", exception);
        ErrorDetailsDTO errorDetailsDTO = ErrorDetailsFactory.buildErrorDetails(exception, request);
        return new ResponseEntity<>(errorDetailsDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDetailsDTO> handleEntityNotFoundException(
            EntityNotFoundException exception, WebRequest request) {
        log.warn("Entity not found: ", exception);
        ErrorDetailsDTO errorDetailsDTO = ErrorDetailsFactory.buildErrorDetails(exception, request);
        return new ResponseEntity<>(errorDetailsDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<ErrorDetailsDTO> handleEventNotFoundException(
            EventNotFoundException exception, WebRequest request) {
        log.warn("EventNotFoundException caught: ", exception);
        ErrorDetailsDTO errorDetailsDTO = ErrorDetailsFactory.buildErrorDetails(exception, request);
        return new ResponseEntity<>(errorDetailsDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserProfileNotFoundException.class)
    public ResponseEntity<ErrorDetailsDTO> handleUserProfileNotFoundException(
            UserProfileNotFoundException exception, WebRequest request) {
        log.warn("UserProfileNotFoundException caught: ", exception);
        ErrorDetailsDTO errorDetailsDTO = ErrorDetailsFactory.buildErrorDetails(exception, request);
        return new ResponseEntity<>(errorDetailsDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ParticipantNotFoundException.class)
    public ResponseEntity<ErrorDetailsDTO> handleParticipantNotFoundException(
            ParticipantNotFoundException exception, WebRequest request) {
        log.warn("ParticipantNotFoundException caught: ", exception);
        ErrorDetailsDTO errorDetailsDTO = ErrorDetailsFactory.buildErrorDetails(exception, request);
        return new ResponseEntity<>(errorDetailsDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorDetailsDTO> handleUsernameAlreadyExistsException(
            UsernameAlreadyExistsException exception, WebRequest request) {
        log.error("UsernameAlreadyExistsException caught: ", exception);
        ErrorDetailsDTO errorDetailsDTO = ErrorDetailsFactory.buildErrorDetails(exception, request);
        return new ResponseEntity<>(errorDetailsDTO, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorDetailsDTO> handleEmailAlreadyExistsException(
            EmailAlreadyExistsException exception, WebRequest request) {
        log.error("EmailAlreadyExistsException caught: ", exception);
        ErrorDetailsDTO errorDetailsDTO = ErrorDetailsFactory.buildErrorDetails(exception, request);
        return new ResponseEntity<>(errorDetailsDTO, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserAlreadyInEventException.class)
    public ResponseEntity<ErrorDetailsDTO> handleUserAlreadyInEventException(
            UserAlreadyInEventException exception, WebRequest request) {
        log.error("UserAlreadyInEventException caught: ", exception);
        ErrorDetailsDTO errorDetailsDTO = ErrorDetailsFactory.buildErrorDetails(exception, request);
        return new ResponseEntity<>(errorDetailsDTO, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotParticipatingInEventException.class)
    public ResponseEntity<ErrorDetailsDTO> handleUserNotParticipatingInEventException(
            UserNotParticipatingInEventException exception, WebRequest request) {
        log.error("UserNotParticipatingInEventException caught: ", exception);
        ErrorDetailsDTO errorDetailsDTO = ErrorDetailsFactory.buildErrorDetails(exception, request);
        return new ResponseEntity<>(errorDetailsDTO, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CannotUpdateInactiveEventException.class)
    public ResponseEntity<ErrorDetailsDTO> handleCannotUpdateInactiveEventException(
            CannotUpdateInactiveEventException exception, WebRequest request) {
        log.error("CannotUpdateInactiveEventException caught: ", exception);
        ErrorDetailsDTO errorDetailsDTO = ErrorDetailsFactory.buildErrorDetails(exception, request);
        return new ResponseEntity<>(errorDetailsDTO, HttpStatus.CONFLICT);
    }
}
