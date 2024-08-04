package com.app.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.RouteDao;
import com.app.dao.StationDao;
import com.app.dao.TrainDao;
import com.app.dto.TrainDTO;
import com.app.entity.Route;
import com.app.entity.Station;
import com.app.entity.Trains;

@Service
@Transactional
public class TrainServiceImpl implements TrainService {
	
	@Autowired
	private TrainDao trainDao;
	
	@Autowired
	private StationDao stationDao;
	
	@Autowired
	private RouteDao routeDao;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public TrainDTO saveTrainDetails(TrainDTO train) {
		// TODO Auto-generated method stub
		
		Route route = routeDao.findById(train.getRoute_id())
	            .orElseThrow(() -> new RuntimeException("Route not found"));
	
		Trains train1 = modelMapper.map(train,Trains.class);
		train1.setRoute(route);
		Trains newtrain = trainDao.save(train1);
		
		TrainDTO newtrainDTO = modelMapper.map(newtrain,TrainDTO.class);
		
		newtrainDTO.setRoute_id(route.getId());
		return newtrainDTO;
	}

	@Override
	
	public List<TrainDTO> getTrainsByRouteId(Long routeId) {
		
		List<Trains> trains = trainDao.findByRouteId(routeId);
		   return trains.stream().map(train -> {
	            TrainDTO trainDTO = modelMapper.map(train, TrainDTO.class);
	            trainDTO.setRoute_id(train.getRoute().getId());
	            return trainDTO;
	        }).collect(Collectors.toList());
	
	}

	
	 public Trains getTrainById(Long trainId) {
	        return trainDao.findById(trainId).orElseThrow(() -> new RuntimeException("Train not found"));
	    }

	@Override
	public Trains findById(Long trainId) {
		// TODO Auto-generated method stub
		  return trainDao.findById(trainId).orElseThrow(() -> new RuntimeException("Train not found"));
	}
}
