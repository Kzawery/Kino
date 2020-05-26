package Database;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "Seans", schema = "dbo", catalog = "master")
public class SeansEntity {
    private Integer idSeansu;
    private Timestamp czas;
    private float cena;
    private SalaEntity salaEntity;
    private Integer salaBySalaId;
    private SalaEntity idSali;
    private FilmEntity idFilmu;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idFilmu", referencedColumnName = "idFilmu", nullable = false)
    public FilmEntity getIdFilmu() {
        return idFilmu;
    }

    public void setIdFilmu(FilmEntity idFilmu) {
        this.idFilmu = idFilmu;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idSali", referencedColumnName = "idSali", nullable = false)
    public SalaEntity getIdSali() {
        return idSali;
    }

    public void setIdSali(SalaEntity idSali) {
        this.idSali = idSali;
    }

    @Id
    @Column(name = "idSeansu")
    public Integer getIdSeansu() {
        return idSeansu;
    }

    public void setIdSeansu(Integer idSeansu) {
        this.idSeansu = idSeansu;
    }

    @Basic
    @Column(name = "czas")
    public Timestamp getCzas() {
        return czas;
    }

    public void setCzas(Timestamp czas) {
        this.czas = czas;
    }

    @Basic
    @Column(name = "cena")
    public Float getCena() {
        return cena;
    }

    public void setCena(Float cena) {
        this.cena = cena;
    }
    


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeansEntity that = (SeansEntity) o;
        return Objects.equals(idSeansu, that.idSeansu) &&
                Objects.equals(czas, that.czas) &&
                Objects.equals(cena, that.cena);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSeansu, czas, cena);
    }

    @Override
    public String toString() {
        return "SeansEntity{" +
                "idSeansu=" + idSeansu +
                ", czas=" + czas +
                ", cena=" + cena +
                ", salaEntity=" + salaEntity +
                ", salaBySalaId=" + salaBySalaId +
                ", idSali=" + idSali +
                ", idFilmu=" + idFilmu +
                '}';
    }
}
