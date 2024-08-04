package com.app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.dto.TrainDTO;
import com.app.entity.Trains;


public interface TrainDao extends JpaRepository<Trains, Long> {

	List<Trains> findByRouteId(Long routeId);

	
}
