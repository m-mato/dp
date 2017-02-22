package com.mmajdis.ufoo;

/**
 * @author Matej Majdis <matej.majdis@avg.com>
 */
public class TCPFootprint {

    private Long id;

    private Long timestamp;

    private Long ipLength;

    private Long tcpLength;

    private Long tcpWindow;

    public TCPFootprint() {
    }

    public TCPFootprint(Long id, Long timestamp, Long ipLength, Long tcpLength, Long tcpWindow) {
        this.id = id;
        this.timestamp = timestamp;
        this.ipLength = ipLength;
        this.tcpLength = tcpLength;
        this.tcpWindow = tcpWindow;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getIpLength() {
        return ipLength;
    }

    public void setIpLength(Long ipLength) {
        this.ipLength = ipLength;
    }

    public Long getTcpLength() {
        return tcpLength;
    }

    public void setTcpLength(Long tcpLength) {
        this.tcpLength = tcpLength;
    }

    public Long getTcpWindow() {
        return tcpWindow;
    }

    public void setTcpWindow(Long tcpWindow) {
        this.tcpWindow = tcpWindow;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TCPFootprint that = (TCPFootprint) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getTimestamp() != null ? !getTimestamp().equals(that.getTimestamp()) : that.getTimestamp() != null)
            return false;
        if (getIpLength() != null ? !getIpLength().equals(that.getIpLength()) : that.getIpLength() != null)
            return false;
        if (getTcpLength() != null ? !getTcpLength().equals(that.getTcpLength()) : that.getTcpLength() != null)
            return false;
        return getTcpWindow() != null ? getTcpWindow().equals(that.getTcpWindow()) : that.getTcpWindow() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getTimestamp() != null ? getTimestamp().hashCode() : 0);
        result = 31 * result + (getIpLength() != null ? getIpLength().hashCode() : 0);
        result = 31 * result + (getTcpLength() != null ? getTcpLength().hashCode() : 0);
        result = 31 * result + (getTcpWindow() != null ? getTcpWindow().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TCPFootprint{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", ipLength=" + ipLength +
                ", tcpLength=" + tcpLength +
                ", tcpWindow=" + tcpWindow +
                '}';
    }
}
