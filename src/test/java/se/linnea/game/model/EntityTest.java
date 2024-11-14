package se.linnea.game.model;

import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

class EntityTest {

    @org.junit.jupiter.api.Test
    void punch() {
        Entity burglar = new Burglar("burglar", 12,4);
        Entity resident = new Resident("resident", 12,3);

        burglar.punch(resident);
        Assertions.assertEquals(8,resident.getHealth());

        resident.punch(burglar);
        Assertions.assertEquals(9,burglar.getHealth());
    }
}