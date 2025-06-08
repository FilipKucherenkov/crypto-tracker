package com.fnk.data.dto.rest;

import com.fnk.data.enums.ConnectionAction;

import java.util.List;

public record CoinbaseConnectionRequest(ConnectionAction connectionAction, List<String> productIds) {}
