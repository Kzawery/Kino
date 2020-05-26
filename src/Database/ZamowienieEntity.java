package Database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "Zamowienie", schema = "dbo", catalog = "master")
public class ZamowienieEntity {
    private int idZamowienia;
    private KlientKinaEntity idKlienta;
    private boolean Zakonczone;

    public boolean getZakonczone() {
        return Zakonczone;
    }

    public void setZakonczone(boolean zakonczone) {
        Zakonczone = zakonczone;
    }

    public KlientKinaEntity getIdKlienta() {
        return idKlienta;
    }

    public void setIdKlienta(KlientKinaEntity idKlienta) {
        this.idKlienta = idKlienta;
    }

    @Id
    @Column(name = "idZamowienia")
    public int getIdZamowienia() {
        return idZamowienia;
    }

    public void setIdZamowienia(int idZamowienia) {
        this.idZamowienia = idZamowienia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZamowienieEntity that = (ZamowienieEntity) o;
        return idZamowienia == that.idZamowienia;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idZamowienia);
    }

    @Override
    public String toString() {
        return "ZamowienieEntity{" +
                "idZamowienia=" + idZamowienia +
                ", idKlienta=" + idKlienta +
                ", Zakonczone=" + Zakonczone +
                '}';
    }
}
