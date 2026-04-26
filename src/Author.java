
public class Author {
    private static Long authorID = (long)1.0;

    private Long id;
    private String name;

    public Author(String name) {
        this.id = authorID++;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
