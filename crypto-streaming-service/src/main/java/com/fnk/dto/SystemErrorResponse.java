package com.fnk.dto;

import com.fnk.enums.ErrorCode;

public record SystemErrorResponse(ErrorCode code, String message){}
