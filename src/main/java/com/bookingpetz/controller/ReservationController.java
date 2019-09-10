/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bookingpetz.controller;

import com.bookingpetz.domain.Reservation;
import com.bookingpetz.services.ReservationService;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author burakzengin
 */
@org.springframework.stereotype.Controller
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @RequestMapping(value = "/doReservation", method = RequestMethod.POST)
    public String addPet(Model m, HttpServletRequest request) {
        try {
            HttpSession session = request.getSession(false);
            if (session.getAttribute("token") != null) {
                Reservation reservation = new Gson().fromJson(request.getParameter("result"), Reservation.class);
                if (reservationService.doReservation(session.getAttribute("token").toString(), reservation)) {
                    return "redirect:bookings";
                }
                return "redirect:home?failed";
            } else {
                return "redirect:/";
            }
        } catch (JsonSyntaxException exception) {
            return "redirect:/?" + exception;
        }
    }

    @RequestMapping(value = "/bookings", method = RequestMethod.GET)
    public String bookings(Model m, HttpServletRequest request) {
        return "bookings";
    }

    @RequestMapping(value = "/hotelBookings", method = RequestMethod.GET)
    public String hotelBookings(Model m, HttpServletRequest request) {
        return "hotelBookings";
    }
}
