package com.crimsonlogic.arilinemanangmentsystem.service;

import com.crimsonlogic.arilinemanangmentsystem.exception.InvalidHumanException;
import com.crimsonlogic.arilinemanangmentsystem.exception.RecordNotFoundException;
import com.crimsonlogic.arilinemanangmentsystem.model.LoyaltyAccount;
import com.crimsonlogic.arilinemanangmentsystem.model.Passenger;
import com.crimsonlogic.arilinemanangmentsystem.utility.IdGenerator;
import com.crimsonlogic.arilinemanangmentsystem.utility.InputUtil;
import com.crimsonlogic.arilinemanangmentsystem.utility.ValidatorUtil;

import java.util.HashMap;

public class PassengerService {

    private final HashMap<String, Passenger> passengers = new HashMap<>();
    private final InputUtil input = new InputUtil();

    public void registerPassenger() {

        System.out.println("\n========== REGISTER PASSENGER ==========");

        String name = input.getString("Enter Name : ");

        String email;
        while (true) {
            try {
                email = input.getString("Enter Email : ");
                ValidatorUtil.validateEmail(email);
                break;
            } catch (InvalidHumanException e) {
                System.out.println(e.getMessage());
            }
        }

        String phone;
        while (true) {
            try {
                phone = input.getString("Enter Phone : ");
                ValidatorUtil.validatePhone(phone);
                break;
            } catch (InvalidHumanException e) {
                System.out.println(e.getMessage());
            }
        }
        String password;
        while (true) {
            try {
                password = input.getString("Enter password : ");
                if(password.equals("0")){
                    throw  new Exception(" 0 can't be password");
                }

                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        String passengerId = IdGenerator.generatePassengerId();

        LoyaltyAccount loyalty = new LoyaltyAccount();

        Passenger passenger = new Passenger(
                passengerId,
                name,
                email,
                phone,
                loyalty,
                password
        );

        passengers.put(passengerId, passenger);

        System.out.println("\nPassenger Registered Successfully.");
        System.out.println("Passenger ID : " + passengerId);
    }

    public Passenger getPassengerById(String  passengerId) throws RecordNotFoundException {
        if(passengers.containsKey(passengerId)){
            return passengers.get(passengerId);
        }
        throw  new RecordNotFoundException("Passenger with this Id is not found");
    }

    /**
     * Inserts demo passengers into the system.
     */
    public void initializeDemoPassengers() {

        Passenger passenger1 = new Passenger(
                IdGenerator.generatePassengerId(),
                "Rahul Sharma",
                "rahul@gmail.com",
                "9876543210",
                new LoyaltyAccount(150, "Silver")
        );

        Passenger passenger2 = new Passenger(
                IdGenerator.generatePassengerId(),
                "Priya Patel",
                "priya@gmail.com",
                "9876543211",
                new LoyaltyAccount(420, LoyaltyAccount.GOLD_TIER)
        );

        Passenger passenger3 = new Passenger(
                IdGenerator.generatePassengerId(),
                "Amit Kumar",
                "amit@gmail.com",
                "9876543212",
                new LoyaltyAccount(80, "Silver")
        );

        Passenger passenger4 = new Passenger(
                IdGenerator.generatePassengerId(),
                "Sneha Reddy",
                "sneha@gmail.com",
                "9876543213",
                new LoyaltyAccount(900, LoyaltyAccount.DIAMOND_TIER)
        );

        Passenger passenger5 = new Passenger(
                IdGenerator.generatePassengerId(),
                "Arjun Singh",
                "arjun@gmail.com",
                "9876543214",
                new LoyaltyAccount()
        );

        passengers.put(passenger1.getPassengerId(), passenger1);
        passengers.put(passenger2.getPassengerId(), passenger2);
        passengers.put(passenger3.getPassengerId(), passenger3);
        passengers.put(passenger4.getPassengerId(), passenger4);
        passengers.put(passenger5.getPassengerId(), passenger5);
    }
    public HashMap<String, Passenger> getPassengers() {
        return passengers;
    }
}