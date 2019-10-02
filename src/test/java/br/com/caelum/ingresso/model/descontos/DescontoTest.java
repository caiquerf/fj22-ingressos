package br.com.caelum.ingresso.model.descontos;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;

import org.junit.Test;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Ingresso;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;
import junit.framework.Assert;




public class DescontoTest {
	
	@Test
	public void naoDeveConcederDescontoParaIngressoNormal() {
		Sala sala = new Sala("Eldorado - IMAX", new BigDecimal("20,5"));
		Filme filme = new Filme("Rogue One", Duration.ofMillis(120), "SCI-FI", new BigDecimal("12"));
		Sessao sessao = new Sessao( filme, sala,LocalTime.parse("10:00:00"));
		Ingresso ingressos = new Ingresso(sessao, new semDesconto());
		
		BigDecimal precoeEsperado = new BigDecimal("32,50");
		
		Assert.assertEquals(precoeEsperado, ingressos.getPreco());
	
	}

}
