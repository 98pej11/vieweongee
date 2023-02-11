package com.ssafy.vieweongee.service;

public interface JwtProperties {
    String ACCESS_TOKEN = "AUTH";
    String REFRESH_TOKEN = "REFRESH";

    String Id = null;
    String Name=null;
    String HEADER_PREFIX = "ACCESS";
    String REFRESH_HEADER_PREFIX = "REFRESH";
    long REFRESH_EXPIRATION_TIME = 60*1000*60*365;
    long EXPIRATION_TIME = 60*1000*60;
}

