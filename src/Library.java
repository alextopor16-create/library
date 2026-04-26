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
            if (books.remove(book)!=null) {//проверяем, а была ли добавлена книга в библиотеку
                booksByYears.get(book.getYear()).remove(book);
                authorsByNames.get(book.getAuthor()).remove(book);
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
                IO.println("В библиотеке отстутвует читатель "+reader.getInfo());
            }
        }
        else {
            IO.println("Читатель ID№"+reader.getId()+" не сдал все книги.");
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
            IO.println(ErrMsg.READER_NOT_FOUND.getMsg()+" ("+ErrMsg.READER_NOT_FOUND.getCode()+")");
            return false;
        }
        //Book tekBook = books.get(bookId);
        //Reader tekReader = readers.get(readerId); // Предполагаем, что читатели тоже в Map
        //// 1. Проверка: не найдены оба
        //if (tekBook == null && tekReader == null) {
        //    IO.println("Книга и читатель не найдены - выдать книгу невозможно");
        //    return false;
        //}
        //// 2. Проверка: не найдена книга
        //if (tekBook == null) {
        //    IO.println("Книга не найдена - выдать книгу невозможно");
        //    return false;
        //}
        //// 3. Проверка: не найден читатель
        //if (tekReader == null) {
        //    IO.println("Читатель не найден - выдать книгу невозможно");
        //    return false;
        //}
        //// 4. Попытка выдать книгу (вызываем метод у найденного читателя)
        //return tekReader.borrowBook(tekBook);

    }

    //возврат книги
    public void returnBook(long readerId, long bookId) {
        Reader tekReader = readers.get(readerId);
        Book tekBook = books.get(bookId);

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
    public List<Book> getReaderBooks(int readerId) {
        Reader tekReader = findReader(readerId);
        if (tekReader != null) {
            //return tekReader.getBorrowedBooks();
            return null;
        }
        else {
            IO.println("Читатель №"+readerId+" не найден");
            return null;
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