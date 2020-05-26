package Database;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Ulga", schema = "dbo", catalog = "master")
public class UlgaEntity {
    private String idUlgi;
    private Double znizka;
    private String opis;

    @Id
    @Column(name = "idUlgi")
    public String getIdUlgi() {
        return idUlgi;
    }

    public void setIdUlgi(String idUlgi) {
        this.idUlgi = idUlgi;
    }

    @Basic
    @Column(name = "znizka")
    public Double getZnizka() {
        return znizka;
    }

    public void setZnizka(Double znizka) {
        this.znizka = znizka;
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
        UlgaEntity that = (UlgaEntity) o;
        return Objects.equals(idUlgi, that.idUlgi) &&
                Objects.equals(znizka, that.znizka) &&
                Objects.equals(opis, that.opis);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUlgi, znizka, opis);
    }

    @Override
    public String toString() {
        return "UlgaEntity{" +
                "idUlgi='" + idUlgi + '\'' +
                ", znizka=" + znizka +
                ", opis='" + opis + '\'' +
                '}';
    }
}
