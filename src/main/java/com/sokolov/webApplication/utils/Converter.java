package com.sokolov.webApplication.utils;

public interface Converter<T,SoapT> {

    T convertFromSoap(SoapT soapT);

    SoapT convertToSoap(T t);
}
