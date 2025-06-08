package com.fnk.data.dto.rest;

import com.fnk.data.enums.ErrorCode;

public record SystemErrorResponse(ErrorCode code, String message){}
