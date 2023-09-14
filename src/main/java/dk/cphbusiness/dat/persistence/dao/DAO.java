package dk.cphbusiness.dat.persistence.dao;

import dk.cphbusiness.dat.model.IJPAEntity;
import dk.cphbusiness.dat.persistence.dao.ADAO;

/**
 * This class is a generic DAO (Data Access Object) that can be used to perform CRUD operations on any entity.
 * @param <T> The entity class that the DAO should be used for.
 */
public class DAO<T extends IJPAEntity> extends ADAO<T> {

    protected DAO(Class<T> entityClass) {
        super(entityClass);
    }
}
