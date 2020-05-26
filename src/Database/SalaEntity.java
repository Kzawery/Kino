package Database;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Sala", schema = "dbo", catalog = "master")
public class SalaEntity {
    private int idSali;
    private Boolean ułatwieniaDlaNiepełnosprawnych;
    private Boolean mozliwosc3D;
    private Integer numerSali;

    @Id
    @Column(name = "idSali")
    public int getIdSali() {
        return idSali;
    }

    public void setIdSali(int idSali) {
        this.idSali = idSali;
    }

    @Basic
    @Column(name = "ułatwienia_dla_niepełnosprawnych")
    public Boolean getUłatwieniaDlaNiepełnosprawnych() {
        return ułatwieniaDlaNiepełnosprawnych;
    }

    public void setUłatwieniaDlaNiepełnosprawnych(Boolean ułatwieniaDlaNiepełnosprawnych) {
        this.ułatwieniaDlaNiepełnosprawnych = ułatwieniaDlaNiepełnosprawnych;
    }

    @Basic
    @Column(name = "mozliwosc3D")
    public Boolean getMozliwosc3D() {
        return mozliwosc3D;
    }

    public void setMozliwosc3D(Boolean mozliwosc3D) {
        this.mozliwosc3D = mozliwosc3D;
    }

    @Basic
    @Column(name = "numer_sali")
    public Integer getNumerSali() {
        return numerSali;
    }

    public void setNumerSali(Integer numerSali) {
        this.numerSali = numerSali;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalaEntity that = (SalaEntity) o;
        return idSali == that.idSali &&
                Objects.equals(ułatwieniaDlaNiepełnosprawnych, that.ułatwieniaDlaNiepełnosprawnych) &&
                Objects.equals(mozliwosc3D, that.mozliwosc3D) &&
                Objects.equals(numerSali, that.numerSali);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSali, ułatwieniaDlaNiepełnosprawnych, mozliwosc3D, numerSali);
    }

    @Override
    public String toString() {
        return "SalaEntity{" +
                "idSali=" + idSali +
                ", ułatwieniaDlaNiepełnosprawnych=" + ułatwieniaDlaNiepełnosprawnych +
                ", mozliwosc3D=" + mozliwosc3D +
                ", numerSali=" + numerSali +
                '}';
    }
}
