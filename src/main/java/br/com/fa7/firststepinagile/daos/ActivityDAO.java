package br.com.fa7.firststepinagile.daos;

import org.springframework.stereotype.Component;

import br.com.fa7.firststepinagile.daos.util.HibernateDAOGenerico;
import br.com.fa7.firststepinagile.entities.Activity;

@Component
public class ActivityDAO extends HibernateDAOGenerico<Activity, Long> {
}
