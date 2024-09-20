package com.jamesstorr.jokes_service.application.exception;

public class ProviderNotFoundException extends RuntimeException {
    public ProviderNotFoundException(String provider)
    {
        super("Provider not found: " + provider);
    }}
