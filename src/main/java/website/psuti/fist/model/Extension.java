package website.psuti.fist.model;

public enum Extension {
     /*APPLICATION_ATOM_XML_VALUE("application/atom+xml"),
     APPLICATION_FORM_URLENCODED_VALUE("application/x-www-form-urlencoded"),
     APPLICATION_JSON_VALUE("application/json"),
     APPLICATION_JSON_UTF8_VALUE("application/json),charset=UTF-8"),
     APPLICATION_OCTET_STREAM_VALUE("application/octet-stream"),*/
     APPLICATION_PDF_VALUE("application/pdf");
     /*APPLICATION_PROBLEM_JSON_VALUE("application/problem+json"),
     APPLICATION_PROBLEM_JSON_UTF8_VALUE("application/problem+json),charset=UTF-8"),
     APPLICATION_PROBLEM_XML_VALUE("application/problem+xml"),
     APPLICATION_RSS_XML_VALUE("application/rss+xml"),
     APPLICATION_STREAM_JSON_VALUE("application/stream+json"),
     APPLICATION_XHTML_XML_VALUE("application/xhtml+xml"),
     APPLICATION_XML_VALUE("application/xml"),
     IMAGE_GIF_VALUE("image/gif"),
     IMAGE_JPEG_VALUE("image/jpeg"),
     IMAGE_PNG_VALUE("image/png"),
     MULTIPART_FORM_DATA_VALUE("multipart/form-data"),
     TEXT_EVENT_STREAM_VALUE("text/event-stream"),
     TEXT_HTML_VALUE("text/html"),
     TEXT_MARKDOWN_VALUE("text/markdown"),
     TEXT_PLAIN_VALUE("text/plain"),
     TEXT_XML_VALUE("text/xml");*/

     private String type;

    Extension(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
