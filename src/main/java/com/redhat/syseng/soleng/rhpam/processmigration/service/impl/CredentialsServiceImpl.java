package com.redhat.syseng.soleng.rhpam.processmigration.service.impl;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.redhat.syseng.soleng.rhpam.processmigration.model.Credentials;
import com.redhat.syseng.soleng.rhpam.processmigration.service.CredentialsService;

@ApplicationScoped
public class CredentialsServiceImpl implements CredentialsService {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Credentials get(Long id) {
        try {
            return em.createNamedQuery("Credentials.findByMigrationId", Credentials.class)
                     .setParameter("id", id)
                     .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    @Transactional
    public Credentials save(Credentials credentials) {
        em.persist(credentials);
        return credentials;
    }

    @Override
    @Transactional
    public Credentials delete(Long id) {
        Credentials cred = get(id);
        if (cred != null) {
            em.remove(cred);
            return cred;
        }
        return null;
    }

}
