package com.crimsonlogic.arilinemanangmentsystem.utility;


import com.crimsonlogic.arilinemanangmentsystem.exception.InvalidInputException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputUtil {

    private static final Scanner sc = new Scanner(System.in);

    public int getInt(String message) {

        while (true) {

            try {

                System.out.print(message);
                return Integer.parseInt(sc.nextLine());

            } catch (NumberFormatException e) {

                System.out.println("Invalid integer. Please try again.");
            }
        }
    }

    public  double getDouble(String message) {

        while (true) {
            try {
                System.out.print(message);
                return Double.parseDouble(sc.nextLine());

            } catch (NumberFormatException e) {
                throw new InvalidInputException(
                        "Invalid Double Input. Please enter a valid number.");
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public  String getString(String message) {

        while (true) {

            try {
                System.out.print(message);

                String value = sc.nextLine().trim();

                if (value.isEmpty()) {
                    throw new InvalidInputException(
                            "Input cannot be empty.");
                }

                return value;

            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public  LocalDate getDate(String message) {

        while (true) {

            try {
                System.out.print(message + " (yyyy-MM-dd): ");
                return LocalDate.parse(sc.nextLine());

            } catch (DateTimeParseException e) {
                System.out.println("Invalid Date Format. Use yyyy-MM-dd.");
            }
        }
    }



    public  boolean getBoolean(String message) {

        while (true) {

            try {

                System.out.print(message + " (true/false): ");

                String value = sc.nextLine().trim().toLowerCase();

                if ("true".equals(value)) {
                    return true;
                }

                if ("false".equals(value)) {
                    return false;
                }

                throw new InvalidInputException(
                        "Please enter only true or false.");

            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public LocalDateTime getDateTime(String message) {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        while (true) {

            try {

                System.out.print(message + " (dd-MM-yyyy HH:mm): ");

                String input = sc.nextLine().trim();

                return LocalDateTime.parse(input, formatter);

            } catch (DateTimeParseException e) {

                System.out.println("Invalid format.");
                System.out.println("Example : 30-07-2026 10:30");
            }
        }
    }
}
