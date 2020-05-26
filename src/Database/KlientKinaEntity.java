package Database;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "KlientKina", schema = "dbo", catalog = "master")
public class KlientKinaEntity {
    private int idKlienta;
    private String sha1;
    private String imie;
    private String nazwisko;
    private String email;

    @Id
    @Column(name = "idKlienta")
    public int getIdKlienta() {
        return idKlienta;
    }

    public void setIdKlienta(int idKlienta) {
        this.idKlienta = idKlienta;
    }

    @Basic
    @Column(name = "sha1")
    public String getSha1() {
        return sha1;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    @Basic
    @Column(name = "imie")
    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    @Basic
    @Column(name = "nazwisko")
    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KlientKinaEntity that = (KlientKinaEntity) o;
        return idKlienta == that.idKlienta &&
                Objects.equals(sha1, that.sha1) &&
                Objects.equals(imie, that.imie) &&
                Objects.equals(nazwisko, that.nazwisko) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idKlienta, sha1, imie, nazwisko, email);
    }

    @Override
    public String toString() {
        return "KlientKinaEntity{" +
                "idKlienta=" + idKlienta +
                ", sha1='" + sha1 + '\'' +
                ", imie='" + imie + '\'' +
                ", nazwisko='" + nazwisko + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
