public class Book {
    private static Integer bookId = 1;

    private Integer id;
    private String title;
    private String author;
    private int year;
    private boolean isBorrowed;
    private Reader borrowedBy;
    private int borrowCount;

    //конструктор
    public Book(String title, String author, int year) {
        this.id = bookId++;
        this.title = title;
        this.author = author;
        this.year = year;
        this.isBorrowed = false;
        this.borrowCount = 0;
        IO.println("Создана новая книга: "+getInfo());
    }

    //взятие книги
    public void borrow(Reader reader) {
        if (!isBorrowed) {
            isBorrowed = true;
            borrowedBy = reader;
            borrowCount++;
            IO.println("Книга id№"+getId()+" выдана "+reader.getInfo());
        }

    }

    //возврат книги
    public void returnBack() {
        IO.println("Книга №"+getId()+" возвращена читателем №"+borrowedBy.getId());
        isBorrowed = false;
        borrowedBy = null;
    }

    //информация о книге
    public String getInfo() {
        return "Книга id№" + id +", название= '" + title + '\'' +", автор= '" + author + '\'' +", год=" + year +", занята=" + isBorrowed +", количество взятий=" + borrowCount;
    }

    //Получение ID
    public Integer getId() {
        return id;
    }

    //Получение занятости
    public boolean isBorrowed() {
        return isBorrowed;
    }

    //Количество взятий
    public int getBorrowCount() {
        return borrowCount;
    }

    //Кем занята
    public Reader getBorrowedBy() {
        return borrowedBy;
    }
}