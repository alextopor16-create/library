import com.sun.source.tree.Tree;

import java.util.*;

public class Library {
    private Map<Long, Book> books = new HashMap<>();
    private Map<Long, Reader> readers = new HashMap<>();
    private TreeMap<Long, List<Book>> booksByYears=new TreeMap<>();
    private TreeMap<String, List<Book>> authorsByNames=new TreeMap<>();

    //Добавляем книгу в библиотеку
    public void addBook(Book book) {
        books.put(book.getId(),book);
        booksByYears.computeIfAbsent((long)book.getYear(), k -> new ArrayList<>()).add(book);
        authorsByNames.computeIfAbsent(book.getAuthor(), k -> new ArrayList<>()).add(book);

        IO.println("Книга ID№"+book.getId()+" добавлена в библиотеку.");
    }

    //удаление книги из библиотеки
    public void removeBook(Book book) {
        if (!book.isBorrowed()) {//проверяем ее занятость
            try {
                books.remove(book); //проверяем, а была ли добавлена книга в библиотеку
                booksByYears.get(book.getYear()).remove(book);
                authorsByNames.get(book.getAuthor()).remove(book);
                IO.println("Книга ID№"+book.getId()+" удалена из библиотеки.");
            }
            catch (Exception e){
                IO.println(ErrMsg.BOOK_NOT_FOUND.getMsg()+" ("+ErrMsg.BOOK_NOT_FOUND.getCode()+")");
            }
        }
        else {
            IO.println(ErrMsg.BOOK_IS_BORROWED.getMsg()+" ("+ErrMsg.BOOK_IS_BORROWED.getCode()+")");
        }
    }

    //добавление читателя в библиотеку
    public void addReader(Reader reader) {
        readers.put(reader.getId(),reader);
        IO.println("Читатель ID№"+reader.getId()+" добавлен в библиотеку.");
    }

    //удаление читателя из библиотеки
    public void removeReader(Reader reader) {
        if (reader.getBorrowedBooks().isEmpty()) {//проверяем пусто ли у него
            if (readers.remove(reader)!=null){
                IO.println("Читатель ID№"+reader.getId()+" удален из библиотеки.");
            }
            else{
                IO.println(ErrMsg.READER_NOT_FOUND.getMsg()+" ("+ErrMsg.READER_NOT_FOUND.getCode()+")");
            }
        }
        else {
            IO.println(ErrMsg.READER_HAS_BOOKS.getMsg()+" ("+ErrMsg.READER_HAS_BOOKS.getCode()+")");
        }
    }

    //поиск книги
    public Book findBook(long id) {
        return books.get(id);
    }

    public String infoBook(long id) {
        try {
            return findBook(id).getInfo();

        } catch (Exception e) {
            return ErrMsg.BOOK_NOT_FOUND.getMsg()+" ("+ErrMsg.BOOK_NOT_FOUND.getCode()+")";
        }
    }

    //поиск читателя
    public Reader findReader(long id) {
        return readers.get(id);
    }

    //выдача книги
    public boolean borrowBook(long readerId, long bookId) {
        try {
            return readers.get(readerId).borrowBook(books.get(bookId));
        }
        //---->>>>
        catch (Exception e) {
            IO.println("Книга №"+readerId+" - "+ErrMsg.BOOK_NOT_BELONG_TO_READER.getMsg()+" ("+ErrMsg.BOOK_NOT_BELONG_TO_READER.getCode()+")");
            return false;
        }
    }

    //возврат книги
    public void returnBook(long readerId, long bookId) {
        Reader tekReader = readers.get(readerId);
        Book tekBook = books.get(bookId);
        try {
            tekReader.returnBook(tekBook);
        }
        catch (Exception e) {
            IO.println("Книга №"+readerId+" - "+ErrMsg.BOOK_NOT_FOUND.getMsg()+" ("+ErrMsg.BOOK_NOT_FOUND.getCode()+") or "+
                    ErrMsg.READER_NOT_FOUND.getMsg()+" ("+ErrMsg.READER_NOT_FOUND.getCode()+")");
        }
    }

    //возврат списка книг
    public Collection<Book> getAllBooks() {
        return books.values();
    }

    //возврат списка читателей
    public Collection<Reader> getAllReaders() {
        return readers.values();
    }

    //получить список всех занятых книг
    public List<Book> getBorrowedBooks() {
        List<Book> booksIsBorroed = new ArrayList<>();
//        for (Book tekBook : books) {//проход по списку
//            if (tekBook.isBorrowed()) {
//                booksIsBorroed.add(tekBook);
//            }
//        }
//        return booksIsBorroed;
        return books.values().stream()     // Получаем поток всех книг из Map
                .filter(Book::isBorrowed)  // Фильтруем только занятые
                .toList();
    }

    //получить список книг читателя
    public void getReaderBooks(Long readerId) {
        try {
            books.values().stream()
                    .filter(book -> book.getBorrowedBy().getId().equals(readers.get(readerId).getId()))
                    .forEach(book -> IO.println(book.getInfo()));
        }
        catch (Exception e) {
            IO.println(ErrMsg.READER_NOT_FOUND.getMsg()+" ("+ErrMsg.READER_NOT_FOUND.getCode()+")");

        }
    }

    public void  getBookAuthor(String author) {
        authorsByNames.entrySet().stream()
                .filter(e -> e.getKey().equals(author))
                .flatMap(e -> e.getValue().stream())
                .forEach(book->IO.println(book.getInfo()));

    }

    public void getBooksInPeriod(Long start,Long end){
        booksByYears.subMap(start, true, end, true)
                .values().stream()
                .flatMap(List::stream)
                .forEach(book -> IO.println(book.getInfo()));
    }
}