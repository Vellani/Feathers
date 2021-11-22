package com.example.feathers.web.exception;

import com.example.feathers.util.SimplePair;

public interface CustomErrorInterface {
    SimplePair<Integer, String> getError();
}
