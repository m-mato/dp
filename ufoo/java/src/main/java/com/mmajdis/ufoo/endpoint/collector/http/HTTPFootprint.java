package com.mmajdis.ufoo.endpoint.collector.http;

import com.mmajdis.ufoo.endpoint.collector.http.geoip.Location;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Matej Majdis
 */
public class HTTPFootprint {

    private RequestInfo requestInfo;

    private Map<String, String> headers;

    public HTTPFootprint() {
        this.headers = new TreeMap<>();
    }

    public HTTPFootprint(RequestInfo requestInfo, Map<String, String> headers) {
        this.requestInfo = requestInfo;
        this.headers = headers;
    }

    public RequestInfo getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(RequestInfo requestInfo) {
        this.requestInfo = requestInfo;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HTTPFootprint that = (HTTPFootprint) o;

        if (requestInfo != null ? !requestInfo.equals(that.requestInfo) : that.requestInfo != null) return false;
        return headers != null ? headers.equals(that.headers) : that.headers == null;
    }

    @Override
    public int hashCode() {
        int result = requestInfo != null ? requestInfo.hashCode() : 0;
        result = 31 * result + (headers != null ? headers.hashCode() : 0);
        return result;
    }

    public static class RequestInfo {

        private String servletPath;

        private String clientIp;

        private Location location;

        private Set<String> locales;

        private String encoding;

        public RequestInfo() {
            this.locales = new HashSet<>();
        }

        public RequestInfo(String servletPath, String clientIp, Location location, Set<String> locales, String encoding) {
            this.servletPath = servletPath;
            this.clientIp = clientIp;
            this.location = location;
            this.locales = locales;
            this.encoding = encoding;
        }

        public String getServletPath() {
            return servletPath;
        }

        public void setServletPath(String servletPath) {
            this.servletPath = servletPath;
        }

        public String getClientIp() {
            return clientIp;
        }

        public void setClientIp(String clientIp) {
            this.clientIp = clientIp;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public Set<String> getLocales() {
            return locales;
        }

        public void setLocales(Set<String> locales) {
            this.locales = locales;
        }

        public String getEncoding() {
            return encoding;
        }

        public void setEncoding(String encoding) {
            this.encoding = encoding;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            RequestInfo that = (RequestInfo) o;

            if (servletPath != null ? !servletPath.equals(that.servletPath) : that.servletPath != null) return false;
            if (clientIp != null ? !clientIp.equals(that.clientIp) : that.clientIp != null) return false;
            if (location != null ? !location.equals(that.location) : that.location != null) return false;
            if (locales != null ? !locales.equals(that.locales) : that.locales != null) return false;
            return encoding != null ? encoding.equals(that.encoding) : that.encoding == null;
        }

        @Override
        public int hashCode() {
            int result = servletPath != null ? servletPath.hashCode() : 0;
            result = 31 * result + (clientIp != null ? clientIp.hashCode() : 0);
            result = 31 * result + (location != null ? location.hashCode() : 0);
            result = 31 * result + (locales != null ? locales.hashCode() : 0);
            result = 31 * result + (encoding != null ? encoding.hashCode() : 0);
            return result;
        }
    }
}
