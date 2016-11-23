/**
 * Created by cantoress on 21.11.2016.
 */
public class FieldCell {

    private boolean hasShip;
    private boolean hasNearShip;
    private boolean hasChecked;
    private Ship ship;

    public FieldCell() {
        hasShip = false;
        hasNearShip = false;
        hasChecked = false;
        ship = null;
    }

    public FieldCell(Ship ship) {
        hasShip = true;
        hasNearShip = false;
        hasChecked = false;
        this.ship = ship;
    }

    public boolean isHasShip() {
        return hasShip;
    }

    public void setHasShip(boolean hasShip) {
        this.hasShip = hasShip;
    }

    public boolean getHasNearShip() {
        return hasNearShip;
    }

    public void setHasNearShip(boolean hasNearShip) {
        this.hasNearShip = hasNearShip;
    }

    public boolean isHasChecked() {
        return hasChecked;
    }

    public void setHasChecked(boolean hasChecked) {
        this.hasChecked = hasChecked;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }




}
