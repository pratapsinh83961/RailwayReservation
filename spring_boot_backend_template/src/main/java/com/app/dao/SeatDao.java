package com.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entity.Seat;
import com.app.entity.Trains;

public interface SeatDao extends JpaRepository<Seat, Long> {
    List<Seat> findByTrain(Trains train);
}
