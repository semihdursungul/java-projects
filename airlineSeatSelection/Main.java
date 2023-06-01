import java.util.*;

class Passenger {
    private String name;
    private String seatNumber;

    public Passenger(String name, String seatNumber) {
        this.name = name;
        this.seatNumber = seatNumber;
    }

    public String getName() {
        return name;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }
}

class AirlineReservationSystem {
    private List<Passenger> passengers;
    private Map<String, Passenger> seatMap;

    public AirlineReservationSystem() {
        passengers = new ArrayList<>();
        seatMap = new HashMap<>();
    }

    public void displayMenu() {
        System.out.println("1. Reserve a seat");
        System.out.println("2. Cancel a reservation");
        System.out.println("3. Display passenger records");
        System.out.println("4. Exit");
    }

    public void reserveSeat() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter passenger name:");
        String name = scanner.nextLine();

        System.out.println("Enter seat number:");
        String seatNumber = scanner.nextLine();

        if (seatMap.containsKey(seatNumber)) {
            System.out.println("Seat " + seatNumber + " is already reserved.");
        } else {
            Passenger passenger = new Passenger(name, seatNumber);
            passengers.add(passenger);
            seatMap.put(seatNumber, passenger);
            System.out.println("Reservation successful.");
        }
    }

    public void cancelReservation() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter passenger name:");
        String name = scanner.nextLine();

        boolean found = false;
        for (Passenger passenger : passengers) {
            if (passenger.getName().equalsIgnoreCase(name)) {
                String seatNumber = passenger.getSeatNumber();
                passengers.remove(passenger);
                seatMap.remove(seatNumber);
                System.out.println("Reservation canceled for " + name + ".");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Passenger not found.");
        }
    }

    public void displayPassengerRecords() {
        if (passengers.isEmpty()) {
            System.out.println("No passengers found.");
        } else {
            System.out.println("Passenger records:");
            for (Passenger passenger : passengers) {
                System.out.println("Name: " + passenger.getName() + ", Seat: " + passenger.getSeatNumber());
            }
        }
    }

    public static void main(String[] args) {
        AirlineReservationSystem system = new AirlineReservationSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            system.displayMenu();
            System.out.println("Enter your choice:");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    system.reserveSeat();
                    break;
                case 2:
                    system.cancelReservation();
                    break;
                case 3:
                    system.displayPassengerRecords();
                    break;
                case 4:
                    System.out.println("Exiting program...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
