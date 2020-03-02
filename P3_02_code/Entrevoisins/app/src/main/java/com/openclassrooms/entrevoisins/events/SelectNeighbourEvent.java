package com.openclassrooms.entrevoisins.events;

import com.openclassrooms.entrevoisins.model.Neighbour;

/**
 * Event fired when a user select a Neighbour
 */
public class SelectNeighbourEvent {

    /**
     * Selected Neighbour
     */
    public Neighbour neighbour;

    /**
     * Constructor.
     * @param neighbour
     */
    public SelectNeighbourEvent(Neighbour neighbour) {
        this.neighbour = neighbour;
    }
}
