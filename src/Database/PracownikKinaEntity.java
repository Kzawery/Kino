package Database;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "PracownikKina", schema = "dbo", catalog = "master")
public class PracownikKinaEntity {
    private int idPracownika;
    private String sha1;
    private String imie;
    private String nazwisko;
    private String email;
    private String rola;

    @Id
    @Column(name = "idPracownika")
    public int getIdPracownika() {
        return idPracownika;
    }

    public void setIdPracownika(int idPracownika) {
        this.idPracownika = idPracownika;
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

    @Basic
    @Column(name = "rola")
    public String getRola() {
        return rola;
    }

    public void setRola(String rola) {
        this.rola = rola;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PracownikKinaEntity that = (PracownikKinaEntity) o;
        return idPracownika == that.idPracownika &&
                Objects.equals(sha1, that.sha1) &&
                Objects.equals(imie, that.imie) &&
                Objects.equals(nazwisko, that.nazwisko) &&
                Objects.equals(email, that.email) &&
                Objects.equals(rola, that.rola);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPracownika, sha1, imie, nazwisko, email, rola);
    }
}
