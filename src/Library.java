import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> books = new ArrayList<>();
    private List<Reader> readers = new ArrayList<>();

    //Добавляем книгу в библиотеку
    public void addBook(Book book) {
        books.add(book);
        IO.println("Книга ID№"+book.getId()+" добавлена в библиотеку.");
    }

    //удаление книги из библиотеки
    public void removeBook(Book book) {
        if (!book.isBorrowed()) {//проверяем ее занятость
            if (books.remove(book)) {//проверяем, а была ли добавлена книга в библиотеку
                IO.println("Книга ID№"+book.getId()+" удалена из библиотеки.");
            }
            else{
                IO.println("В библиотеке отстутвует книга "+book.getInfo());
            }

        }
        else {
            IO.println("Книга ID№"+book.getId()+" у читателя, удаление невозможно.");
        }
    }

    //добавление читателя в библиотеку
    public void addReader(Reader reader) {
        readers.add(reader);
        IO.println("Читатель ID№"+reader.getId()+" добавлен в библиотеку.");
    }

    //удаление читателя из библиотеки
    public void removeReader(Reader reader) {
        if (reader.getBorrowedBooks().isEmpty()) {//проверяем пусто ли у него
            if (readers.remove(reader)){
                IO.println("Читатель ID№"+reader.getId()+" удален из библиотеки.");
            }
            else{
                IO.println("В библиотеке отстутвует читатель "+reader.getInfo());
            }
        }
        else {
            IO.println("Читатель ID№"+reader.getId()+" не сдал все книги.");
        }
    }

    //поиск книги
    public Book findBook(long id) {
        for (int i=0;i<books.size();i++) {
            if (books.get(i).getId()==id) {
                return books.get(i);
            }
        }
        IO.println("Книга №"+id+" не найдена");
        return null;
    }

    public String infoBook(long id) {
        for (int i=0;i<books.size();i++) {
            if (books.get(i).getId()==id) {
                return books.get(i).getInfo();
            }
        }
        IO.println("Книга №"+id+" не найдена");
        return null;
    }

    //поиск читателя
    public Reader findReader(long id) {
        for (int i=0;i<readers.size();i++) {
            if (readers.get(i).getId()==id) {
                return readers.get(i);
            }
        }
        IO.println("Читатель №"+id+" не найден");
        return null;
    }

    //відача книги
    public boolean borrowBook(long readerId, long bookId) {
        Book tekBook=findBook(bookId);
        Reader tekReader=findReader(readerId);

        if ((tekBook==null) && (tekReader==null)) {
            IO.println("Книга и читатель не найлдены - выдать книгу не возможно");
            return false;
        }
        if (tekBook==null) {
            IO.println("Книга не найдена - выдать книгу не возможно");
            return false;
        }
        if (tekReader==null) {
            IO.println("Читатель не найден - выдать книгу не возможно");
            return false;
        }
        if (tekReader.borrowBook(tekBook)) {
            return true;
        }
        return false;
    }

    //возврат книги
    public void returnBook(long readerId, long bookId) {
        Reader tekReader = findReader(readerId);
        Book tekBook = findBook(bookId);

        if (tekReader != null && tekBook != null) {
            tekReader.returnBook(tekBook);
        }
        else if (tekReader == null) {
            IO.println("Читатель не найден - возврат невозможен");
        }
        else {
            IO.println("Книга не найдена - возврат невозможен");
        }
    }

    //возврат списка книг
    public List<Book> getAllBooks() {
        return books;
    }

    //возврат списка читателей
    public List<Reader> getAllReaders() {
        return readers;
    }

    //получить список всех занятых книг
    public List<Book> getBorrowedBooks() {
        List<Book> booksIsBorroed = new ArrayList<>();
        for (Book tekBook : books) {//проход по списку
            if (tekBook.isBorrowed()) {
                booksIsBorroed.add(tekBook);
            }
        }
        return booksIsBorroed;
    }

    //получить список книг читателя
    public List<Book> getReaderBooks(int readerId) {
        Reader tekReader = findReader(readerId);
        if (tekReader != null) {
            return tekReader.getBorrowedBooks();
        }
        else {
            IO.println("Читатель №"+readerId+" не найден");
            return null;
        }
    }
}