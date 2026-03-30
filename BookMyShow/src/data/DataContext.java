package data;

import models.*;

import java.util.List;

/**
 * Plain record-like container for all seeded objects.
 * Avoids bleeding DataSeeder internals into Main.
 */
public class DataContext {

    public final City    mumbai;
    public final City    bangalore;

    public final Movie   inception;
    public final Movie   kgf;
    public final Movie   pushpa;

    public final Theatre pvr;
    public final Theatre inox;

    public final Show    show1;   // Inception @ PVR IMAX
    public final Show    show2;   // Inception @ PVR 2D
    public final Show    show3;   // KGF @ INOX Dolby
    public final Show    show4;   // Pushpa @ PVR 2D

    public final List<Seat> imaxSeats;
    public final List<Seat> hall2Seats;

    public DataContext(City mumbai, City bangalore,
                       Movie inception, Movie kgf, Movie pushpa,
                       Theatre pvr, Theatre inox,
                       Show show1, Show show2, Show show3, Show show4,
                       List<Seat> imaxSeats, List<Seat> hall2Seats) {
        this.mumbai    = mumbai;
        this.bangalore = bangalore;
        this.inception = inception;
        this.kgf       = kgf;
        this.pushpa    = pushpa;
        this.pvr       = pvr;
        this.inox      = inox;
        this.show1     = show1;
        this.show2     = show2;
        this.show3     = show3;
        this.show4     = show4;
        this.imaxSeats = imaxSeats;
        this.hall2Seats = hall2Seats;
    }
}
