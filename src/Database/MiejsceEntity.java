package Database;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Miejsce", schema = "dbo", catalog = "master")
public class MiejsceEntity {
    private int idMiejsca;
    private String rzad;
    private Integer numerMiejsca;
    private Boolean czyKanapa;
    private SalaEntity idSali;

    public SalaEntity getIdSali() {
        return idSali;
    }

    public void setIdSali(SalaEntity idSali) {
        this.idSali = idSali;
    }

    @Id
    @Column(name = "idMiejsca")
    public int getIdMiejsca() {
        return idMiejsca;
    }

    public void setIdMiejsca(int idMiejsca) {
        this.idMiejsca = idMiejsca;
    }

    @Basic
    @Column(name = "rzad")
    public String getRzad() {
        return rzad;
    }

    public void setRzad(String rzad) {
        this.rzad = rzad;
    }

    @Basic
    @Column(name = "numer_miejsca")
    public Integer getNumerMiejsca() {
        return numerMiejsca;
    }

    public void setNumerMiejsca(Integer numerMiejsca) {
        this.numerMiejsca = numerMiejsca;
    }

    @Basic
    @Column(name = "czyKanapa")
    public Boolean getCzyKanapa() {
        return czyKanapa;
    }

    public void setCzyKanapa(Boolean czyKanapa) {
        this.czyKanapa = czyKanapa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MiejsceEntity that = (MiejsceEntity) o;
        return idMiejsca == that.idMiejsca &&
                Objects.equals(rzad, that.rzad) &&
                Objects.equals(numerMiejsca, that.numerMiejsca) &&
                Objects.equals(czyKanapa, that.czyKanapa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMiejsca, rzad, numerMiejsca, czyKanapa);
    }

    @Override
    public String toString() {
        return "MiejsceEntity{" +
                "idMiejsca=" + idMiejsca +
                ", rzad='" + rzad + '\'' +
                ", numerMiejsca=" + numerMiejsca +
                ", czyKanapa=" + czyKanapa +
                ", idSali=" + idSali +
                '}';
    }
}
