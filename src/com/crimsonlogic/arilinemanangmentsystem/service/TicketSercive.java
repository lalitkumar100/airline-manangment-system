package com.crimsonlogic.arilinemanangmentsystem.service;

import com.crimsonlogic.arilinemanangmentsystem.model.Booking;
import com.crimsonlogic.arilinemanangmentsystem.model.Flight;
import com.crimsonlogic.arilinemanangmentsystem.model.Ticket;
import com.crimsonlogic.arilinemanangmentsystem.utility.InputUtil;
import com.crimsonlogic.arilinemanangmentsystem.model.Seat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class TicketSercive {

    InputUtil input = new InputUtil();
    HashMap<String, Ticket>  TicketHashMap = new HashMap<>();

   FlightService flightService;
    public TicketSercive(FlightService flightService){
     this.flightService=flightService;
    }
    /**
     * Generates tickets for a completed flight.
     */
    public void generateTickets() {

        try {

            flightService.displayAllFlights();

            String flightId =
                    input.getString("Enter Flight ID : ");

            Flight flight =
                    flightService.findFlightById(flightId);

            if (flight.getStatus()
                    .equalsIgnoreCase(Flight.STATUS_COMPLETED)) {

                System.out.println("All right Ticket are genrated.");
                return;
            }


            PriorityQueue<Booking> queue = new PriorityQueue<>(flight.getWaitLsit());

            ArrayList<Booking> aBookings = new ArrayList<>();
            ArrayList<Booking> bBookings = new ArrayList<>();
            ArrayList<Booking> cBookings = new ArrayList<>();

            while (!queue.isEmpty()) {

                Booking booking = queue.poll();

                switch (booking.getSeatType().toUpperCase()) {

                    case "A":
                        aBookings.add(booking);
                        break;

                    case "B":
                        bBookings.add(booking);
                        break;

                    default:
                        cBookings.add(booking);
                }
            }

            int capacity = flight.getAircraft().getCapacity();

            int aCapacity = capacity * 20 / 100;
            int bCapacity = capacity * 30 / 100;
            int cCapacity = capacity - aCapacity - bCapacity;

            while (aBookings.size() < aCapacity && !bBookings.isEmpty()) {

                Booking booking = bBookings.remove(0);

                booking.setSeatType("A");

                aBookings.add(booking);
            }

            while (bBookings.size() < bCapacity && !cBookings.isEmpty()) {

                Booking booking = cBookings.remove(0);

                booking.setSeatType("B");

                bBookings.add(booking);
            }

            assignSeats(aBookings, 'A', flight);

            assignSeats(bBookings, 'B', flight);

            assignSeats(cBookings, 'C', flight);

            System.out.println("Tickets Generated Successfully.");
            flight.setStatus(Flight.STATUS_COMPLETED);

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    /**
     * Assigns seats and creates tickets.
     */
    private void assignSeats(ArrayList<Booking> bookings,
                             char seatType,
                             Flight flight) {

        int seatNumber = 1;

        for (Booking booking : bookings) {

            Seat seat = new Seat(
                    seatNumber++,
                    seatType,
                    false,
                    !booking.getSeatType()
                            .equalsIgnoreCase(String.valueOf(seatType)));

            Ticket ticket =
                    new Ticket(booking.getAmount(), seat);
            booking.setTicket(ticket);
            flight.addTickets(ticket);
            booking.setBookingstatus(Booking.STATUS_CONFIRMED);
            System.out.println("------------------------------------------");
            booking.displayInfo();
            System.out.println("Ticket ID : " + ticket.getTicketId());
            System.out.println("Seat      : " + seatType + seat.getSeatNo());
        }
    }
    }

