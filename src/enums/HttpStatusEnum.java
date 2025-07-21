package enums;

public enum HttpStatusEnum {
    OK_200(200, "OK"),
    NO_CONTENT_204(204, "No Content"),
    NOT_FOUND_404(404, "Not Found");

    private final int code;
    private final String statusDescription;

    HttpStatusEnum(int code, String statusDescription) {
        this.code = code;
        this.statusDescription = statusDescription;
    }

    public int getCode() {
        return this.code;
    }

    public String getStatusDescription() {
        return this.statusDescription;
    }
}
