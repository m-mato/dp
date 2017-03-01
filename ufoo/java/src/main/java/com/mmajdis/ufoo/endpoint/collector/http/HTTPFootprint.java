package com.mmajdis.ufoo.endpoint.collector.http;

import com.mmajdis.ufoo.endpoint.collector.http.location.GeoIP;

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

    public class RequestInfo {

        private String servletPath;

        private String clientIp;

        private GeoIP geoIP;

        private Set<String> locales;

        private String encoding;

        public RequestInfo() {
            this.locales = new HashSet<>();
        }

        public RequestInfo(String servletPath, String clientIp, GeoIP geoIP, Set<String> locales, String encoding) {
            this.servletPath = servletPath;
            this.clientIp = clientIp;
            this.geoIP = geoIP;
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

        public GeoIP getGeoIP() {
            return geoIP;
        }

        public void setGeoIP(GeoIP geoIP) {
            this.geoIP = geoIP;
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
            if (geoIP != null ? !geoIP.equals(that.geoIP) : that.geoIP != null) return false;
            if (locales != null ? !locales.equals(that.locales) : that.locales != null) return false;
            return encoding != null ? encoding.equals(that.encoding) : that.encoding == null;
        }

        @Override
        public int hashCode() {
            int result = servletPath != null ? servletPath.hashCode() : 0;
            result = 31 * result + (clientIp != null ? clientIp.hashCode() : 0);
            result = 31 * result + (geoIP != null ? geoIP.hashCode() : 0);
            result = 31 * result + (locales != null ? locales.hashCode() : 0);
            result = 31 * result + (encoding != null ? encoding.hashCode() : 0);
            return result;
        }
    }
}
