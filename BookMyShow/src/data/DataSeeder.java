package data;

import enums.*;
import models.*;
import service.AdminService;
import pricing.BasePricingStrategy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DataSeeder {

    private final AdminService adminService;

    public DataSeeder(AdminService adminService) {
        this.adminService = adminService;
    }

    public DataContext seed() {
        City mumbai    = new City("Mumbai", "Maharashtra");
        City bangalore = new City("Bangalore", "Karnataka");

        Movie inception = new Movie(
                "Inception",
                "A thief who steals corporate secrets through dream-sharing technology.",
                148, Language.ENGLISH,
                List.of(Genre.SCI_FI, Genre.THRILLER)
        );
        inception.setRating(8.8);

        Movie kgf = new Movie(
                "KGF Chapter 2",
                "Rocky's rise to power continues in Kolar Gold Fields.",
                168, Language.KANNADA,
                List.of(Genre.ACTION, Genre.DRAMA)
        );
        kgf.setRating(8.4);

        Movie pushpa = new Movie(
                "Pushpa: The Rule",
                "Pushpa Raj battles his enemies as he expands his red sandalwood empire.",
                175, Language.TELUGU,
                List.of(Genre.ACTION, Genre.THRILLER)
        );
        pushpa.setRating(7.6);

        adminService.addMovie(inception);
        adminService.addMovie(kgf);
        adminService.addMovie(pushpa);

        List<Seat> imaxSeats  = buildSeats(5, 10);
        Screen imaxScreen     = new Screen("IMAX Hall 1", ScreenType.IMAX, imaxSeats);

        List<Seat> hall2Seats = buildSeats(6, 8);
        Screen screen2D       = new Screen("Hall 2", ScreenType.SCREEN_2D, hall2Seats);

        List<Seat> dolbySeats = buildSeats(4, 8);
        Screen dolbyScreen    = new Screen("Dolby Atmos Hall", ScreenType.DOLBY, dolbySeats);

        Theatre pvr = new Theatre(
                "PVR ICON Juhu",
                "Juhu, Mumbai - 400049",
                mumbai,
                new ArrayList<>(List.of(imaxScreen, screen2D))
        );

        Theatre inox = new Theatre(
                "INOX Garuda Mall",
                "Garuda Mall, Bengaluru - 560001",
                bangalore,
                new ArrayList<>(List.of(dolbyScreen))
        );

        adminService.addTheatre(pvr);
        adminService.addTheatre(inox);

        LocalDateTime now = LocalDateTime.now();

        Show show1 = new Show(inception, imaxScreen, pvr,
                now.plusHours(2), now.plusHours(2).plusMinutes(148));

        Show show2 = new Show(inception, screen2D, pvr,
                now.plusHours(5), now.plusHours(5).plusMinutes(148));

        Show show3 = new Show(kgf, dolbyScreen, inox,
                now.plusHours(3), now.plusHours(3).plusMinutes(168));

        Show show4 = new Show(pushpa, screen2D, pvr,
                now.plusHours(6), now.plusHours(6).plusMinutes(175));

        adminService.addShow(show1, 200.0);
        adminService.addShow(show2, 150.0);
        adminService.addShow(show3, 180.0);
        adminService.addShow(show4, 160.0);

        adminService.applyDynamicPricing(show1.getId(), new BasePricingStrategy());

        System.out.println("\n[DataSeeder] Sample data ready. Cities: Mumbai, Bangalore. Movies: 3. Shows: 4.\n");

        return new DataContext(mumbai, bangalore,
                inception, kgf, pushpa,
                pvr, inox,
                show1, show2, show3, show4,
                imaxSeats, hall2Seats);
    }

    private List<Seat> buildSeats(int rows, int cols) {
        List<Seat> seats = new ArrayList<>();
        for (int r = 0; r < rows; r++) {
            SeatType type = mapRowToType(r, rows);
            for (int c = 1; c <= cols; c++) {
                String label = (char) ('A' + r) + String.valueOf(c);
                seats.add(new Seat(label, r, c, type));
            }
        }
        return seats;
    }

    private SeatType mapRowToType(int row, int totalRows) {
        double frac = (double) row / totalRows;
        if (frac < 0.40) return SeatType.SILVER;
        if (frac < 0.65) return SeatType.GOLD;
        if (frac < 0.85) return SeatType.PLATINUM;
        return SeatType.RECLINER;
    }
}
