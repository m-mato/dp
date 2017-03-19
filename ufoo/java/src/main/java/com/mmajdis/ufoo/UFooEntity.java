package com.mmajdis.ufoo;

import java.util.Map;

/**
 * @author Matej Majdis <matej.majdis@avg.com>
 */
public class UFooEntity {

    private String staticData;

    private RelationData relationData;

    public String getStaticData() {
        return staticData;
    }

    public void setStaticData(String staticData) {
        this.staticData = staticData;
    }

    public RelationData getRelationData() {
        return relationData;
    }

    public void setRelationData(RelationData relationData) {
        this.relationData = relationData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UFooEntity that = (UFooEntity) o;

        if (getStaticData() != null ? !getStaticData().equals(that.getStaticData()) : that.getStaticData() != null)
            return false;
        return getRelationData() != null ? getRelationData().equals(that.getRelationData()) : that.getRelationData() == null;
    }

    @Override
    public int hashCode() {
        int result = getStaticData() != null ? getStaticData().hashCode() : 0;
        result = 31 * result + (getRelationData() != null ? getRelationData().hashCode() : 0);
        return result;
    }

    public static class RelationData {

        private Map<String, String> relationHeaders;

        private long timestamp;

        public Map<String, String> getRelationHeaders() {
            return relationHeaders;
        }

        public void setRelationHeaders(Map<String, String> relationHeaders) {
            this.relationHeaders = relationHeaders;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            RelationData that = (RelationData) o;

            if (getTimestamp() != that.getTimestamp()) return false;
            return getRelationHeaders() != null ? getRelationHeaders().equals(that.getRelationHeaders()) : that.getRelationHeaders() == null;
        }

        @Override
        public int hashCode() {
            int result = getRelationHeaders() != null ? getRelationHeaders().hashCode() : 0;
            result = 31 * result + (int) (getTimestamp() ^ (getTimestamp() >>> 32));
            return result;
        }
    }
}
