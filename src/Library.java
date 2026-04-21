import java.util.*;

public class Library {
    private Map<Long, Book> books = new HashMap<>();
    private Map<Long, Reader> readers = new HashMap<>();

    //Добавляем книгу в библиотеку
    public void addBook(Book book) {
        books.put(book.getId(),book);
        IO.println("Книга ID№"+book.getId()+" добавлена в библиотеку.");
    }

    //удаление книги из библиотеки
    public void removeBook(Book book) {
        if (!book.isBorrowed()) {//проверяем ее занятость
            if (books.remove(book)!=null) {//проверяем, а была ли добавлена книга в библиотеку
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
        for (int i=0;i<books.size();i++) {
            if (books.get(i).getId()==id) {
                return books.get(i);
            }
        }
        IO.println("Книга №"+id+" не найдена");
        return null;
    }

    public String infoBook(long id) {
        Book book = books.get(id); // Прямой поиск по ID

        if (book != null) {
            return book.getInfo();
        }

        IO.println("Книга №" + id + " не найдена");
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

    //выдача книги
    public boolean borrowBook(long readerId, long bookId) {
        Book tekBook = books.get(bookId);
        Reader tekReader = readers.get(readerId); // Предполагаем, что читатели тоже в Map
        // 1. Проверка: не найдены оба
        if (tekBook == null && tekReader == null) {
            IO.println("Книга и читатель не найдены - выдать книгу невозможно");
            return false;
        }
        // 2. Проверка: не найдена книга
        if (tekBook == null) {
            IO.println("Книга не найдена - выдать книгу невозможно");
            return false;
        }
        // 3. Проверка: не найден читатель
        if (tekReader == null) {
            IO.println("Читатель не найден - выдать книгу невозможно");
            return false;
        }
        // 4. Попытка выдать книгу (вызываем метод у найденного читателя)
        return tekReader.borrowBook(tekBook);
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
            return tekReader.getBorrowedBooks();
        }
        else {
            IO.println("Читатель №"+readerId+" не найден");
            return null;
        }
    }
}