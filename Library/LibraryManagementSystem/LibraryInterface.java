import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LibraryInterface extends JFrame {

    private Library library;

    public LibraryInterface(UserManager userManager) {
        library = new Library();
        setTitle("Library Management System");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Set vertical layout
        add(panel);
        placeComponents(panel);

        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        // Customize button styles
        JButton addBookButton = createStyledButton("Add Book");
        JButton displayBooksButton = createStyledButton("Display Books");
        JButton borrowBookButton = createStyledButton("Borrow Book");
        JButton returnBookButton = createStyledButton("Return Book");
        JButton removeBookButton = createStyledButton("Remove Book");

        // Center the buttons
        addCentered(panel, addBookButton);
        addCentered(panel, displayBooksButton);
        addCentered(panel, borrowBookButton);
        addCentered(panel, returnBookButton);
        addCentered(panel, removeBookButton);

        // Action listeners for buttons
        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel inputPanel = new JPanel();
                JTextField titleField = new JTextField(15);
                JTextField authorField = new JTextField(15);
                JTextField isbnField = new JTextField(15);

                inputPanel.add(new JLabel("Title:"));
                inputPanel.add(titleField);
                inputPanel.add(Box.createHorizontalStrut(15)); // Spacer
                inputPanel.add(new JLabel("Author:"));
                inputPanel.add(authorField);
                inputPanel.add(Box.createHorizontalStrut(15)); // Spacer
                inputPanel.add(new JLabel("ISBN:"));
                inputPanel.add(isbnField);

                int result = JOptionPane.showConfirmDialog(null, inputPanel,
                        "Enter Book Details", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    String title = titleField.getText();
                    String author = authorField.getText();
                    String isbn = isbnField.getText();
                    library.addBook(new Book(title, author, isbn));
                    JOptionPane.showMessageDialog(null,"Book Added Successfully!");
                }
            }
        });

        displayBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] columnNames = {"Title", "Author", "ISBN", "Availability"};
                DefaultTableModel model = new DefaultTableModel(columnNames, 0);

                for (Book book : library.displayBooks()) {
                    String availability = book.isAvailable() ? "Available" : "Not Available";
                    model.addRow(new Object[]{book.getTitle(), book.getAuthor(), book.getIsbn(), availability});
                }

                JTable table = new JTable(model);
                JScrollPane scrollPane = new JScrollPane(table);

                // Set preferred size for the table
                table.setFillsViewportHeight(true);

                JDialog dialog = new JDialog();
                dialog.setTitle("Books in Library");
                dialog.setSize(500, 300);
                dialog.setLocationRelativeTo(null); // Center on screen
                dialog.add(scrollPane);
                dialog.setVisible(true);
            }
        });

        borrowBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = JOptionPane.showInputDialog("Enter Book Title to Borrow:");
                if (library.borrowBook(title)) {
                    JOptionPane.showMessageDialog(null,"You have borrowed the book: " + title);
                } else {
                    JOptionPane.showMessageDialog(null,"Book not available or does not exist.");
                }
            }
        });

        returnBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = JOptionPane.showInputDialog("Enter Book Title to Return:");
                library.returnBook(title); 
            }
        });

        removeBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = JOptionPane.showInputDialog("Enter Book Title to Remove:");
                library.removeBook(title); 
                JOptionPane.showMessageDialog(null,"Book Removed Successfully!"); 
            } 
       });
    }

    private void addCentered(JPanel panel, JButton button) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment for the button
        panel.add(button);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);

        // Set button properties
        button.setBackground(Color.decode("#007BFF")); // Bootstrap primary color
        button.setForeground(Color.WHITE); // Text color
        button.setFont(new Font("Arial", Font.BOLD, 14)); // Font style
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding
        button.setFocusPainted(false); // Remove focus outline

       // Add hover effect using mouse listeners
       button.addMouseListener(new java.awt.event.MouseAdapter() {
           public void mouseEntered(java.awt.event.MouseEvent evt) {
               button.setBackground(Color.decode("#0056b3")); // Darker blue on hover
           }
           public void mouseExited(java.awt.event.MouseEvent evt) {
               button.setBackground(Color.decode("#007BFF")); // Original color when not hovered
           }
       });

       return button;
   }
}