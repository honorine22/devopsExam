package rw.ac.rca.termOneExam.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.dto.CreateCityDTO;
import rw.ac.rca.termOneExam.repository.ICityRepository;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class CityServiceTest {
    @Mock
    private ICityRepository cityRepositoryMock;

    @InjectMocks
    private CityService cityService;

    @Test
    public void getAll_withSomeElements() {
        when(cityRepositoryMock.findAll()).thenReturn(Arrays.asList(new City(101, "Kigali", 24, 75.2),
                new City(102, "Musanze", 18, 64.4)));
        assertEquals(75.2, cityService.getAll().get(0).getFahrenheit());
    }

    @Test
    public void getById_success() {
        when(cityRepositoryMock.findById(101L)).thenReturn(Optional.of(new
                City(101, "Kigali", 24, 75.2)));
        assertEquals("Kigali", cityService.getById(101).get().getName());
    }

    @Test
    public void update_Success() {
        CreateCityDTO dto = new CreateCityDTO("Huye", 18);
        City city = new City(101, "Huye",18,32);
        when(cityRepositoryMock.findById(101L)).thenReturn(Optional.of(city));
        when(cityRepositoryMock.existsByName(dto.getName())).thenReturn(true);
        when(cityRepositoryMock.save(city)).thenReturn(city);

        ResponseEntity<?> updateCity = cityService.updateCity(101, dto);
        assertTrue(updateCity.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void update_404() {
        CreateCityDTO dto = new CreateCityDTO("Huye", 18);
        City city = new City(1, "Huye", 18,32);
        when(cityRepositoryMock.findById(1L)).thenReturn(Optional.empty());


        ResponseEntity<?> updateItem = cityService.updateCity(1, dto);
        assertTrue(updateItem.getStatusCodeValue()==404);

    }

    @Test
    public void update_NameExists() {
        CreateCityDTO dto = new CreateCityDTO("Nyamagabe",15);
        City city = new City(101, "Kigali", 24, 95);
        when(cityRepositoryMock.findById(101L)).thenReturn(Optional.of(city));
        when(cityRepositoryMock.existsByName(dto.getName())).thenReturn(true);

        ResponseEntity<?> updateCity = cityService.updateCity(101, dto);
        assertTrue(updateCity.getStatusCodeValue()==400);
    }

@Test
public void post_success() {
    CreateCityDTO city = new CreateCityDTO( "Huye",18);
    City newCity = new City(108,"Huye",18,32);
    when(cityRepositoryMock.save(newCity)).thenReturn(newCity);

    assertEquals("Huye", city.getName());
}

}
