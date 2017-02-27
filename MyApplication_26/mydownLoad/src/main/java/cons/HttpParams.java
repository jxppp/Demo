package cons;

/**
 * Created by Administrator on 2015/11/3.
 */
public enum  HttpParams {

    /**请求类型*/
    MEATHOD("GET"),
    /**读取超时*/
    TIMEOUT_READ(5000),
    /**连接超时*/
    TIMEOUT_CONNECT(10000),
    /**浏览器支持的MIME类型*/
    REQUEST_ACCEPT("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg, " +
            "application/x-shockwave-flash, application/xaml+xml, " +
            "application/vnd.ms-xpsdocument, application/x-ms-xbap, " +
            "application/x-ms-application, application/vnd.ms-excel, " +
            "application/vnd.ms-powerpoint, application/msword, */*");
//    /**浏览器所支持的语言类型*/
//    REQUEST_LANGUAGE("")


    private String field;
    private String content;
    private int value;

    HttpParams(int value){
        this.value = value;
    }

    HttpParams(String content){
        this.content = content;
    }

    HttpParams(String field, String content){
        this.field = field;
        this.content = content;
    }

    public String getField() {
        return field;
    }

    public String getContent() {
        return content;
    }

    public int getValue() {
        return value;
    }
}
