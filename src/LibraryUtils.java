import java.util.ArrayList;
import java.util.List;

public final class LibraryUtils {
    private LibraryUtils() {}

    public static long totalBooks(Library lib) {
        return lib.getAllBooks().size();
    }

    public static long borrowedBooksCount(Library lib) {
        return lib.getBorrowedBooks().size();
    }

    public static Reader topReader(Library lib) {
        Reader topRead = null;
        int maxCountBooks=-1;
        for (Reader tekReader : lib.getAllReaders()){
            if (maxCountBooks<tekReader.getBorrowedBooks().size()) {
                maxCountBooks=tekReader.getBorrowedBooks().size();
                topRead = tekReader;
            }
        }
        if (topRead !=null) {
            return topRead;
        }
        else {
            return null;
        }
    }

    public static Book mostBorrowedBook(Library lib) {
        Book topBook = null;
        int maxBorrowCount=-1;
        for (Book tekBook : lib.getAllBooks()){
            if (maxBorrowCount<tekBook.getBorrowCount()){
                maxBorrowCount=tekBook.getBorrowCount();
                topBook=tekBook;
            }
        }
        if (topBook !=null) {
            return topBook;
        }
        else {
            return null;
        }
    }

    public static void printBookInfo(Book book) {
        if (book != null) {
            IO.println(book.getInfo());
        }
    }

    public static void printAllBooks(Library lib) {
        for (Book book : lib.getAllBooks()) {
            printBookInfo(book);
        }
    }
}
