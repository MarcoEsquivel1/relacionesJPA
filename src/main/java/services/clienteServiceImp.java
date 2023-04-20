package services;

import interfaces.Operations;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import models.ClienteEntity;
import utils.AdapterJPA;

import java.util.List;

@Stateless
public class clienteServiceImp implements Operations<ClienteEntity> {
    private final EntityManager em = AdapterJPA.getEntityManagerFactory();
    @Override
    public List<ClienteEntity> listar() {
        em.clear();
        TypedQuery<ClienteEntity> query = em.createQuery("SELECT c FROM ClienteEntity c", ClienteEntity.class);
        return query.getResultList();
    }

    @Override
    public void insertar(ClienteEntity cliente) {
        em.getTransaction().begin();
        em.persist(cliente);
        em.getTransaction().commit();
        em.clear();
    }

    @Override
    public void eliminar(int id) {
        em.getTransaction().begin();
        ClienteEntity cliente = em.find(ClienteEntity.class, id);
        em.remove(em.merge(cliente));
        em.getTransaction().commit();
        em.clear();
    }

    @Override
    public void actualizar(ClienteEntity cliente) {
        em.getTransaction().begin();
        em.merge(cliente);
        em.getTransaction().commit();
        em.clear();
    }

    @Override
    public ClienteEntity buscar(int id) {
        return em.find(ClienteEntity.class, id);
    }
}
