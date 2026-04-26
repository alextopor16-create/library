import java.util.HashMap;

public class Main {
    static void main() {

        //Создаем книги и авторов
        Book b1 = new Book("Три толстяка", "Гайдар", 1949);
        Book b2 = new Book("Чук и Гек", "Гайдар", 1965);
        Book b3 = new Book("Царь Салтан", "Пушкин", 1845);
        Book b4 = new Book("Анжелика", "Гайдар", 1946);
        Book b5 = new Book("Сказки", "Гайдар", 1985);
        Book b6 = new Book("Квантовая механика", "КиевНаучКнига", 1968);
        Book b7 = new Book("Море", "Пушкин", 1973);
        Book b8 = new Book("Пришельцы", "Новые горизонты", 2025);

        //Создаем читателей
        Reader r1 = new Reader("Алексей");
        Reader r2 = new Reader("Лариса");
        Reader r3 = new Reader("Кирилл");
        Reader r4 = new Reader("Влада");

        //Создаем библиотеку
        Library lib1 = new Library();

        //Добавляем книги в библиотеку
        lib1.addBook(b1);
        lib1.addBook(b2);
        lib1.addBook(b3);
        lib1.addBook(b4);
        lib1.addBook(b5);
        lib1.addBook(b6);
        lib1.addBook(b7);

        //Добавляем читателей
        lib1.addReader(r1);
        lib1.addReader(r2);
        lib1.addReader(r3);
        lib1.addReader(r4);

        IO.println("\nПроверка поиска:");
        IO.println("Поиск книги №4 - "+lib1.infoBook(4));
        IO.println("Поиск книги №12 - "+lib1.infoBook(12));

        IO.println("\nПроверка выдачи:");
        lib1.borrowBook(2,3);
        lib1.borrowBook(2,3);
        lib1.borrowBook(3,3);
        lib1.borrowBook(5,5);
        lib1.borrowBook(2,6);
        lib1.borrowBook(2,7);
        lib1.borrowBook(2,4);

        IO.println("\nПроверка возврата:");
        lib1.returnBook(2,5);
        lib1.returnBook(2,5);
        lib1.returnBook(6,3);
        lib1.returnBook(2,7);
        lib1.returnBook(2,10);

        IO.println("\nПроверка всех книг:");
        for (Book tekBook: lib1.getAllBooks()) {
            IO.println(tekBook.getInfo());
        }

        IO.println("\nПроверка всех читателей:");
        for (Reader tekReader: lib1.getAllReaders()) {
            IO.println(tekReader.getInfo());
        }

        IO.println("\nПроверка всех занятых книг:");
        for (Book tekBook: lib1.getBorrowedBooks()) {
            IO.println(tekBook.getInfo());
        }

        IO.println("\nПроверка книг читателя №3:");
        //---------------------???
        try {
            lib1.getReaderBooks((long)3);
        }
        catch (Exception e) {
            IO.println(ErrMsg.READER_HASNT_BOOKS.getMsg()+" ("+ErrMsg.READER_HASNT_BOOKS.getCode()+")");
        }


        IO.println("\nПроверка LibraryUtils:");
        IO.println("Всего книг: "+LibraryUtils.totalBooks(lib1));
        IO.println("из них занятых книг: "+LibraryUtils.borrowedBooksCount(lib1));
        IO.print("из них самая востребованная книга №: ");
        LibraryUtils.printBookInfo(LibraryUtils.mostBorrowedBook(lib1));
        IO.println("Топ Читатель: "+LibraryUtils.topReader(lib1).getInfo());
        IO.println("\nСписок всех книг:");
        LibraryUtils.printAllBooks(lib1);

        IO.println("\nСписок книг автора Гайдар:");
        lib1.getBookAuthor("Гайдар");
        IO.println("\nСписок книг автора Пушкин:");
        lib1.getBookAuthor("Пушкин");

        IO.println("\nСписок книг c 1950 по 2000:");
        lib1.getBooksInPeriod((long)1950,(long)2000);
    }

}
