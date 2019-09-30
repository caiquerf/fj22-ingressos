package br.com.caelum.ingresso.validacao;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import br.com.caelum.ingresso.model.Sessao;

public class GerenciadorDeSessao {
	
	private List<Sessao> sessaoDasala;

	public GerenciadorDeSessao(List<Sessao> sessaoDasala) {
		this.sessaoDasala = sessaoDasala;
	}
	
	public boolean cabe(Sessao sessaoNova) {
		if (terminaAmanha(sessaoNova)) {
			return false;
		}
		return this.sessaoDasala.stream().noneMatch(sessaoExistente -> horarioIsConflitante(sessaoExistente, sessaoNova));
		
	}

	private boolean terminaAmanha(Sessao sessao) {
		
		LocalDateTime terminoSessaoNova = getTerminoSessaoComDiaDeHoje(sessao);
		LocalDateTime ultimoSegundoDeHoje = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
		
		if (terminoSessaoNova.isAfter(ultimoSegundoDeHoje)) {
			return true;
		}
		
		
		return false;
	}
	
	public boolean horarioIsConflitante(Sessao sessaoExistente, Sessao SessaoNova) {
		
		LocalDateTime inicioSessaoExistente = getInicioSessaoComDiaDeHoje(sessaoExistente);
		LocalDateTime terminoSessaoExistente = getTerminoSessaoComDiaDeHoje(sessaoExistente);
		LocalDateTime inicioSessaoNova = getInicioSessaoComDiaDeHoje(SessaoNova);
		LocalDateTime terminoSessaoNova = getTerminoSessaoComDiaDeHoje(SessaoNova);
		
		boolean sessaoNovaTerminaAntesDaExistente = terminoSessaoNova.isBefore(inicioSessaoExistente);
		boolean sessaoNovaComecaDepoisDaExistente = terminoSessaoExistente.isBefore(inicioSessaoNova);
		
		if (sessaoNovaTerminaAntesDaExistente || sessaoNovaComecaDepoisDaExistente) {
			return false;
		}
		return true;
	}
	
	private LocalDateTime getInicioSessaoComDiaDeHoje(Sessao sessao) {
		LocalDate hoje = LocalDate.now();
		
		return sessao.getHorario().atDate(hoje);
	}
	
	private LocalDateTime getTerminoSessaoComDiaDeHoje(Sessao sessao) {
		LocalDateTime inicioSessaoNova = getInicioSessaoComDiaDeHoje(sessao);
		
		return inicioSessaoNova.plus(sessao.getFilme().getDuracao());
	}
}
