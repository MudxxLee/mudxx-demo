package com.mudxx.demo.boot.jpa.modules.table.dto;

import java.io.Serializable;
import java.util.Date;

public class PlatformActionLogForm implements Serializable {
    private static final long serialVersionUID = 7873558090843357194L;

    private Date operateDate;
    private String uri;
    private String queryParam;
    private String body;
    private String requestId;

    private PlatformActionLogForm(Builder builder) {
        setOperateDate(builder.operateDate);
        setUri(builder.uri);
        setQueryParam(builder.queryParam);
        setBody(builder.body);
        setRequestId(builder.requestId);
        setIp(builder.ip);
    }

    public Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getQueryParam() {
        return queryParam;
    }

    public void setQueryParam(String queryParam) {
        this.queryParam = queryParam;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    private String ip;

    public static final class Builder {
        private Date operateDate;
        private String uri;
        private String queryParam;
        private String body;
        private String requestId;
        private String ip;

        public Builder() {
        }

        public Builder operateDate(Date val) {
            operateDate = val;
            return this;
        }

        public Builder uri(String val) {
            uri = val;
            return this;
        }

        public Builder queryParam(String val) {
            queryParam = val;
            return this;
        }

        public Builder body(String val) {
            body = val;
            return this;
        }

        public Builder requestId(String val) {
            requestId = val;
            return this;
        }

        public Builder ip(String val) {
            ip = val;
            return this;
        }

        public PlatformActionLogForm build() {
            return new PlatformActionLogForm(this);
        }
    }
}
