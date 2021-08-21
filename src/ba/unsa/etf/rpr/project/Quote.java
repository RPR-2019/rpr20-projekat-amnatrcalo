package ba.unsa.etf.rpr.project;

public class Quote {
    private Integer id;
    private String content;
    private String author;

    public Quote(Integer id,String content, String author) {
        this.id=id;
        this.content = content;
        this.author = author;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
