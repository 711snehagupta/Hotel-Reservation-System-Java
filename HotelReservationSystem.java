import java.util.*;
import java.io.*;

class Room {
    int roomNumber;
    String category;
    double price;
    boolean available;

    Room(int roomNumber, String category, double price) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.price = price;
        this.available = true;
    }
}

class Booking {
    String customerName;
    Room room;

    Booking(String customerName, Room room) {
        this.customerName = customerName;
        this.room = room;
    }
}

public class HotelReservationSystem {

    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Booking> bookings = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        initializeRooms();

        while (true) {

            System.out.println("\n==================================");
            System.out.println(" HOTEL RESERVATION SYSTEM ");
            System.out.println("==================================");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View Bookings");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    viewAvailableRooms();
                    break;

                case 2:
                    bookRoom();
                    break;

                case 3:
                    cancelBooking();
                    break;

                case 4:
                    viewBookings();
                    break;

                case 5:
                    System.out.println("Thank you for using the system!");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void initializeRooms() {

        rooms.add(new Room(101, "Standard", 1500));
        rooms.add(new Room(102, "Standard", 1500));

        rooms.add(new Room(201, "Deluxe", 3000));
        rooms.add(new Room(202, "Deluxe", 3000));

        rooms.add(new Room(301, "Suite", 5000));
    }

    static void viewAvailableRooms() {

        System.out.println("\nAvailable Rooms:");

        for (Room room : rooms) {

            if (room.available) {

                System.out.println(
                        "Room " + room.roomNumber +
                        " | " + room.category +
                        " | ₹" + room.price);
            }
        }
    }

    static void bookRoom() {

        sc.nextLine();

        System.out.print("Enter Customer Name: ");
        String name = sc.nextLine();

        viewAvailableRooms();

        System.out.print("\nEnter Room Number to Book: ");
        int roomNo = sc.nextInt();

        for (Room room : rooms) {

            if (room.roomNumber == roomNo && room.available) {

                System.out.println("\n----- PAYMENT -----");
                System.out.println("Room Type : " + room.category);
                System.out.println("Amount    : ₹" + room.price);

                sc.nextLine();
                System.out.print("Confirm Payment (yes/no): ");
                String payment = sc.nextLine();

                if (payment.equalsIgnoreCase("yes")) {

                    room.available = false;

                    Booking booking = new Booking(name, room);
                    bookings.add(booking);

                    saveBooking(name, room);

                    System.out.println("\nPayment Successful!");
                    System.out.println("Booking Confirmed!");
                } else {

                    System.out.println("Payment Cancelled.");
                }

                return;
            }
        }

        System.out.println("Room not available!");
    }

    static void cancelBooking() {

        System.out.print("Enter Room Number to Cancel: ");
        int roomNo = sc.nextInt();

        Iterator<Booking> iterator = bookings.iterator();

        while (iterator.hasNext()) {

            Booking booking = iterator.next();

            if (booking.room.roomNumber == roomNo) {

                booking.room.available = true;

                iterator.remove();

                System.out.println("Booking Cancelled Successfully!");

                return;
            }
        }

        System.out.println("Booking not found!");
    }

    static void viewBookings() {

        if (bookings.isEmpty()) {

            System.out.println("No bookings found!");
            return;
        }

        System.out.println("\nCurrent Bookings:");

        for (Booking booking : bookings) {

            System.out.println("----------------------------");
            System.out.println("Customer : " + booking.customerName);
            System.out.println("Room No  : " + booking.room.roomNumber);
            System.out.println("Category : " + booking.room.category);
            System.out.println("Price    : ₹" + booking.room.price);
        }
    }

    static void saveBooking(String name, Room room) {

        try {

            FileWriter writer = new FileWriter("bookings.txt", true);

            writer.write(
                    name + "," +
                    room.roomNumber + "," +
                    room.category + "," +
                    room.price + "\n");

            writer.close();

        } catch (IOException e) {

            System.out.println("Error saving booking!");
        }
    }
}