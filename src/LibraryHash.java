import java.util.HashMap;
import java.util.Map;

public class LibraryHash {
    private Map<Book,Reader> regTable = new HashMap<>();

    //Добавляем книгу в библиотеку
    public void addBook(Book book) {
        regTable.putIfAbsent(book,null);
        IO.println("Книга ID№"+regTable +" добавлена в библиотеку.");


    }
}