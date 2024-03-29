package Project1;

import java.awt.Point;

public class BS_Submarine {
    private int shipSize = 3;
    private char shipSymbol = 's';
    @SuppressWarnings("unused")
    private battleshipModel model;

    BS_Submarine(Point coordinates, battleshipModel model, boolean rotation){
        this.model = model;
        double x = coordinates.getX();
        double y = coordinates.getY();
        int xPos = (int)x;
        int yPos = (int)y;
        System.out.println("Submarine - x: " + xPos + " y: " + yPos);

        //updates ships x coordinate if no rotation
        if(!rotation){
            while(shipSize > 0){
                model.setModel(xPos, yPos, shipSymbol);
                xPos++;
                shipSize--;
            }
        }
        else{
            while(shipSize > 0){
                model.setModel(xPos, yPos, shipSymbol);
                yPos++;
                shipSize--;
            }
        }
    }
    
}
