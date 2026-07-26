package com.crimsonlogic.arilinemanangmentsystem.utility;

import com.crimsonlogic.arilinemanangmentsystem.exception.InvalidCrewException;
import com.crimsonlogic.arilinemanangmentsystem.exception.InvalidHumanException;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

public final class ValidatorUtil {

    private ValidatorUtil() {
    }

    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    private static final String PHONE_REGEX =
            "^[0-9]{10}$";

    public static void validateEmail(String email)
            throws InvalidHumanException {

        if (!Pattern.matches(EMAIL_REGEX, email)) {
            throw new InvalidHumanException("Invalid email address.");
        }
    }

    public static void validatePhone(String phone)
            throws InvalidHumanException {

        if (!Pattern.matches(PHONE_REGEX, phone)) {
            throw new InvalidHumanException("Phone number must contain exactly 10 digits.");
        }
    }

    public static void validateAge(LocalDate dob)
            throws InvalidHumanException {

        int age = Period.between(dob, LocalDate.now()).getYears();

        if (age < 0 || age > 120) {
            throw new InvalidHumanException("Age must be between 0 and 120 years.");
        }
    }




}