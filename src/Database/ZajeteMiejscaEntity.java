package Database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "ZajeteMiejsca", schema = "dbo", catalog = "master")
public class ZajeteMiejscaEntity {
    private int id;
    private SeansEntity idSeansu;
    private MiejsceEntity idMiejsca;

    public MiejsceEntity getIdMiejsca() {
        return idMiejsca;
    }

    public void setIdMiejsca(MiejsceEntity idMiejsca) {
        this.idMiejsca = idMiejsca;
    }

    public SeansEntity getIdSeansu() {
        return idSeansu;
    }

    public void setIdSeansu(SeansEntity idSeansu) {
        this.idSeansu = idSeansu;
    }

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZajeteMiejscaEntity that = (ZajeteMiejscaEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public String toString() {
        return "ZajeteMiejscaEntity{" +
                "id=" + id +
                ", idSeansu=" + idSeansu +
                ", idMiejsca=" + idMiejsca +
                '}';
    }
}
