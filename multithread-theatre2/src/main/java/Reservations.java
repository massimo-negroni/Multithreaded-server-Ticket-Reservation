public class Reservations {
    private final int seatNum;

    public Reservations(int seatNum){
        this.seatNum = seatNum;
    }

    public int checkSeats(){
        int maxSeats = 100;
            return maxSeats - seatNum;
    }
}
