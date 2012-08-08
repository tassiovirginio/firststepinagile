package br.com.fa7.firststepinagile.business.dao;

import org.springframework.stereotype.Component;

import br.com.fa7.firststepinagile.business.dao.util.HibernateDAOGenerico;
import br.com.fa7.firststepinagile.entities.Sprint;

@Component
public class SprintDAO extends HibernateDAOGenerico<Sprint, Long> {}