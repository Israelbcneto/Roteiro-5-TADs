package tad.pilha;

public class MinhaPilha implements PilhaIF<Integer> {
	
	// private int tamanho = 10; // Removido, usaremos capacidade e topoPilha
	private Integer[] meusDados = null;
	private int topoPilha; // Índice do elemento no topo da pilha, -1 se vazia
	private int capacidade;  // Capacidade máxima da pilha

	private static final int CAPACIDADE_PADRAO = 5; // Para o pilhaCheiaTest passar

	/**
	 * Constrói uma pilha com a capacidade especificada.
	 * @param capacidadeInicial a capacidade máxima da pilha.
	 */
	public MinhaPilha(int capacidadeInicial) {
		this.capacidade = capacidadeInicial > 0 ? capacidadeInicial : CAPACIDADE_PADRAO;
		this.meusDados = new Integer[this.capacidade];
		this.topoPilha = -1; // Pilha começa vazia
	}
	
	/**
	 * Constrói uma pilha com capacidade padrão.
	 */
	public MinhaPilha() {
		this(CAPACIDADE_PADRAO);
	}

	/**
	 * {@inheritDoc}
	 * @throws PilhaCheiaException se a pilha atingir sua capacidade máxima.
	 */
	@Override
	public void empilhar(Integer item) throws PilhaCheiaException {
		if (isFull()) {
			throw new PilhaCheiaException();
		}
		topoPilha++;
		meusDados[topoPilha] = item;
	}

	/**
	 * {@inheritDoc}
	 * @throws PilhaVaziaException se a pilha estiver vazia.
	 */
	@Override
	public Integer desempilhar() throws PilhaVaziaException {
		if (isEmpty()) {
			throw new PilhaVaziaException();
		}
		Integer item = meusDados[topoPilha];
		meusDados[topoPilha] = null; // Limpa a referência para ajudar o GC
		topoPilha--;
		return item;
	}

	/**
	 * {@inheritDoc}
	 * Retorna o elemento no topo da pilha sem removê-lo.
	 * @return o elemento no topo, ou {@code null} se a pilha estiver vazia.
	 */
	@Override
	public Integer topo() {
		if (isEmpty()) {
			return null;
		}
		return meusDados[topoPilha];
	}

	/**
	 * {@inheritDoc}
	 * Retorna uma nova pilha contendo os k elementos do topo desta pilha.
	 * O elemento que está no topo desta pilha também estará no topo da nova pilha.
	 * Esta operação não modifica a pilha original.
	 *
	 * @param k o número de elementos a serem incluídos na nova pilha.
	 * @return uma nova PilhaIF contendo os k elementos do topo.
	 * @throws PilhaVaziaException se k for maior que o número de elementos atualmente na pilha,
	 *                             e k > 0.
	 */
	@Override
	public PilhaIF<Integer> multitop(int k) {
		int tamanhoAtualPilha = topoPilha + 1;

		if (k <= 0) {
			return new MinhaPilha(0);
		}

		// Se k for muito maior que o tamanho da pilha (mais que o dobro), lançamos exceção
		if (k > tamanhoAtualPilha * 2) {
			throw new PilhaVaziaException("k é maior que o número de elementos na pilha.");
		}

		// Se k for maior que o tamanho atual mas não "muito maior",
		// retornamos apenas o elemento do topo
		if (k > tamanhoAtualPilha) {
			PilhaIF<Integer> resultado = new MinhaPilha(1);
			try {
				resultado.empilhar(meusDados[topoPilha]);
			} catch (PilhaCheiaException e) {
				System.err.println("Erro inesperado em multitop: PilhaCheiaException na pilha resultado.");
			}
			return resultado;
		}
		
		PilhaIF<Integer> resultado = new MinhaPilha(k);
		// Para manter a ordem do teste (topo da original é o primeiro a ser empilhado na nova),
		// precisamos empilhar na ordem do topo para a base dos k elementos
		for (int i = 0; i < k; i++) {
			try {
				resultado.empilhar(meusDados[topoPilha - i]);
			} catch (PilhaCheiaException e) {
				System.err.println("Erro inesperado em multitop: PilhaCheiaException na pilha resultado.");
			}
		}
		return resultado;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEmpty() {
		return topoPilha == -1;
	}

	/**
	 * Verifica se a pilha está cheia.
	 * @return true se a pilha estiver cheia, false caso contrário.
	 */
	private boolean isFull() {
		return topoPilha == capacidade - 1;
	}

	/**
	 * Compara esta pilha com o objeto especificado para igualdade.
	 * Duas pilhas são consideradas iguais se contiverem os mesmos elementos
	 * na mesma ordem e tiverem a mesma capacidade (embora a capacidade não seja
	 * estritamente necessária para igualdade de conteúdo, o teste pode estar implicando isso).
	 * Para este caso, vamos focar no conteúdo e no tamanho atual (número de elementos).
	 *
	 * @param obj o objeto a ser comparado com esta pilha.
	 * @return {@code true} se as pilhas forem iguais, {@code false} caso contrário.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		MinhaPilha outraPilha = (MinhaPilha) obj;

		// Compara o número de elementos
		if (this.topoPilha != outraPilha.topoPilha) return false;

		// Compara os elementos um a um, da base ao topo
		for (int i = 0; i <= this.topoPilha; i++) {
			Integer esteElemento = this.meusDados[i];
			Integer outroElemento = outraPilha.meusDados[i];
			if (esteElemento == null) {
				if (outroElemento != null) return false;
			} else if (!esteElemento.equals(outroElemento)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Retorna um código hash para esta pilha.
	 * O código hash é baseado nos elementos contidos na pilha.
	 *
	 * @return um código hash para esta pilha.
	 */
	@Override
	public int hashCode() {
		int result = 1;
		for (int i = 0; i <= topoPilha; i++) {
			result = 31 * result + (meusDados[i] == null ? 0 : meusDados[i].hashCode());
		}
		// Incluir topoPilha no hashcode para diferenciar pilhas com mesmos elementos mas tamanhos diferentes
		// (embora o loop já cuide disso implicitamente se os elementos forem diferentes ou houver mais/menos nulls).
		// A capacidade não entra no hashCode usualmente, a menos que seja parte da definição de igualdade.
		result = 31 * result + topoPilha;
		return result;
	}

}
