package Database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "Bilet", schema = "dbo", catalog = "master")
public class BiletEntity {
    private int idBiletu;
    private ZamowienieEntity idZamownia;
    private MiejsceEntity idMiejsca;
    private UlgaEntity ulga;
    private SeansEntity idSeansu;

    public SeansEntity getIdSeansu() {
        return idSeansu;
    }

    public void setIdSeansu(SeansEntity idSeansu) {
        this.idSeansu = idSeansu;
    }

    public UlgaEntity getUlga() {
        return ulga;
    }

    public void setUlga(UlgaEntity ulga) {
        this.ulga = ulga;
    }

    public MiejsceEntity getIdMiejsca() {
        return idMiejsca;
    }

    public void setIdMiejsca(MiejsceEntity idMiejsca) {
        this.idMiejsca = idMiejsca;
    }

    public ZamowienieEntity getIdZamownia() {
        return idZamownia;
    }

    public void setIdZamownia(ZamowienieEntity idZamownia) {
        this.idZamownia = idZamownia;
    }

    @Id
    @Column(name = "idBiletu")
    public int getIdBiletu() {
        return idBiletu;
    }

    public void setIdBiletu(int idBiletu) {
        this.idBiletu = idBiletu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BiletEntity that = (BiletEntity) o;
        return idBiletu == that.idBiletu;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idBiletu);
    }

    @Override
    public String toString() {
        return "BiletEntity{" +
                "idBiletu=" + idBiletu +
                ", idZamownia=" + idZamownia +
                ", idMiejsca=" + idMiejsca +
                ", ulga=" + ulga +
                ", idSeansu=" + idSeansu +
                '}';
    }
}
