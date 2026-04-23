import java.util.*;

public class Reader {
    private static Long readerId = (long)1.0;

    private Long id;
    private String name;
    //private List<Book> borrowedBooks;
    private Map<Long, Book> borrowedBooks = new HashMap<>();

    //Конструктор
    public Reader(String name) {
        this.id = readerId++;
        this.name = name;
        this.borrowedBooks = new HashMap<>();
        IO.println("Новій читатель: "+getInfo());
    }

    //Взять книгу (если у читателя меньше 3 и книга свободна)
    public boolean borrowBook(Book book) {
        if (borrowedBooks.size() >= 3) {
            IO.println("Книга id№"+getId()+" не выдана - у читателя достигнут лимит книг");
            return false;
        }
        if (book.isBorrowed()) {
            IO.println("Книга id№"+book.getId()+" занята читателем №"+book.getBorrowedBy().getId());
            return false;
        }
        borrowedBooks.put(book.getId(),book);//добавляем книгу в массив
        book.borrow(this);//помечаем книгу как занятую
        IO.println("Книга №"+book.getId()+" выдана читателю №"+getId()+" и помечена как занятая");
        return true;
    }

    //возвращаем книгу
    public void returnBook(Book book) {
        if (borrowedBooks.remove(book.getId())!=null) {//проверяем успешность удаления книги из ArreyList, если успешно - удаляем и
            book.returnBack();//вызываем возвращение книги
            IO.println("Книга №"+book.getId()+" возвращена и свободна");
        }
        else {
            IO.println("Книга №"+book.getId()+" отсутствует у читателя - возвращать нечего");
        }
    }

    //Не доделал - получаем информацию о читателе, пока без списка книг - только с количеством
    public String getInfo() {
        return "Читатель id№" + id +", Имя=" + name + ", Количество взятых книг=" + borrowedBooks.size();
    }

    //получаем номер id
    public Long getId() {
        return id;
    }

    //Не доделал - Список книг - пока в виде ссылок
    public Collection<Book> getBorrowedBooks() {return borrowedBooks.values();
    }
}