package com.dwf.switchstore.ws.interceptor;

import jakarta.interceptor.InterceptorBinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark methods that require authentication.
 * Specifically, it is used to validate the JWT token in the Authorization header.
 */
@InterceptorBinding // Indicates that this annotation is an interceptor binding type
@Target({ElementType.TYPE, ElementType.METHOD}) // Indicates where this annotation can be used
@Retention(RetentionPolicy.RUNTIME) // Indicates when this annotation is available
public @interface Secure {} // Indicates that this annotation is an interface