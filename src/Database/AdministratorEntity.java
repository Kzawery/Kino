package Database;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Administrator", schema = "dbo", catalog = "master")
public class AdministratorEntity {
    private int id;
    private String sha1;
    private String imie;
    private String nazwisko;
    private String email;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        AdministratorEntity that = (AdministratorEntity) o;
        return id == that.id &&
                Objects.equals(sha1, that.sha1) &&
                Objects.equals(imie, that.imie) &&
                Objects.equals(nazwisko, that.nazwisko) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sha1, imie, nazwisko, email);
    }
}
