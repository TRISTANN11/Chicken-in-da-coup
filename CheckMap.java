package sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;

public class CheckMap {

    boolean[][] mapBool;

    public CheckMap(String filename) throws URISyntaxException {

        mapBool = new boolean[32][34];

        try (BufferedReader br = new BufferedReader(new FileReader(new File("src/sample/"+filename)))) {

            String strCurrentLine;
            Integer offset=0;
            while ((strCurrentLine = br.readLine()) != null) {


                String[] arrayString = strCurrentLine.split(",");
                for (int i = 0; i < arrayString.length; i++) {
                    if (Integer.parseInt(arrayString[i])==1){
                        mapBool[i][offset ] = true;
                    }else {
                        mapBool[i][offset] = false;

                    }
                }
                offset++;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }

    public double[] movement(double x, double y, double dx, double dy)  {
        int intX = (int)(x+dx)/25;
        int intY = (int)(y+dy)/25;
        Double moveX,moveY;
        moveX=x;
        moveY=y;

        if (mapBool[intX][intY]){
            moveX=moveX+dx;
            moveY=moveY+dy;
        }
      //  System.out.println("X = "+intX+"    Y = "+intY);

        if (intY>15 && intY<18){
            if (intX==30 && dx>0){
            moveX=5.0;
            }else if (intX==0 && dx<0)
            moveX=795.0;
        }

        double[] returnVal =new double[2];

        returnVal[0]=moveX;
         returnVal[1]=moveY;
         return returnVal;
    }

    public boolean canMoveLeft(double x, double y){

        Integer intX = (int)x/25;
        Integer intY = (int)y/25;

        if (intX<1){
            return false;
        }

        return mapBool[intX-1][intY];
    }
    public boolean canMoveRight(double x, double y){
        Integer intX = (int)x/25;
        Integer intY = (int)y/25;

        if (intX>29){
            return false;
        }
        return mapBool[intX+1][intY];
    }
    public boolean canMoveUp(double x, double y){
        Integer intX = (int)x/25;
        Integer intY = (int)y/25;

        if (intY<1){
            return false;
        }
        return mapBool[intX][intY-1];
    }
    public boolean canMoveDown(double x, double y){
        Integer intX = (int)x/25;
        Integer intY = (int)y/25;

        if (intY>30){
            return false;
        }
        return mapBool[intX][intY+1];
    }

    public boolean canSee(double px, double py, double x, double y){
        Integer intPX = (int)px/25;
        Integer intPY = (int)py/25;
        Integer intX = (int)x/25;
        Integer intY = (int)y/25;

        boolean pathX = false;
        boolean pathY = false;


        //Check vertical path

        //Up
        if ((intPX == intX) && (intPY < intY)){
            for (int i = 0; i < intY - intPY;i++){
                pathY = canMoveUp(x, y - (i*25));
                if (pathY == false){
                    break;
                }
            }
        }
        //Down
        else if ((intPX == intX) && (intPY > intY)){
            for (int i = 0; i < intPY - intY;i++){
                pathY = canMoveDown(x, y + (i*25));
                if (pathY == false){
                    break;
                }
            }
        }

        //Horizontal Path
        //Left
        if ((intPY == intY) && (intPX < intX)){
            for (int i = 0; i < intX - intPX;i++){
                pathX = canMoveLeft(x-(i*25), y);
                if (pathX == false){
                    break;
                }
            }
        }
        //Right
        else if ((intPY == intY) && (intPX > intX)){
            for (int i = 0; i < intPX - intX;i++){
                pathX = canMoveRight(x+(i*25), y);
                if (pathX == false){
                    break;
                }
            }
        }
        return (pathX || pathY);
    }

    public static void main(String[] args) throws URISyntaxException {
    CheckMap myMap = new CheckMap("Map1.csv");
    }

}
