package com.fnk.data.dto.websocket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SubscriptionsMessage extends CoinbaseMessage {

    private List<Channel> channels = List.of();


    public List<Channel> getChannels() {
        return channels;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Channel {

        private String name;

        @JsonProperty("product_ids")
        private List<String> productIds = Collections.emptyList();

        @JsonProperty("account_ids")
        private List<String> accountIds;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getProductIds() {
            return productIds;
        }

        public void setProductIds(List<String> productIds) {
            this.productIds = productIds;
        }

        public List<String> getAccountIds() {
            return accountIds;
        }

        public void setAccountIds(List<String> accountIds) {
            this.accountIds = accountIds;
        }

        @Override
        public String toString() {
            return "Channel{" +
                    "name='" + name + '\'' +
                    ", productIds=" + productIds +
                    ", accountIds=" + accountIds +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "SubscriptionsMessage{" +
                "type='" + getType() + '\'' +
                ", channels=" + channels +
                '}';
    }
}
