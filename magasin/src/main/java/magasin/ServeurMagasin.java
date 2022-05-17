package magasin;

import magasin.job.SendFactures;
import magasin.job.UpdatePrix;
import magasin.rmi.Serveur;

import java.io.FileInputStream;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServeurMagasin {
    public static final Properties config = new Properties();

    static {
        try {
            config.load(new FileInputStream("magasin.config"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startServeur() {
        Serveur serveur = new Serveur();
        serveur.start();
    }

    public static void scheduleJobs() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        ZonedDateTime now = ZonedDateTime.now();

        // Tous les jours à 20h00
        ZonedDateTime nextRun = now.withHour(20).withMinute(0).withSecond(0);
        if(now.compareTo(nextRun) > 0) nextRun = nextRun.plusDays(1);
        scheduler.scheduleAtFixedRate(
                new SendFactures(),
                Duration.between(now, nextRun).toSeconds(),
                TimeUnit.DAYS.toSeconds(1),
                TimeUnit.SECONDS
        );

        // Tous les jours à 6h00
        nextRun = now.withHour(6).withMinute(0).withSecond(0);
        if(now.compareTo(nextRun) > 0) nextRun = nextRun.plusDays(1);
        scheduler.scheduleAtFixedRate(
                new UpdatePrix(),
                Duration.between(now, nextRun).toSeconds(),
                TimeUnit.DAYS.toSeconds(1),
                TimeUnit.SECONDS
        );
    }

    public static void main(String[] args) {
        startServeur();
        scheduleJobs();

        if(args.length > 0 && args[0].equals("--test")) {
            (new UpdatePrix()).run();
            (new SendFactures()).run();
        }
    }
}
