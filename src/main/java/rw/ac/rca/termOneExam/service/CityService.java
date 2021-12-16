package rw.ac.rca.termOneExam.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.dto.CreateCityDTO;
import rw.ac.rca.termOneExam.repository.ICityRepository;

@Service
public class CityService {

	@Autowired
	private ICityRepository cityRepository;
	
	public Optional<City> getById(long id) {
		
		return cityRepository.findById(id);
	}

	public List<City> getAll() {
		List<City> cities = cityRepository.findAll();
		for(City city:cities) {
			city.setFahrenheit(city.getWeather()*1.8+32);
		}
		return cities;
	}

	public boolean existsByName(String name) {
		
		return cityRepository.existsByName(name);
	}

	public City save(CreateCityDTO dto) {
		City city =  new City(dto.getName(), dto.getWeather());
		return cityRepository.save(city);
	}

	public ResponseEntity<?> updateCity(int id, CreateCityDTO dto) {
		Optional<City> findById = cityRepository.findById((long) id);
		if(findById.isPresent()) {
			City city = findById.get();

			if(cityRepository.existsByName(dto.getName()) &&
					!(city.getName().equalsIgnoreCase(dto.getName()))) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("city name exists already!");
			}

			city.setName(dto.getName());
			city.setWeather(dto.getWeather());
			cityRepository.save(city);
			return ResponseEntity.status(HttpStatus.CREATED).body(city);

		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("city does not exist");

	}
	

}
