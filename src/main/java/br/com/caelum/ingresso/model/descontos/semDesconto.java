package br.com.caelum.ingresso.model.descontos;

import java.math.BigDecimal;

public class semDesconto implements Desconto {

	@Override
	public BigDecimal aplicarDescontoSobbre(BigDecimal precoOriginal) {
		return precoOriginal;
	}
	
	

}
