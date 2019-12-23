package io.mankea.tools.img_renamer.model;

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import static io.mankea.tools.img_renamer.Photos.getDateTakenExif;
import static io.mankea.tools.img_renamer.Photos.getTakenNio;

public class ImgFile {

    private File file;
    private String name;
    private Date createdAtExif;
    private Instant createdAtPlain;

    public ImgFile(File file) {
        this.file = file;
        this.name = file.getName();
        this.createdAtExif = getDateTakenExif(file);
        this.createdAtPlain = getTakenNio(file);
    }

    public long daysDiff() {
        if(createdAtExif != null && createdAtPlain != null) {
            return Duration.between(createdAtExif.toInstant(), createdAtPlain).abs().toDays();
        } else {
            return 0;
        }
    }

    public Date getCreatedAt() {
        return createdAtExif != null ? createdAtExif : Date.from(createdAtPlain);
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedAtExif() {
        return createdAtExif;
    }

    public void setCreatedAtExif(Date createdAtExif) {
        this.createdAtExif = createdAtExif;
    }

    public Instant getCreatedAtPlain() {
        return createdAtPlain;
    }

    public void setCreatedAtPlain(Instant createdAtPlain) {
        this.createdAtPlain = createdAtPlain;
    }

    @Override
    public String toString() {
        return name + " (" + getCreatedAt() + ")";
    }
}
