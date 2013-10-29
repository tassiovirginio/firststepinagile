package br.com.fa7.firststepinagile.business.dao;

import org.springframework.stereotype.Component;

import br.com.fa7.firststepinagile.business.dao.util.HibernateDAOGenerico;
import br.com.fa7.firststepinagile.entities.Convite;

@Component
public class ConvitesDAO extends HibernateDAOGenerico<Convite, Long> {}