package com.fnk.dto;

import com.fnk.enums.ErrorCode;

public record SocketClientErrorResponse(ErrorCode code, String message){}
