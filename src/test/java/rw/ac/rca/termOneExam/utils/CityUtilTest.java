package rw.ac.rca.termOneExam.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.repository.ICityRepository;
import rw.ac.rca.termOneExam.service.CityService;

import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CityUtilTest {
    @Mock
    private ICityRepository cityRepositoryMock;

    @InjectMocks
    private CityService cityService;

    @Test
    public void no_city_has_above_forty() {
        when(cityRepositoryMock.findAll()).thenReturn(Arrays.asList(
                new City(101, "Kigali", 24),
                new City(102, "Musanze", 18),
                new City(103, "Rubavu", 20),
                new City(104, "Nyagatare", 28)
        ));
        assertFalse("No city has weather more than 40\n" +
                "degree Celsius", cityService.getAll().get(0).getWeather() > 40);
    }

    @Test
    public void no_city_has_less_ten() {
        when(cityRepositoryMock.findAll()).thenReturn(Arrays.asList(
                new City(101, "Kigali", 24),
                new City(102, "Musanze", 18),
                new City(103, "Rubavu", 20),
                new City(104, "Nyagatare", 28)
        ));
        assertFalse("No city has weather less than 10\n" +
                "degree Celsius", cityService.getAll().get(0).getWeather() < 10);
    }


}
