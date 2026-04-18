import java.util.HashMap;

public class Main {
    static void main() {

        //Создаем книги
        Book b1 = new Book("Три толстяка", "Олеша", 1949);
        Book b2 = new Book("Чук и Гек", "Гайдар", 1965);
        Book b3 = new Book("Царь Салтан", "Пушкин", 1845);
        Book b4 = new Book("Анжелика", "Маркиз де Сад", 1946);
        Book b5 = new Book("Сказки", "Народные", 1985);
        Book b6 = new Book("Квантовая механика", "КиевНаучКнига", 1968);
        Book b7 = new Book("Море", "Наутилус", 1973);
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
        IO.println("Поиск книги №4. "+lib1.infoBook(4));
        IO.println("Поиск книги 12. "+lib1.infoBook(12));
        IO.println("\nПроверка выдачи:");
        if (lib1.borrowBook(2,3)){IO.println("Книга выдана успешно");}
        if (lib1.borrowBook(2,3)){IO.println("Книга выдана успешно");}
        if (lib1.borrowBook(3,3)){IO.println("Книга выдана успешно");}
        if (lib1.borrowBook(2,5)){IO.println("Книга выдана успешно");}
        if (lib1.borrowBook(2,6)){IO.println("Книга выдана успешно");}
        if (lib1.borrowBook(2,7)){IO.println("Книга выдана успешно");}

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

        IO.println("\nПроверка книг читателя №2:");
        for (Book tekBook: lib1.getReaderBooks(2)) {
            IO.println(tekBook.getInfo());
        }

        IO.println("\nПроверка LibraryUtils:");
        IO.println("Всего книг: "+LibraryUtils.totalBooks(lib1));
        IO.println("из них занятых книг: "+LibraryUtils.borrowedBooksCount(lib1));
        IO.print("из них самая востребованная книга №: ");
        LibraryUtils.printBookInfo(LibraryUtils.mostBorrowedBook(lib1));
        IO.println("Топ Читатель: "+LibraryUtils.topReader(lib1).getInfo());
        IO.println("\nСписок всех книг:");
        LibraryUtils.printAllBooks(lib1);

//==========================================================
//HASHMAP
//=========================================================

        IO.println("\nHashMap:");
        //Создаем HashMap книги
        HashMap<Integer, Book> books=new HashMap<>();
        books.putIfAbsent(b1.getId(),b1);
        books.putIfAbsent(b2.getId(),b2);
        books.putIfAbsent(b3.getId(),b3);
        books.putIfAbsent(b4.getId(),b4);
        books.putIfAbsent(b5.getId(),b5);
        books.putIfAbsent(b6.getId(),b6);
        books.putIfAbsent(b7.getId(),b7);
        books.putIfAbsent(b8.getId(),b8);
        IO.println("\nСписок всех книг:");
        books.forEach((k,v)->{IO.println(v.getInfo());});

        //Создаем HashMap читателей
        HashMap<Integer, Reader> readers=new HashMap<>();
        readers.putIfAbsent(r1.getId(),r1);
        readers.putIfAbsent(r2.getId(),r2);
        readers.putIfAbsent(r3.getId(),r3);
        readers.putIfAbsent(r4.getId(),r4);
        IO.println("\nСписок всех читателей:");
        readers.forEach((k,v)->{IO.println(v.getInfo());});


        //Создаем библиотеку
        LibraryHash libHash = new LibraryHash();

        //Добавляем книги в библиотеку
        for (int i=1; i<10; i++) {
            libHash.addBook(books.get(i));
        }

    }
}
