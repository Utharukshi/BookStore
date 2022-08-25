import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Book {
    private JPanel Main;
    private JTextField txtName;
    private JTextField txtAuthor;
    private JTextField txtQty;
    private JButton insertButton;
    private JTable table1;
    private JButton updateButton;
    private JButton clearButton;
    private JButton deleteButton;
    private JScrollPane table_1;
    private JTextField txtID;
    private JButton searchButton;
    private JLabel imageLogo;


    public static void main(String[] args) {
        JFrame frame = new JFrame("Book");
        frame.setContentPane(new Book().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    Connection con;
    PreparedStatement pst;

    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/bookstore", "root", "");
            System.out.println("Successs");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }


    }

    void table_load() {
        try {
            pst = con.prepareStatement("select * from book");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Book() {
        connect();
        table_load();

        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String bname, author, quantity;

                bname = txtName.getText();
                author = txtAuthor.getText();
                quantity = txtQty.getText();


                try {
                    pst = con.prepareStatement("insert into book(bname,author,quantity)values(?,?,?)");
                    pst.setString(1, bname);
                    pst.setString(2, author);
                    pst.setString(3, quantity);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Added!!!!!");
                    table_load();
                    txtName.setText("");
                    txtAuthor.setText("");
                    txtQty.setText("");
                    txtName.requestFocus();
                } catch (SQLException e1) {

                    e1.printStackTrace();
                }

            }
        });


        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String id, bname, author, quantity;

                bname = txtName.getText();
                author = txtAuthor.getText();
                quantity = txtQty.getText();
                id = txtID.getText();

                try {
                    pst = con.prepareStatement("update book set bname = ?,author = ?,quantity = ? where id = ?");
                    pst.setString(1, bname);
                    pst.setString(2, author);
                    pst.setString(3, quantity);
                    pst.setString(4, id);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Updated!!!!!");
                    table_load();
                    txtName.setText("");
                    txtAuthor.setText("");
                    txtQty.setText("");
                    txtName.requestFocus();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    String id = txtID.getText();

                    pst = con.prepareStatement("select bname,author,quantity from book where id = ?");
                    pst.setString(1, id);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next() == true) {
                        String bname = rs.getString(1);
                        String author = rs.getString(2);
                        String quantity = rs.getString(3);

                        //parsing to the relevent text fields
                        txtName.setText(bname);
                        txtAuthor.setText(author);
                        txtQty.setText(quantity);

                    } else {
                        txtName.setText("");
                        txtAuthor.setText("");
                        txtQty.setText("");
                        JOptionPane.showMessageDialog(null, "Invalid Employee No");

                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }


            }
        });


        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String id;
                id = txtID.getText();

                try {
                    pst = con.prepareStatement("delete from book where id = ?");

                    pst.setString(1, id);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Deleted!!!!!");
                    table_load();
                    txtName.setText("");
                    txtAuthor.setText("");
                    txtQty.setText("");
                    txtName.requestFocus();
                } catch (SQLException e1) {

                    e1.printStackTrace();
                }
            }


        });


        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                txtName.setText("");
                txtAuthor.setText("");
                txtQty.setText("");
                txtName.requestFocus();

            }
        });
    }

}



