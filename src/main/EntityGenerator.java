package main;

import entity.Entity;

public class EntityGenerator {

    final GamePanel gp;

    public EntityGenerator(GamePanel gp) {
        this.gp = gp;
    }

    public Entity getObject(String itemName) {

        return switch (itemName) {
//          case OBJ_PipsBone.OBJ_NAME -> new OBJ_PipsBone(gp);
            default -> null;
        };
    }
}
