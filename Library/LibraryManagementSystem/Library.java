import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Library {
    private ArrayList<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(String title) {
        books.removeIf(book -> book.getTitle().equalsIgnoreCase(title));
    }

    public ArrayList<Book> displayBooks() {
        return books;
    }

    public boolean borrowBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title) && book.isAvailable()) {
                book.setAvailable(false);
                return true;
            }
        }
        return false;
    }

    public void returnBook(String title) {
    for (Book book : books) {
        if (book.getTitle().equalsIgnoreCase(title)) {
            book.setAvailable(true);
            JOptionPane.showMessageDialog(null, "You have returned the book: " + title);
            return;
        }
    }
    JOptionPane.showMessageDialog(null, "Book not found in the library.");
}
}