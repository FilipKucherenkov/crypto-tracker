package com.fnk.dto;

import com.fnk.enums.ConnectionAction;

import java.util.List;

public record ExchangeConnectionRequest(ConnectionAction connectionAction, List<String> productIds) {}
