package tad.fila;

/**
 * Implementação de uma fila de inteiros usando um array fixo com estratégia circular.
 * A fila segue o princípio FIFO (First-In, First-Out).
 * Lança exceções específicas para condições de fila cheia ou vazia.
 * @author fabioleite
 *
 */
public class MinhaFila implements FilaIF<Integer> {
	
	private int tamanho = 10;
	
	private int cabeca;
	private int cauda;
	private int numElementos;
	
	private Integer[] meusDados = null;

	/**
	 * Constrói uma nova fila com o tamanho especificado.
	 * @param tamanhoEspecificado o tamanho máximo da fila.
	 */
	public MinhaFila(int tamanhoEspecificado) {
		this.tamanho = tamanhoEspecificado > 0 ? tamanhoEspecificado : 10; // Garante tamanho positivo
		meusDados = new Integer[this.tamanho];
		cabeca = 0;
		cauda = 0;
		numElementos = 0;
	}
	
	/**
	 * Constrói uma nova fila com um tamanho padrão (10 elementos).
	 */
	public MinhaFila() {
		meusDados = new Integer[this.tamanho]; // this.tamanho já é 10 por padrão
		cabeca = 0;
		cauda = 0;
		numElementos = 0;
	}

	/**
	 * {@inheritDoc}
	 * @throws FilaCheiaException se a fila estiver cheia.
	 */
	@Override
	public void enfileirar(Integer item) throws FilaCheiaException {
		if (isFull()) {
			throw new FilaCheiaException();
		}
		meusDados[cauda] = item;
		cauda = (cauda + 1) % tamanho;
		numElementos++;
	}

	/**
	 * {@inheritDoc}
	 * @throws FilaVaziaException se a fila estiver vazia.
	 */
	@Override
	public Integer desenfileirar() throws FilaVaziaException {
		if (isEmpty()) {
			throw new FilaVaziaException();
		}
		Integer itemRemovido = meusDados[cabeca];
		meusDados[cabeca] = null; // Opcional: limpar a posição
		cabeca = (cabeca + 1) % tamanho;
		numElementos--;
		return itemRemovido;
	}

	/**
	 * {@inheritDoc}
	 * Retorna o último elemento inserido na fila (o mais recente) sem removê-lo.
	 * @return o elemento na cauda da fila, ou {@code null} se a fila estiver vazia.
	 */
	@Override
	public Integer verificarCauda() {
		if (isEmpty()) {
			return null;
		}
		// A cauda (último elemento inserido) está na posição anterior ao índice 'cauda'
		int indiceUltimoElemento = (cauda - 1 + tamanho) % tamanho;
		return meusDados[indiceUltimoElemento];
	}

	/**
	 * {@inheritDoc}
	 * @return o elemento na cabeça da fila, ou {@code null} se a fila estiver vazia.
	 */
	@Override
	public Integer verificarCabeca() {
		if (isEmpty()) {
			return null;
		}
		return meusDados[cabeca];
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEmpty() {
		return numElementos == 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isFull() {
		return numElementos == tamanho;
	}

}
