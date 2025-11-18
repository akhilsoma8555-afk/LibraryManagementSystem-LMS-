import java.io.*;
import java.util.*;
// Book class represents book details
class Book
 {
    String bookId;
    String title;
    String author;
    int quantity;
public Book(String bookId, String title, String author, int quantity) 
{
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.quantity = quantity;
  }
 // Convert object data into file-friendly format
    public String toFile() 
{
        return bookId + "," + title + "," + author + "," + quantity;
 }
}
// Student class stores issued book details
class Student 
{
    String studentId;
    String name;
    String bookId;
    public Student(String studentId, String name, String bookId) 
{
        this.studentId = studentId;
        this.name = name;
        this.bookId = bookId;
    }
// Convert student data into file format
    public String toFile() 
{
        return studentId + "," + name + "," + bookId;
 }
}
public class LibraryManagementSystem 
{
 static Scanner sc = new Scanner(System.in);
 // Fine system configuration
    static int allowedDays = 21;     
    static int finePerDay = 1;       
 // ADD BOOK
    public static void addBook() 
{
        try {
            sc.nextLine();
            System.out.print("Book ID: ");
            String id = sc.nextLine();
            System.out.print("Title: ");
            String title = sc.nextLine();
            System.out.print("Author: ");
            String author = sc.nextLine();
            System.out.print("Quantity: ");
            int qty = sc.nextInt();
            Book b = new Book(id, title, author, qty);
            FileWriter fw = new FileWriter("books.txt", true);
            fw.write(b.toFile() + "\n");
            fw.close();
            System.out.println("Book Added!\n");
        } catch (Exception e) 
       {
            System.out.println("Error in addBook()");
        }
    }
// ISSUE BOOK
    public static void issueBook() 
{
        try 
      {
            System.out.print("Enter Book ID: ");
            String bookId = sc.next();
            System.out.print("Enter Student ID: ");
            String sid = sc.next();
            sc.nextLine();
            System.out.print("Enter Student Name: ");
            String sname = sc.nextLine();
            File oldFile = new File("books.txt");
            File newFile = new File("temp.txt");
            BufferedReader br = new BufferedReader(new FileReader(oldFile));
            BufferedWriter bw = new BufferedWriter(new FileWriter(newFile));
            String line;
            boolean issued = false;
            while ((line = br.readLine()) != null) 
          {
                String[] arr = line.split(",");
                // Check stock availability
                if (arr[0].equals(bookId) && Integer.parseInt(arr[3]) > 0) 
             {
                    int newQty = Integer.parseInt(arr[3]) - 1; // Reduce qty
                    bw.write(arr[0] + "," + arr[1] + "," + arr[2] + "," + newQty + "\n");
                   // Save student issue record
                    Student s = new Student(sid, sname, bookId);
                    FileWriter fs = new FileWriter("students.txt", true);
                    fs.write(s.toFile() + "\n");
                    fs.close();
                    issued = true;
                } else 
                {
                    bw.write(line + "\n");
                }
            }
            br.close();
            bw.close();
            oldFile.delete();
            newFile.renameTo(oldFile);
            if (issued) 
            System.out.println("Book Issued!\n");
           else
             System.out.println("Book Not Available!\n");
         } 
       catch (Exception e) 
       {
            System.out.println("Error in issueBook()");
        }
    }
    // RETURN BOOK WITH FINE
    public static void returnBook() {
        try 
     {
            System.out.print("Enter Book ID: ");
            String bookId = sc.next();
           System.out.print("Enter number of days you kept the book: ");
            int daysTaken = sc.nextInt();
           // Fine calculation
            int fine = 0;
            if (daysTaken > allowedDays) 
           {
                fine = (daysTaken - allowedDays) * finePerDay;
            }
           File oldFile = new File("books.txt");
            File newFile = new File("temp.txt");
           BufferedReader br = new BufferedReader(new FileReader(oldFile));
           BufferedWriter bw = new BufferedWriter(new FileWriter(newFile));
            String line;
            boolean returned = false;
            while ((line = br.readLine()) != null) 
          {
                String[] arr = line.split(",");
               if (arr[0].equals(bookId))
             {
                    int newQty = Integer.parseInt(arr[3]) + 1; // Increase qty
                    bw.write(arr[0] + "," + arr[1] + "," + arr[2] + "," + newQty + "\n");
                    returned = true;
              } else 
              {
                    bw.write(line + "\n");
                }
            }
           br.close();
            bw.close();
            oldFile.delete();
            newFile.renameTo(oldFile);
            if (returned) 
           {
                System.out.println("\nBook Returned!");
                if (fine > 0) 
               {
                    System.out.println("You exceeded the limit!");
                    System.out.println("Fine Amount = Rs. " + fine);
                } 
               else 
                {
                    System.out.println("No Fine! Returned on time.");
                }
                System.out.println();
            } 
           else 
           {
                System.out.println("Book Not Found!\n");
            }

        } catch (Exception e) 
      {
            System.out.println("Error in returnBook()");
        }
    }
    // SHOW STUDENT DETAILS
    public static void showStudents() 
  {
        try 
      {
            File f = new File("students.txt");
            if (!f.exists()) 
          {
                System.out.println("No Records!\n");
                return;
            }
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
           while ((line = br.readLine()) != null) 
          {
                String[] arr = line.split(",");
                System.out.println("\nStudent ID : " + arr[0]);
                System.out.println("Name       : " + arr[1]);
                System.out.println("Book ID    : " + arr[2]);
            }
            br.close();
            System.out.println();
      } catch (Exception e) 
      {
            System.out.println("Error in showStudents()");
        }
    }
   // SEARCH BOOK
    public static void searchBook() 
  {
        try 
      {
            System.out.print("Enter Book ID: ");
            String id = sc.next();
            BufferedReader br = new BufferedReader(new FileReader("books.txt"));
            String line;
            boolean found = false;
            while ((line = br.readLine()) != null) 
          {
                String[] arr = line.split(",");
                if (arr[0].equals(id)) 
              {
                    System.out.println("\nFOUND!");
                    System.out.println("ID      : " + arr[0]);
                    System.out.println("Title   : " + arr[1]);
                    System.out.println("Author  : " + arr[2]);
                    System.out.println("Qty     : " + arr[3]);
                    found = true;
                    break;
                }
            }
            if (!found)
            System.out.println("Book Not Found!");
            System.out.println();
            br.close();
       } catch (Exception e) 
       {
            System.out.println("Error in searchBook()");
        }
    }
    // SHOW ALL BOOKS
    public static void showBooks() 
{
        try 
      {
            File f = new File("books.txt");
            if (!f.exists()) 
          {
                System.out.println("No Books!\n");
                return;
            }
           BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            System.out.println("\n--- AVAILABLE BOOKS ---");
            while ((line = br.readLine()) != null) 
          {
                String[] arr = line.split(",");
                System.out.println("ID: " + arr[0] + " | " + arr[1] + " | " + arr[2] + " | Qty: " + arr[3]);
            }
            System.out.println();
            br.close();
       } catch (Exception e) 
      {
            System.out.println("Error in showBooks()");
        }
    }
   // MAIN MENU
    public static void main(String[] args) 
{
          while (true) 
       {
            System.out.println("===== LIBRARY MENU =====");
            System.out.println("1. Add Book");
            System.out.println("2. Issue Book");
            System.out.println("3. Return Book");
            System.out.println("4. Show Student Details");
            System.out.println("5. Search Book");
            System.out.println("6. Show Available Books");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");
            int ch = sc.nextInt();
            switch (ch) 
          {
                case 1: addBook(); break;
                case 2: issueBook(); break;
                case 3: returnBook(); break;
                case 4: showStudents(); break;
                case 5: searchBook(); break;
                case 6: showBooks(); break;
                case 7: System.exit(0);
                default: System.out.println("Invalid choice!");
            }
        }
    }
}
