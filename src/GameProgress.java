import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class GameProgress implements Serializable {
    private static final long serialVersionUID = 1L;

    private int health;
    private int weapons;
    private int lvl;
    private double distance;

    public GameProgress(int health, int weapons, int lvl, double distance) {
        this.health = health;
        this.weapons = weapons;
        this.lvl = lvl;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "GameProgress{" +
                "health=" + health +
                ", weapons=" + weapons +
                ", lvl=" + lvl +
                ", distance=" + distance +
                '}';
    }

    static void saveGame(String pathToFile, GameProgress gameProgress) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(pathToFile);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(gameProgress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void zipFiles(String pathToFile, List<String> pathToFileList) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(pathToFile))) {
            for (String pathToZip : pathToFileList) {
                FileInputStream fis = new FileInputStream(pathToZip);
                File file = new File(pathToZip);
                ZipEntry entry = new ZipEntry(file.getName());
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
                fis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void deleteNotZip(List<String> pathToFileList) {
        for (String notZip : pathToFileList) {
            File notZipFile = new File(notZip);
            notZipFile.delete();
        }
    }
}