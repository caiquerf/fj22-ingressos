package br.com.caelum.ingresso.validacao;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;

public class GerenciadorDeSessaoTest {

	private Filme rogueOne;
	private Sala sala3D;
	private Sessao sessaoDasDez;
	private Sessao sessaoDasTreze;
	private Sessao sessaoDasDezoito;
	
	@Before
	public void preparaSessao() {
		
		this.rogueOne = new Filme("Rogue One", Duration.ofMinutes(120), "SCI-FI", BigDecimal.ONE);
		this.sala3D = new Sala("sala 3D", BigDecimal.TEN);
		
		this.sessaoDasDez = new Sessao(rogueOne, sala3D, LocalTime.parse("10:00:00"));
		this.sessaoDasTreze = new Sessao(rogueOne, sala3D, LocalTime.parse("13:00:00"));
		this.sessaoDasDezoito = new Sessao(rogueOne, sala3D, LocalTime.parse("18:00:00"));	
	}
	
	@Test
	public void garanteQueNaoDevePermitirSessaoNoMesmoHorario() {
		
		List<Sessao> sessoes = Arrays.asList(sessaoDasDez);
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoes);
		Assert.assertFalse(gerenciador.cabe(sessaoDasDez));
		
	}
	
	@Test
	public void garanteQueNaoDevePermitirSessoesTerminandoDentroDoHorarioDeUmaSessaoExistente() {
		
		List<Sessao> sessoes = Arrays.asList(sessaoDasDez);
		Sessao sessao = new Sessao(rogueOne, sala3D, sessaoDasDez.getHorario().minusHours(1));
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoes);
		Assert.assertFalse(gerenciador.cabe(sessaoDasDez));
		
	}
	
	@Test
	public void garanteQueNaoDevePermitirSessoesIniciandoDentroDoHorarioDeUmaSessaoJaExistente() {
		
		List<Sessao> sessoes = Arrays.asList(sessaoDasDez);
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoes);
		Sessao sessao = new Sessao(rogueOne, sala3D, sessaoDasDez.getHorario().plusHours(1));
		Assert.assertFalse(gerenciador.cabe(sessaoDasDez));
		
	}
	
	@Test
	public void garanteQueDevePermitirUmaInsercaoEntreDoisFilmes() {
		
		List<Sessao> sessoes = Arrays.asList(sessaoDasDez, sessaoDasDezoito);
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoes);
		Sessao sessao = new Sessao(rogueOne, sala3D, sessaoDasDez.getHorario().plusHours(1));
		Assert.assertTrue(gerenciador.cabe(sessaoDasTreze));
		
	}
	
	@Test
	public void garanteQueDeveNaoPermitirUmaSessaoQueTerminaNoProximoDia() {
		
		List<Sessao> sessoes = Collections.emptyList();
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoes);
		Sessao sessaoQueTerminaAmanha = new Sessao(rogueOne, sala3D, LocalTime.parse("23:00:00"));
		
		Assert.assertFalse(gerenciador.cabe(sessaoQueTerminaAmanha));
		
	}
}
