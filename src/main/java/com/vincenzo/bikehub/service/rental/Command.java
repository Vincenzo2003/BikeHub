package com.vincenzo.bikehub.service.rental;


public interface Command<T> {
    T execute();
}
