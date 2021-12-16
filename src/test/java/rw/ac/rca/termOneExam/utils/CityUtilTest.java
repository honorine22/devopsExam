package rw.ac.rca.termOneExam.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.assertj.core.api.Assertions.assertThat;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.repository.ICityRepository;
import rw.ac.rca.termOneExam.service.CityService;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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

    @Test
    public void city_contain_given_cities() {
        ArrayList<City> cities =  spy(ArrayList.class);

        City musanze = new City("Kigali",30);
        City kigali = new City("Musanze",40);

        cities.add(musanze);
        cities.add(kigali);

        assertThat(cities).extracting(City::getName).contains("Musanze","Kigali");
    }

    @Test
    public void testMocking() {
        ArrayList<City> arrayListMock =  mock(ArrayList.class);

        when(arrayListMock.size()).thenReturn(5);
        assertEquals(5, arrayListMock.size());

        when(arrayListMock.get(0)).thenReturn(new City("Kigali",30));
        assertEquals("Kigali", arrayListMock.get(0).getName());

        verify(arrayListMock, times(1)).get(0);

    }

    @Test
    public void testSpying() {

        ArrayList<City> arrayListSpy =  spy(ArrayList.class);
        arrayListSpy.add(new City("Kigali",30));

        arrayListSpy.add(new City("Musanze",40));


        when(arrayListSpy.size()).thenReturn(5);
        assertEquals(5, arrayListSpy.size());

        verify(arrayListSpy).add(new City("Musanze",40));
    }

}
