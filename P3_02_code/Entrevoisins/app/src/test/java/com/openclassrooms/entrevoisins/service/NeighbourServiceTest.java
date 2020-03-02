package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.util.List;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    /**
     * We ensure we can get Neighbour list
     */
    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    /**
     * We ensure we can delete a Neighbour
     */
    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    /**
     * We ensure we can create a Neighbour
     */
    @Test
    public void createNeighbourWithSuccess() {
        Neighbour neighbour = new Neighbour(100, "testName","testAvatarUrl","testAddress","testPhoneNumber","testAboutMe");
        service.createNeighbour(neighbour);
        assertTrue(service.getNeighbours().contains(neighbour));
    }

    /**
     * We ensure we can make a Neighbour favorite
     */
    @Test
    public void makeNeighbourFavoriteWithSuccess() {
        Neighbour neighbour = service.getNeighbours().get(0);
        neighbour.setFavorite(true);
        assertTrue(neighbour.getFavorite());
    }

}
