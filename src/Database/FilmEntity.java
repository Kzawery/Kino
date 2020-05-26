package Database;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "Film", schema = "dbo", catalog = "master")
public class FilmEntity {
    private int idFilmu;
    private String tytul;
    private Date rokProdukcji;
    private String rezyser;
    private String gatunek;
    private Time czasTrwania;
    private String opis;

    @Id
    @Column(name = "idFilmu")
    public int getIdFilmu() {
        return idFilmu;
    }

    public void setIdFilmu(int idFilmu) {
        this.idFilmu = idFilmu;
    }

    @Basic
    @Column(name = "tytul")
    public String getTytul() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    @Basic
    @Column(name = "rok_produkcji")
    public Date getRokProdukcji() {
        return rokProdukcji;
    }

    public void setRokProdukcji(Date rokProdukcji) {
        this.rokProdukcji = rokProdukcji;
    }

    @Basic
    @Column(name = "rezyser")
    public String getRezyser() {
        return rezyser;
    }

    public void setRezyser(String rezyser) {
        this.rezyser = rezyser;
    }

    @Basic
    @Column(name = "gatunek")
    public String getGatunek() {
        return gatunek;
    }

    public void setGatunek(String gatunek) {
        this.gatunek = gatunek;
    }

    @Basic
    @Column(name = "czas_trwania")
    public Time getCzasTrwania() {
        return czasTrwania;
    }

    public void setCzasTrwania(Time czasTrwania) {
        this.czasTrwania = czasTrwania;
    }

    @Basic
    @Column(name = "opis")
    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmEntity that = (FilmEntity) o;
        return idFilmu == that.idFilmu &&
                Objects.equals(tytul, that.tytul) &&
                Objects.equals(rokProdukcji, that.rokProdukcji) &&
                Objects.equals(rezyser, that.rezyser) &&
                Objects.equals(gatunek, that.gatunek) &&
                Objects.equals(czasTrwania, that.czasTrwania) &&
                Objects.equals(opis, that.opis);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFilmu, tytul, rokProdukcji, rezyser, gatunek, czasTrwania, opis);
    }

    @Override
    public String toString() {
        return "FilmEntity{" +
                "idFilmu=" + idFilmu +
                ", tytul='" + tytul + '\'' +
                ", rokProdukcji=" + rokProdukcji +
                ", rezyser='" + rezyser + '\'' +
                ", gatunek='" + gatunek + '\'' +
                ", czasTrwania=" + czasTrwania +
                ", opis='" + opis + '\'' +
                '}';
    }
}
