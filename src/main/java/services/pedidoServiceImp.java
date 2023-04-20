package services;

import interfaces.Operations;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import models.PedidoEntity;
import utils.AdapterJPA;

import java.util.List;

@Stateless
public class pedidoServiceImp implements Operations<PedidoEntity> {
    private final EntityManager em = AdapterJPA.getEntityManagerFactory();
    @Override
    public List<PedidoEntity> listar() {
        em.clear();
        TypedQuery<PedidoEntity> query = em.createQuery("SELECT p FROM PedidoEntity p JOIN FETCH p.clienteByIdCliente ", PedidoEntity.class);
        return query.getResultList();
    }
    @Override
    public void insertar(PedidoEntity pedido) {
        em.getTransaction().begin();
        em.persist(pedido);
        em.getTransaction().commit();
        em.clear();
    }

    @Override
    public void eliminar(int id) {
        em.getTransaction().begin();
        PedidoEntity pedido = em.find(PedidoEntity.class, id);
        em.remove(em.merge(pedido));
        em.getTransaction().commit();
        em.clear();
    }

    @Override
    public void actualizar(PedidoEntity pedido) {
        em.getTransaction().begin();
        em.merge(pedido);
        em.getTransaction().commit();
        em.clear();
    }

    @Override
    public PedidoEntity buscar(int id) {
        return em.find(PedidoEntity.class, id);
    }
}
