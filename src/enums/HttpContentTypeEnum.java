package enums;

public enum HttpContentTypeEnum {
    JSON("application/json"),
    TEXT("text/plain");

    private final String mediaType;

    HttpContentTypeEnum(String mediaType) {
        this.mediaType = mediaType;
    }

    String getMediaType() {
        return this.mediaType;
    }

    public static boolean isJSON(String type) {
        return JSON.getMediaType().equals(type);
    }
}
